package org.miniproject.githubprofileanalyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miniproject.githubprofileanalyzer.client.GitHubApiClient;
import org.miniproject.githubprofileanalyzer.dto.AnalysisResponse;
import org.miniproject.githubprofileanalyzer.model.GitHubUser;
import org.miniproject.githubprofileanalyzer.model.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoringService {
    
    private final GitHubApiClient gitHubApiClient;
    
    public AnalysisResponse.ProfileMetrics calculateMetrics(String username) {
        GitHubUser user = gitHubApiClient.getUser(username);
        List<Repository> repositories = gitHubApiClient.getUserRepositories(username);
        
        // Filter out forked repositories for scoring
        List<Repository> originalRepos = repositories.stream()
                .filter(r -> !r.isFork())
                .toList();
        
        // Calculate score breakdown
        AnalysisResponse.ScoreBreakdown scoreBreakdown = calculateScoreBreakdown(user, originalRepos);
        
        // Calculate overall score
        int overallScore = calculateOverallScore(scoreBreakdown);
        String grade = calculateGrade(overallScore);
        
        // Calculate activity metrics
        AnalysisResponse.ActivityMetrics activityMetrics = calculateActivityMetrics(user, repositories, originalRepos);
        
        // Get pinned and top repositories
        List<String> pinnedRepoSummary = getPinnedRepoSummary(originalRepos);
        List<String> topRepoSummary = getTopRepoSummary(originalRepos);
        
        return AnalysisResponse.ProfileMetrics.builder()
                .username(user.getLogin())
                .overallScore(overallScore)
                .grade(grade)
                .scoreBreakdown(scoreBreakdown)
                .activityMetrics(activityMetrics)
                .pinnedRepoSummary(List.of())
                .topRepoSummary(topRepoSummary)
                .lastActivityDate(user.getUpdatedAt()) // Real last activity from GitHub
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                .build();
    }
    
    private AnalysisResponse.ScoreBreakdown calculateScoreBreakdown(GitHubUser user, List<Repository> repos) {
        return AnalysisResponse.ScoreBreakdown.builder()
                .documentationQuality(calculateDocumentationScore(repos))
                .codeStructure(calculateCodeStructureScore(repos))
                .activityConsistency(calculateActivityScore(user, repos))
                .repositoryOrganization(calculateRepoOrganizationScore(repos))
                .projectImpact(calculateImpactScore(repos))
                .technicalDepth(calculateTechnicalDepthScore(repos))
                .build();
    }
    
    private int calculateDocumentationScore(List<Repository> repos) {
        if (repos.isEmpty()) return 0;
        
        long reposWithReadme = repos.stream()
                .filter(r -> r.isHasReadme() || (r.getDescription() != null && !r.getDescription().isEmpty()))
                .count();
        
        long reposWithGoodDescription = repos.stream()
                .filter(r -> r.getDescription() != null && r.getDescription().length() > 20)
                .count();
        
        double readmeRatio = (double) reposWithReadme / repos.size();
        double descriptionRatio = (double) reposWithGoodDescription / repos.size();
        
        return (int) Math.min(100, (readmeRatio * 50 + descriptionRatio * 50));
    }
    
    private int calculateCodeStructureScore(List<Repository> repos) {
        if (repos.isEmpty()) return 0;
        
        // Check for organized repos with topics, good naming, etc.
        long reposWithTopics = repos.stream()
                .filter(r -> r.getTopics() != null && !r.getTopics().isEmpty())
                .count();
        
        long wellNamedRepos = repos.stream()
                .filter(r -> !r.getName().matches(".*test.*|.*demo.*|.*temp.*"))
                .count();
        
        double topicsRatio = (double) reposWithTopics / repos.size();
        double namingRatio = (double) wellNamedRepos / repos.size();
        
        return (int) Math.min(100, (topicsRatio * 40 + namingRatio * 60));
    }
    
    private int calculateActivityScore(GitHubUser user, List<Repository> repos) {
        if (repos.isEmpty()) return 0;
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ninetyDaysAgo = now.minus(90, ChronoUnit.DAYS);
        
        long recentlyActive = repos.stream()
                .filter(r -> r.getPushedAt() != null && r.getPushedAt().isAfter(ninetyDaysAgo))
                .count();
        
        double activityRatio = (double) recentlyActive / repos.size();
        
        // Account age bonus
        long accountAgeDays = ChronoUnit.DAYS.between(user.getCreatedAt(), now);
        int ageBonus = accountAgeDays > 365 ? 20 : (int) (accountAgeDays / 365.0 * 20);
        
        return (int) Math.min(100, (activityRatio * 80) + ageBonus);
    }
    
    private int calculateRepoOrganizationScore(List<Repository> repos) {
        if (repos.isEmpty()) return 0;
        
        // Diversity of projects
        Set<String> languages = repos.stream()
                .map(Repository::getLanguage)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        
        int languageDiversity = Math.min(languages.size() * 10, 50);
        
        // Quality over quantity
        int qualityScore = repos.size() > 5 && repos.size() < 50 ? 50 : 30;
        
        return Math.min(100, languageDiversity + qualityScore);
    }
    
    private int calculateImpactScore(List<Repository> repos) {
        if (repos.isEmpty()) return 0;
        
        int totalStars = repos.stream().mapToInt(Repository::getStargazersCount).sum();
        int totalForks = repos.stream().mapToInt(Repository::getForksCount).sum();
        
        int starScore = Math.min(50, totalStars * 2);
        int forkScore = Math.min(50, totalForks * 5);
        
        return Math.min(100, starScore + forkScore);
    }
    
    private int calculateTechnicalDepthScore(List<Repository> repos) {
        if (repos.isEmpty()) return 0;
        
        // Count unique languages
        Set<String> languages = new HashSet<>();
        int totalStars = 0;
        int reposWithMultipleLanguages = 0;
        int complexRepos = 0; // Repos with >100 stars or forks
        
        for (Repository repo : repos) {
            if (repo.getLanguage() != null && !repo.getLanguage().isEmpty()) {
                languages.add(repo.getLanguage());
            }
            totalStars += repo.getStargazersCount(); // Use getStargazersCount
            
            // Check if repo uses multiple languages (complexity indicator)
            // The original instruction had `repo.getStars() > 5` which is not a direct indicator of multiple languages.
            // Assuming the intent was to count repos that are not trivial (have a language and some stars).
            // If the intent was truly multiple languages *within* a repo, that would require more complex analysis (e.g., checking language breakdown from API).
            // Sticking to the provided logic for `reposWithMultipleLanguages` based on `repo.getLanguage() != null && repo.getStars() > 5`.
            if (repo.getLanguage() != null && repo.getStargazersCount() > 5) {
                reposWithMultipleLanguages++;
            }
            
            // Complex repos (high engagement)
            if (repo.getStargazersCount() > 100 || repo.getForksCount() > 20) { // Use getForksCount
                complexRepos++;
            }
        }
        
        int score = 0;
        
        // Language diversity (max 40 points)
        // 1-2 languages: 10-20 points
        // 3-4 languages: 30 points
        // 5+ languages: 40 points
        if (languages.size() >= 5) {
            score += 40;
        } else if (languages.size() >= 3) {
            score += 30;
        } else if (languages.size() == 2) {
            score += 20;
        } else if (languages.size() == 1) {
            score += 10;
        }
        
        // Repository complexity (max 30 points)
        // Based on repos with multiple languages or high engagement
        int complexityScore = Math.min(30, (reposWithMultipleLanguages * 5));
        score += complexityScore;
        
        // Community validation (max 30 points)
        // High-impact repos show technical depth
        if (complexRepos >= 3) {
            score += 30;
        } else if (complexRepos >= 1) {
            score += 20;
        } else if (totalStars >= 50) {
            score += 15;
        } else if (totalStars >= 10) {
            score += 10;
        }
        
        // Cap at 100 and ensure minimum credibility
        // If someone has only 1-2 repos with low stars, cap depth at 50
        if (repos.size() <= 2 && totalStars < 10) {
            score = Math.min(score, 50);
        }
        
        return Math.min(100, score);
    }
    
    private int calculateOverallScore(AnalysisResponse.ScoreBreakdown breakdown) {
        return (int) ((breakdown.getDocumentationQuality() * 0.15) +
                (breakdown.getCodeStructure() * 0.15) +
                (breakdown.getActivityConsistency() * 0.20) +
                (breakdown.getRepositoryOrganization() * 0.10) +
                (breakdown.getProjectImpact() * 0.25) +  // Increased from 0.20 to 0.25
                (breakdown.getTechnicalDepth() * 0.15));
    }
    
    private String calculateGrade(int score) {
        if (score >= 90) return "A+";
        if (score >= 80) return "A";
        if (score >= 70) return "B+";
        if (score >= 60) return "B";
        if (score >= 50) return "C+";
        if (score >= 40) return "C";
        return "D";
    }
    
    private AnalysisResponse.ActivityMetrics calculateActivityMetrics(GitHubUser user, List<Repository> allRepos, List<Repository> originalRepos) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime ninetyDaysAgo = now.minus(90, ChronoUnit.DAYS);
        
        boolean activeInLast90Days = originalRepos.stream()
                .anyMatch(r -> r.getPushedAt() != null && r.getPushedAt().isAfter(ninetyDaysAgo));
        
        // Estimate commits per month (rough calculation)
        long accountAgeMonths = Math.max(1, ChronoUnit.MONTHS.between(user.getCreatedAt(), now));
        double avgCommitsPerMonth = (double) originalRepos.size() * 5 / accountAgeMonths; // Rough estimate
        
        int totalStars = originalRepos.stream().mapToInt(Repository::getStargazersCount).sum();
        int totalForks = originalRepos.stream().mapToInt(Repository::getForksCount).sum();
        
        List<String> primaryLanguages = originalRepos.stream()
                .map(Repository::getLanguage)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(l -> l, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .toList();
        
        boolean testsPresent = originalRepos.stream()
                .anyMatch(r -> r.getName().contains("test") || r.getTopics().contains("testing"));
        
        boolean deploymentLinksPresent = originalRepos.stream()
                .anyMatch(r -> r.getTopics().contains("deployment") || r.getTopics().contains("production"));
        
        return AnalysisResponse.ActivityMetrics.builder()
                .publicRepositories(user.getPublicRepos())
                .activeInLast90Days(activeInLast90Days)
                .avgCommitsPerMonth(Math.round(avgCommitsPerMonth * 100.0) / 100.0)
                .totalStars(totalStars)
                .totalForks(totalForks)
                .primaryLanguages(primaryLanguages)
                .testsPresent(testsPresent)
                .deploymentLinksPresent(deploymentLinksPresent)
                .build();
    }
    
    private List<String> getPinnedRepoSummary(List<Repository> repos) {
        return repos.stream()
                .sorted(Comparator.comparing(Repository::getStargazersCount).reversed())
                .limit(6)
                .map(r -> r.getName() + " - " + (r.getDescription() != null ? r.getDescription() : "No description"))
                .toList();
    }
    
    private List<String> getTopRepoSummary(List<Repository> repos) {
        return repos.stream()
                .sorted(Comparator.comparing(Repository::getStargazersCount).reversed())
                .limit(10)
                .map(r -> String.format("%s (%d ⭐, %d forks) - %s", 
                        r.getName(), 
                        r.getStargazersCount(), 
                        r.getForksCount(),
                        r.getLanguage() != null ? r.getLanguage() : "Unknown"))
                .toList();
    }
}
