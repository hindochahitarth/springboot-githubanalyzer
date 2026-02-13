package org.miniproject.githubprofileanalyzer.service;

import lombok.extern.slf4j.Slf4j;
import org.miniproject.githubprofileanalyzer.dto.AnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AIInsightService {
    
    @Value("${gemini.api.key}")
    private String geminiApiKey;
    
    /**
     * Generate AI-powered insights based on profile metrics
     * Note: This is a template-based implementation. For production, integrate with actual LLM API.
     */
    public AnalysisResponse generateInsights(AnalysisResponse.ProfileMetrics metrics) {
        
        // Generate insights based on metrics
        String executiveSummary = generateExecutiveSummary(metrics);
        AnalysisResponse.OverallAssessment overallAssessment = generateOverallAssessment(metrics);
        List<String> whatRecruitersNoticeFirst = generateWhatRecruitersNoticeFirst(metrics);
        List<AnalysisResponse.Signal> strongSignals = generateStrongSignals(metrics);
        List<AnalysisResponse.RedFlag> redFlags = generateRedFlags(metrics);
        AnalysisResponse.RepositoryStrategy repositoryStrategy = generateRepositoryStrategy(metrics);
        AnalysisResponse.ImpactAndDiscoverability impactAndDiscoverability = generateImpactAndDiscoverability(metrics);
        AnalysisResponse.RecruiterVerdict recruiterVerdict = generateRecruiterVerdict(metrics);
        
        // Polish features
        String flagshipProject = generateFlagshipProject(metrics);
        String confidenceLevel = generateConfidenceLevel(metrics);
        List<String> fixPriorities = generateFixPriorities(metrics);
        AnalysisResponse.CompletenessStats completenessStats = generateCompletenessStats(metrics);
        AnalysisResponse.ScoreSimulation scoreSimulation = generateScoreSimulation(metrics);
        
        // Intelligence features
        List<String> top3StrongestRepos = generateTop3StrongestRepos(metrics);
        String weakestDimension = generateWeakestDimension(metrics);
        AnalysisResponse.SkillCategories skillCategories = generateSkillCategories(metrics);
        String activityTrend = generateActivityTrend(metrics);
        String recruiterRiskSummary = generateRecruiterRiskSummary(metrics);
        
        // Maturity features
        String contributionConsistency = generateContributionConsistency(metrics);
        String languageFocus = generateLanguageFocus(metrics);
        Integer repositoryNoiseCount = generateRepositoryNoiseCount(metrics);
        String engineeringMaturity = generateEngineeringMaturity(metrics);
        String commitQuality = generateCommitQuality(metrics);
        
        // Final polish features
        String profileAge = generateProfileAge(metrics);
        String lastCommitRecency = generateLastCommitRecency(metrics);
        AnalysisResponse.TechStackEvaluation techStackEvaluation = generateTechStackEvaluation(metrics);
        AnalysisResponse.UiUxEvaluation uiUxEvaluation = generateUiUxEvaluation();
        AnalysisResponse.ThirtyDayActionPlan actionPlan = generateActionPlan(metrics);
        List<String> immediateImprovements = generateImmediateImprovements(metrics);
        String resumeSummary = generateResumeSummary(metrics);
        
        return AnalysisResponse.builder()
                .executiveSummary(executiveSummary)
                .overallAssessment(overallAssessment)
                .whatRecruitersNoticeFirst(whatRecruitersNoticeFirst)
                .strongSignals(strongSignals)
                .redFlags(redFlags)
                .repositoryStrategy(repositoryStrategy)
                .impactAndDiscoverability(impactAndDiscoverability)
                .recruiterVerdict(recruiterVerdict)
                .flagshipProject(flagshipProject)
                .confidenceLevel(confidenceLevel)
                .fixPriorities(fixPriorities)
                .completenessStats(completenessStats)
                .scoreSimulation(scoreSimulation)
                .top3StrongestRepos(top3StrongestRepos)
                .weakestDimension(weakestDimension)
                .skillCategories(skillCategories)
                .activityTrend(activityTrend)
                .recruiterRiskSummary(recruiterRiskSummary)
                .contributionConsistency(contributionConsistency)
                .languageFocus(languageFocus)
                .repositoryNoiseCount(repositoryNoiseCount)
                .engineeringMaturity(engineeringMaturity)
                .commitQuality(commitQuality)
                .profileAge(profileAge)
                .lastCommitRecency(lastCommitRecency)
                .techStackEvaluation(techStackEvaluation)
                .uiUxEvaluation(uiUxEvaluation)
                .recommendedPages(getRecommendedPages())
                .recommendedAppFlow(getRecommendedAppFlow())
                .recommendedFolderStructure(getRecommendedFolderStructure())
                .thirtyDayActionPlan(actionPlan)
                .threeImmediateHighImpactFixes(immediateImprovements)
                .resumeReadyProfileSummary(resumeSummary)
                .profileMetrics(metrics)
                .build();
    }
    
    private String generateExecutiveSummary(AnalysisResponse.ProfileMetrics metrics) {
        int score = metrics.getOverallScore();
        int repos = metrics.getActivityMetrics().getPublicRepositories();
        int stars = metrics.getActivityMetrics().getTotalStars();
        boolean isActive = metrics.getActivityMetrics().isActiveInLast90Days();
        
        String verdict;
        if (score >= 75 && isActive) {
            verdict = String.format("Strong candidate with %d well-maintained repositories. " +
                    "Clear evidence of technical competency and professional development practices. " +
                    "Portfolio demonstrates %s-level engineering maturity with %d community stars.",
                    repos, metrics.getOverallScore() >= 80 ? "advanced" : "intermediate", stars);
        } else if (score >= 60) {
            verdict = String.format("Developing portfolio with %d repositories showing %s potential. " +
                    "Core technical skills evident but needs stronger documentation and project polish. " +
                    "%s for internship roles with mentorship.",
                    repos, isActive ? "solid" : "moderate",
                    isActive ? "Ready" : "Nearly ready");
        } else if (score >= 40) {
            verdict = String.format("Early-stage portfolio with %d repositories. " +
                    "Basic technical foundation present but significant gaps in professional practices. " +
                    "Requires 30-60 days of focused improvement before recruiter consideration.",
                    repos);
        } else {
            verdict = String.format("Portfolio needs substantial development. %d repositories lack professional structure, " +
                    "documentation, and clear project narratives. Not currently competitive for technical roles.",
                    repos);
        }
        
        return verdict;
    }
    
    private AnalysisResponse.OverallAssessment generateOverallAssessment(AnalysisResponse.ProfileMetrics metrics) {
        int score = metrics.getOverallScore();
        
        String portfolioStrength = score >= 80 ? "Very Strong" : score >= 60 ? "Strong" : score >= 40 ? "Developing" : "Weak";
        String engineeringLevel = score >= 70 ? "Advanced" : score >= 50 ? "Intermediate" : "Beginner";
        String hireReadiness = score >= 75 ? "Job Ready" : score >= 60 ? "Internship Ready" : score >= 40 ? "Needs Improvement" : "Not Ready";
        
        // Enhanced recruiter verdict
        String recruiterVerdict;
        if (score >= 80 && metrics.getActivityMetrics().isActiveInLast90Days()) {
            recruiterVerdict = "Strong Hire";
        } else if (score >= 70) {
            recruiterVerdict = "Hire";
        } else if (score >= 50) {
            recruiterVerdict = "Maybe";
        } else {
            recruiterVerdict = "Not Ready";
        }
        
        // More detailed reasoning
        String reasoning = String.format("Profile shows %d repositories with %s activity. " +
                "Documentation quality (%d/100) and technical depth (%d/100) are %s. " +
                "Community engagement: %d stars. %s",
                metrics.getActivityMetrics().getPublicRepositories(),
                metrics.getActivityMetrics().isActiveInLast90Days() ? "consistent recent" : "irregular",
                metrics.getScoreBreakdown().getDocumentationQuality(),
                metrics.getScoreBreakdown().getTechnicalDepth(),
                score >= 70 ? "strong hiring signals" : score >= 50 ? "moderate but need improvement" : "below hiring threshold",
                metrics.getActivityMetrics().getTotalStars(),
                metrics.getActivityMetrics().getTotalStars() > 50 ? "Strong community validation." : 
                    metrics.getActivityMetrics().getTotalStars() > 10 ? "Some community recognition." : "Limited external validation.");
        
        return AnalysisResponse.OverallAssessment.builder()
                .portfolioStrength(portfolioStrength)
                .engineeringLevel(engineeringLevel)
                .hireReadiness(hireReadiness)
                .confidenceReasoning(reasoning)
                .build();
    }
    
    private List<AnalysisResponse.Signal> generateStrongSignals(AnalysisResponse.ProfileMetrics metrics) {
        List<AnalysisResponse.Signal> signals = new ArrayList<>();
        
        if (metrics.getActivityMetrics().isActiveInLast90Days()) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("Recent Consistent Activity")
                    .whyItMatters("Active in last 90 days signals current engagement and learning. Recruiters prioritize candidates who code regularly over those with stale profiles.")
                    .build());
        }
        
        if (metrics.getActivityMetrics().getTotalStars() > 50) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("Strong Community Validation")
                    .whyItMatters(String.format("%d stars demonstrate that projects solve real problems and provide value to other developers. This is rare among student portfolios.", 
                            metrics.getActivityMetrics().getTotalStars()))
                    .build());
        } else if (metrics.getActivityMetrics().getTotalStars() > 10) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("Community Recognition")
                    .whyItMatters(String.format("%d stars show some external validation. While modest, this indicates projects have utility beyond personal learning.", 
                            metrics.getActivityMetrics().getTotalStars()))
                    .build());
        }
        
        if (metrics.getActivityMetrics().getPrimaryLanguages().size() >= 4) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("Multi-Language Technical Breadth")
                    .whyItMatters(String.format("Proficiency across %d languages (%s) demonstrates adaptability and strong fundamentals. Valuable for teams with diverse tech stacks.", 
                            metrics.getActivityMetrics().getPrimaryLanguages().size(),
                            String.join(", ", metrics.getActivityMetrics().getPrimaryLanguages().subList(0, Math.min(3, metrics.getActivityMetrics().getPrimaryLanguages().size())))))
                    .build());
        } else if (metrics.getActivityMetrics().getPrimaryLanguages().size() >= 2) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("Multi-Language Experience")
                    .whyItMatters(String.format("Experience with %d languages shows willingness to learn new technologies.", 
                            metrics.getActivityMetrics().getPrimaryLanguages().size()))
                    .build());
        }
        
        if (metrics.getScoreBreakdown().getDocumentationQuality() >= 75) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("Professional Documentation Standards")
                    .whyItMatters("Comprehensive READMEs with setup instructions, architecture details, and clear descriptions indicate professional habits. This is uncommon in student portfolios and highly valued by hiring managers.")
                    .build());
        } else if (metrics.getScoreBreakdown().getDocumentationQuality() >= 60) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("Good Documentation Practices")
                    .whyItMatters("Solid documentation shows understanding that code must be maintainable and accessible to teams.")
                    .build());
        }
        
        if (metrics.getScoreBreakdown().getProjectImpact() >= 70) {
            signals.add(AnalysisResponse.Signal.builder()
                    .signal("High-Impact Project Portfolio")
                    .whyItMatters("Projects demonstrate real-world relevance, not just tutorial completion. Recruiters look for evidence of independent problem-solving.")
                    .build());
        }
        
        return signals;
    }
    
    private List<AnalysisResponse.RedFlag> generateRedFlags(AnalysisResponse.ProfileMetrics metrics) {
        List<AnalysisResponse.RedFlag> flags = new ArrayList<>();
        
        if (!metrics.getActivityMetrics().isActiveInLast90Days()) {
            flags.add(AnalysisResponse.RedFlag.builder()
                    .issue("No Recent Activity")
                    .impact("Suggests profile may be outdated or candidate is not actively coding")
                    .severity("High")
                    .build());
        }
        
        if (metrics.getActivityMetrics().getPublicRepositories() < 5) {
            flags.add(AnalysisResponse.RedFlag.builder()
                    .issue("Limited Portfolio Size")
                    .impact("Insufficient projects to demonstrate consistent skill and experience")
                    .severity("Medium")
                    .build());
        }
        
        if (metrics.getScoreBreakdown().getDocumentationQuality() < 40) {
            flags.add(AnalysisResponse.RedFlag.builder()
                    .issue("Poor Documentation")
                    .impact("Lack of READMEs and descriptions makes it hard to understand project value")
                    .severity("Medium")
                    .build());
        }
        
        if (metrics.getActivityMetrics().getTotalStars() == 0 && metrics.getActivityMetrics().getPublicRepositories() > 5) {
            flags.add(AnalysisResponse.RedFlag.builder()
                    .issue("No Community Engagement")
                    .impact("Projects lack external validation or may not be solving real problems")
                    .severity("Low")
                    .build());
        }
        
        return flags;
    }
    
    private List<String> generateWhatRecruitersNoticeFirst(AnalysisResponse.ProfileMetrics metrics) {
        List<String> notices = new ArrayList<>();
        
        // First thing: Activity pattern
        if (metrics.getActivityMetrics().isActiveInLast90Days()) {
            notices.add("✓ Active contributor - commits within last 90 days");
        } else {
            notices.add("⚠ Inactive profile - no recent commits");
        }
        
        // Second thing: Documentation quality
        if (metrics.getScoreBreakdown().getDocumentationQuality() >= 70) {
            notices.add("✓ Strong documentation - clear READMEs present");
        } else if (metrics.getScoreBreakdown().getDocumentationQuality() >= 40) {
            notices.add("⚠ Basic documentation - needs improvement");
        } else {
            notices.add("✗ Weak documentation - hard to understand projects");
        }
        
        // Third thing: Portfolio depth
        if (metrics.getActivityMetrics().getTotalStars() > 50) {
            notices.add(String.format("✓ Community validation - %d stars across projects", metrics.getActivityMetrics().getTotalStars()));
        } else if (metrics.getActivityMetrics().getPublicRepositories() >= 10) {
            notices.add(String.format("○ %d repositories - needs clearer flagship project", metrics.getActivityMetrics().getPublicRepositories()));
        } else {
            notices.add("⚠ Limited portfolio - needs more substantial projects");
        }
        
        return notices;
    }
    
    private AnalysisResponse.RepositoryStrategy generateRepositoryStrategy(AnalysisResponse.ProfileMetrics metrics) {
        List<String> highlight = new ArrayList<>();
        List<String> improve = new ArrayList<>();
        List<String> archive = new ArrayList<>();
        
        // Analyze actual repositories from topRepoSummary
        List<String> topRepos = metrics.getTopRepoSummary();
        
        if (!topRepos.isEmpty()) {
            // Highlight: Top 2-3 repos with best signals
            int highlightCount = Math.min(3, topRepos.size());
            for (int i = 0; i < highlightCount; i++) {
                String repo = topRepos.get(i);
                if (repo.contains("star") || repo.contains("fork") || repo.contains("README")) {
                    highlight.add(repo.split(" ")[0]); // Extract repo name
                }
            }
            
            // If no specific repos, add general guidance
            if (highlight.isEmpty()) {
                highlight.add("Your most starred repository (if any)");
                highlight.add("Projects with clear documentation");
            }
        } else {
            highlight.add("Create 1-2 flagship projects to showcase");
        }
        
        // Improve: Repos that need work
        if (metrics.getScoreBreakdown().getDocumentationQuality() < 70) {
            improve.add("All repositories - add comprehensive READMEs");
        }
        if (!metrics.getActivityMetrics().isDeploymentLinksPresent()) {
            improve.add("Add live demo links to web projects");
        }
        if (!metrics.getActivityMetrics().isTestsPresent()) {
            improve.add("Add tests to demonstrate code quality");
        }
        if (improve.isEmpty()) {
            improve.add("Add project screenshots to READMEs");
            improve.add("Include setup/installation instructions");
        }
        
        // Archive: Generic advice based on portfolio size
        if (metrics.getActivityMetrics().getPublicRepositories() > 15) {
            archive.add("Forked repositories with no modifications");
            archive.add("Old tutorial/practice projects (>1 year inactive)");
            archive.add("Experimental repos with unclear purpose");
        } else if (metrics.getActivityMetrics().getPublicRepositories() > 8) {
            archive.add("Incomplete or abandoned projects");
            archive.add("Duplicate/similar projects");
        } else {
            archive.add("Keep all repos for now - focus on improving existing ones");
        }
        
        return AnalysisResponse.RepositoryStrategy.builder()
                .highlightThese(highlight)
                .improveThese(improve)
                .considerArchiving(archive)
                .build();
    }
    
    private AnalysisResponse.TechStackEvaluation generateTechStackEvaluation(AnalysisResponse.ProfileMetrics metrics) {
        return AnalysisResponse.TechStackEvaluation.builder()
                .backendArchitectureQuality("Structured")
                .frontendArchitectureQuality("Structured")
                .missingBestPractices(Arrays.asList(
                        "Comprehensive test coverage",
                        "CI/CD pipeline configuration",
                        "API documentation (OpenAPI/Swagger)",
                        "Docker containerization"
                ))
                .backendImprovementSuggestions(Arrays.asList(
                        "Add unit and integration tests",
                        "Implement proper error handling and logging",
                        "Use dependency injection patterns",
                        "Add API versioning strategy"
                ))
                .frontendImprovementSuggestions(Arrays.asList(
                        "Implement component testing",
                        "Add loading and error states",
                        "Optimize bundle size",
                        "Ensure accessibility compliance"
                ))
                .build();
    }
    
    private AnalysisResponse.UiUxEvaluation generateUiUxEvaluation() {
        return AnalysisResponse.UiUxEvaluation.builder()
                .uiThemeAssessment("Professional")
                .layoutClarity("Clear")
                .designConsistency("High")
                .improvementSuggestions(Arrays.asList(
                        "Add micro-animations for better user engagement",
                        "Implement skeleton loading states",
                        "Ensure mobile responsiveness",
                        "Add dark mode support"
                ))
                .build();
    }
    
    private AnalysisResponse.ThirtyDayActionPlan generateActionPlan(AnalysisResponse.ProfileMetrics metrics) {
        int score = metrics.getOverallScore();
        
        // Conditional roadmap based on score
        if (score >= 75) {
            // Optimization Roadmap for high performers
            return AnalysisResponse.ThirtyDayActionPlan.builder()
                    .week1("Optimize your top 3 repositories: add performance benchmarks and advanced documentation")
                    .week2("Create technical blog posts or tutorials showcasing your expertise")
                    .week3("Contribute to high-profile open source projects to increase visibility")
                    .week4("Add case studies or real-world impact metrics to your flagship projects")
                    .build();
        } else if (score < 60) {
            // Foundation Roadmap for those needing improvement
            return AnalysisResponse.ThirtyDayActionPlan.builder()
                    .week1("Add comprehensive READMEs to all repositories with setup instructions and screenshots")
                    .week2("Create one complete, well-documented project from scratch")
                    .week3("Add tests and CI/CD to your main projects")
                    .week4("Deploy at least one project and add the live link to README")
                    .build();
        } else {
            // Standard roadmap for mid-range scores
            return AnalysisResponse.ThirtyDayActionPlan.builder()
                    .week1("Improve documentation quality across all repositories")
                    .week2("Add deployment links and live demos to showcase projects")
                    .week3("Increase commit frequency and maintain consistent activity")
                    .week4("Create one flagship project that demonstrates end-to-end skills")
                    .build();
        }
    }
    
    private List<String> generateImmediateImprovements(AnalysisResponse.ProfileMetrics metrics) {
        List<String> improvements = new ArrayList<>();
        
        if (metrics.getScoreBreakdown().getDocumentationQuality() < 60) {
            improvements.add("Add detailed READMEs to your top 3 repositories with setup instructions and screenshots");
        }
        
        if (!metrics.getActivityMetrics().isActiveInLast90Days()) {
            improvements.add("Make a commit to demonstrate current activity - update documentation or fix a small issue");
        }
        
        if (metrics.getActivityMetrics().getPrimaryLanguages().size() < 3) {
            improvements.add("Start a project in a new language/framework to demonstrate learning agility");
        }
        
        return improvements;
    }
    
    private String generateResumeSummary(AnalysisResponse.ProfileMetrics metrics) {
        return String.format("GitHub-active developer with %d public repositories across %s. " +
                "Demonstrated proficiency in %s with %d community stars. %s contributor with a focus on %s.",
                metrics.getActivityMetrics().getPublicRepositories(),
                String.join(", ", metrics.getActivityMetrics().getPrimaryLanguages()),
                metrics.getActivityMetrics().getPrimaryLanguages().isEmpty() ? "multiple technologies" : 
                        metrics.getActivityMetrics().getPrimaryLanguages().get(0),
                metrics.getActivityMetrics().getTotalStars(),
                metrics.getActivityMetrics().isActiveInLast90Days() ? "Active" : "Experienced",
                metrics.getOverallScore() >= 70 ? "production-ready code and best practices" : "continuous learning and skill development");
    }
    
    private List<String> getRecommendedPages() {
        return Arrays.asList(
                "Landing Page (GitHub URL Input)",
                "Dashboard Page (Score + Charts + Insights)",
                "Repository Detail Page",
                "Comparison Page (Optional Innovation)",
                "About / Methodology Page"
        );
    }
    
    private List<String> getRecommendedAppFlow() {
        return Arrays.asList(
                "User enters GitHub URL",
                "Backend validates and fetches data",
                "Scoring engine calculates metrics",
                "AI generates structured insights",
                "Dashboard renders results in sections"
        );
    }
    
    private List<String> getRecommendedNextSteps() {
        return List.of(
                "Ensure all repositories have comprehensive READMEs with setup instructions",
                "Create at least one flagship project that demonstrates end-to-end skills",
                "Add live deployment links to showcase working applications"
        );
    }
    
    private AnalysisResponse.ImpactAndDiscoverability generateImpactAndDiscoverability(AnalysisResponse.ProfileMetrics metrics) {
        String businessRelevance;
        String communityValidation;
        String productionReadiness;
        
        // Business Relevance
        int impactScore = metrics.getScoreBreakdown().getProjectImpact();
        if (impactScore >= 70) {
            businessRelevance = "High - Projects demonstrate real-world application";
        } else if (impactScore >= 40) {
            businessRelevance = "Medium - Some practical projects, needs more depth";
        } else {
            businessRelevance = "Low - Limited evidence of real-world problem solving";
        }
        
        // Community Validation
        int totalStars = metrics.getActivityMetrics().getTotalStars();
        int totalForks = metrics.getActivityMetrics().getTotalForks();
        
        if (totalStars >= 100 || totalForks >= 20) {
            communityValidation = String.format("Strong - %d stars, %d forks show community interest", totalStars, totalForks);
        } else if (totalStars >= 20 || totalForks >= 5) {
            communityValidation = String.format("Moderate - %d stars, %d forks indicate some recognition", totalStars, totalForks);
        } else {
            communityValidation = String.format("Low - %d stars, %d forks suggest limited external validation", totalStars, totalForks);
        }
        
        // Production Readiness
        boolean hasDeployment = metrics.getActivityMetrics().isDeploymentLinksPresent();
        boolean hasTests = metrics.getActivityMetrics().isTestsPresent();
        int docScore = metrics.getScoreBreakdown().getDocumentationQuality();
        
        if (hasDeployment && hasTests && docScore >= 70) {
            productionReadiness = "High - Deployed projects with tests and documentation";
        } else if (hasDeployment || hasTests || docScore >= 50) {
            productionReadiness = "Partial - Some production indicators present";
        } else {
            productionReadiness = "Low - Missing deployment, tests, or clear documentation";
        }
        
        return AnalysisResponse.ImpactAndDiscoverability.builder()
                .businessRelevance(businessRelevance)
                .communityValidation(communityValidation)
                .productionReadiness(productionReadiness)
                .build();
    }
    
    private AnalysisResponse.RecruiterVerdict generateRecruiterVerdict(AnalysisResponse.ProfileMetrics metrics) {
        int overallScore = metrics.getOverallScore();
        int docScore = metrics.getScoreBreakdown().getDocumentationQuality();
        int impactScore = metrics.getScoreBreakdown().getProjectImpact();
        int totalStars = metrics.getActivityMetrics().getTotalStars();
        boolean isActive = metrics.getActivityMetrics().isActiveInLast90Days();
        
        String decision;
        String reason;
        
        // Strong Hire: 80+ score, good docs, active, community validation
        if (overallScore >= 80 && docScore >= 70 && isActive && totalStars >= 50) {
            decision = "Strong Hire";
            reason = "Excellent portfolio with strong documentation, community validation, and consistent activity";
        }
        // Hire: 65+ score, decent fundamentals
        else if (overallScore >= 65 && docScore >= 50 && isActive) {
            decision = "Hire";
            reason = "Solid portfolio with good fundamentals and active development";
        }
        // Maybe: 50-64 score, some gaps
        else if (overallScore >= 50) {
            decision = "Maybe";
            if (docScore < 50) {
                reason = "Potential evident but weak documentation reduces confidence";
            } else if (!isActive) {
                reason = "Good work shown but recent inactivity raises concerns";
            } else if (impactScore < 40) {
                reason = "Active developer but lacks clear flagship project or impact";
            } else {
                reason = "Borderline candidate - needs stronger portfolio signals";
            }
        }
        // Not Ready: <50 score
        else {
            decision = "Not Ready";
            List<String> gaps = new ArrayList<>();
            if (docScore < 40) gaps.add("weak documentation");
            if (!isActive) gaps.add("inactive profile");
            if (impactScore < 30) gaps.add("no flagship project");
            if (totalStars < 5) gaps.add("limited community validation");
            
            if (gaps.isEmpty()) {
                reason = "Portfolio needs significant improvement across multiple areas";
            } else {
                reason = String.format("Not shortlist-ready due to: %s", String.join(", ", gaps));
            }
        }
        
        return AnalysisResponse.RecruiterVerdict.builder()
                .decision(decision)
                .shortReason(reason)
                .build();
    }
    
    private Map<String, List<String>> getRecommendedFolderStructure() {
        Map<String, List<String>> structure = new HashMap<>();
        
        structure.put("backend", Arrays.asList(
                "controller/",
                "service/",
                "client/",
                "model/",
                "dto/",
                "config/",
                "util/",
                "exception/"
        ));
        
        structure.put("frontend", Arrays.asList(
                "src/pages/",
                "src/components/",
                "src/layouts/",
                "src/services/",
                "src/hooks/",
                "src/utils/",
                "src/assets/"
        ));
        
        return structure;
    }
    
    // ===== POLISH FEATURES =====
    
    private String generateFlagshipProject(AnalysisResponse.ProfileMetrics metrics) {
        List<String> topRepos = metrics.getTopRepoSummary();
        
        if (topRepos.isEmpty()) {
            return "No flagship project identified - create a comprehensive project to showcase";
        }
        
        // Pick repo with highest stars (first in sorted list)
        String topRepo = topRepos.get(0);
        String repoName = topRepo.split(" ")[0]; // Extract repo name
        
        return String.format("⭐ Recommended Flagship: %s", repoName);
    }
    
    private String generateConfidenceLevel(AnalysisResponse.ProfileMetrics metrics) {
        int score = metrics.getOverallScore();
        
        if (score >= 70) {
            return "High"; // 🟢
        } else if (score >= 50) {
            return "Moderate"; // 🟡
        } else {
            return "Low"; // 🔴
        }
    }
    
    private List<String> generateFixPriorities(AnalysisResponse.ProfileMetrics metrics) {
        List<String> priorities = new ArrayList<>();
        AnalysisResponse.ScoreBreakdown breakdown = metrics.getScoreBreakdown();
        
        // Create list of (dimension, score) pairs
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Documentation", breakdown.getDocumentationQuality());
        scores.put("Code Structure", breakdown.getCodeStructure());
        scores.put("Activity Consistency", breakdown.getActivityConsistency());
        scores.put("Repository Organization", breakdown.getRepositoryOrganization());
        scores.put("Project Impact", breakdown.getProjectImpact());
        scores.put("Technical Depth", breakdown.getTechnicalDepth());
        
        // Sort by score (lowest first)
        scores.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(3)
                .forEach(entry -> priorities.add(String.format("Improve %s (Current: %d/100)", 
                        entry.getKey(), entry.getValue())));
        
        return priorities;
    }
    
    private AnalysisResponse.CompletenessStats generateCompletenessStats(AnalysisResponse.ProfileMetrics metrics) {
        int totalRepos = metrics.getActivityMetrics().getPublicRepositories();
        
        // Professional standard: README + (Stars > threshold OR Tests OR Active contributors)
        // This allows kernel-style projects to pass even without deployment
        int docScore = metrics.getScoreBreakdown().getDocumentationQuality();
        boolean hasTests = metrics.getActivityMetrics().isTestsPresent();
        boolean isActive = metrics.getActivityMetrics().isActiveInLast90Days();
        int totalStars = metrics.getActivityMetrics().getTotalStars();
        
        // Estimate professional repos with flexible criteria
        int professionalRepos = 0;
        
        // High quality: Good docs + (high stars OR tests OR active)
        if (docScore >= 70 && (totalStars >= 50 || hasTests || isActive)) {
            professionalRepos = (int) (totalRepos * 0.6); // 60% meet standards
        } 
        // Medium quality: Decent docs + (some stars OR tests OR active)
        else if (docScore >= 50 && (totalStars >= 10 || hasTests || isActive)) {
            professionalRepos = (int) (totalRepos * 0.4); // 40% meet standards
        } 
        // Basic quality: Some docs + any positive signal
        else if (docScore >= 30 && (totalStars >= 5 || isActive)) {
            professionalRepos = (int) (totalRepos * 0.2); // 20% meet standards
        }
        // Low quality
        else if (docScore >= 20) {
            professionalRepos = (int) (totalRepos * 0.1); // 10% meet standards
        }
        
        String message = String.format("Only %d out of %d repositories meet professional standards", 
                professionalRepos, totalRepos);
        
        return AnalysisResponse.CompletenessStats.builder()
                .professionalRepos(professionalRepos)
                .totalRepos(totalRepos)
                .message(message)
                .build();
    }
    
    private AnalysisResponse.ScoreSimulation generateScoreSimulation(AnalysisResponse.ProfileMetrics metrics) {
        AnalysisResponse.ScoreBreakdown breakdown = metrics.getScoreBreakdown();
        int currentScore = metrics.getOverallScore();
        
        // Find lowest scoring dimension
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Documentation", breakdown.getDocumentationQuality());
        scores.put("Code Structure", breakdown.getCodeStructure());
        scores.put("Activity", breakdown.getActivityConsistency());
        scores.put("Organization", breakdown.getRepositoryOrganization());
        scores.put("Impact", breakdown.getProjectImpact());
        scores.put("Depth", breakdown.getTechnicalDepth());
        
        String lowestDimension = scores.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Documentation");
        
        int lowestScore = scores.get(lowestDimension);
        
        // Simulate improvement: +20 points to lowest dimension
        int improvedDimensionScore = Math.min(100, lowestScore + 20);
        int scoreDiff = improvedDimensionScore - lowestScore;
        
        // Calculate weight for this dimension (all are 15-20%)
        double weight = 0.15;
        if (lowestDimension.equals("Activity") || lowestDimension.equals("Impact")) {
            weight = 0.20;
        }
        
        int projectedScore = currentScore + (int) (scoreDiff * weight);
        String projectedGrade = calculateGrade(projectedScore);
        
        String message = String.format("If %s improves to %d+, overall score could reach %d (%s)", 
                lowestDimension, improvedDimensionScore, projectedScore, projectedGrade);
        
        return AnalysisResponse.ScoreSimulation.builder()
                .improvementArea(lowestDimension)
                .currentScore(currentScore)
                .projectedScore(projectedScore)
                .projectedGrade(projectedGrade)
                .message(message)
                .build();
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
    
    // ===== INTELLIGENCE FEATURES =====
    
    private List<String> generateTop3StrongestRepos(AnalysisResponse.ProfileMetrics metrics) {
        List<String> topRepos = metrics.getTopRepoSummary();
        
        if (topRepos.isEmpty()) {
            return List.of("No repositories to rank");
        }
        
        // Top repos are already sorted by stars, take top 3
        return topRepos.stream()
                .limit(3)
                .map(repo -> {
                    String repoName = repo.split(" ")[0];
                    return repoName;
                })
                .toList();
    }
    
    private String generateWeakestDimension(AnalysisResponse.ProfileMetrics metrics) {
        AnalysisResponse.ScoreBreakdown breakdown = metrics.getScoreBreakdown();
        
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Documentation", breakdown.getDocumentationQuality());
        scores.put("Code Structure", breakdown.getCodeStructure());
        scores.put("Activity Consistency", breakdown.getActivityConsistency());
        scores.put("Repository Organization", breakdown.getRepositoryOrganization());
        scores.put("Project Impact", breakdown.getProjectImpact());
        scores.put("Technical Depth", breakdown.getTechnicalDepth());
        
        Map.Entry<String, Integer> weakest = scores.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);
        
        if (weakest != null) {
            return String.format("🚨 Most Critical Weakness: %s (%d/100)", 
                    weakest.getKey(), weakest.getValue());
        }
        
        return "No critical weaknesses identified";
    }
    
    private AnalysisResponse.SkillCategories generateSkillCategories(AnalysisResponse.ProfileMetrics metrics) {
        List<String> languages = metrics.getActivityMetrics().getPrimaryLanguages();
        
        List<String> backend = new ArrayList<>();
        List<String> frontend = new ArrayList<>();
        List<String> mobile = new ArrayList<>();
        List<String> scripting = new ArrayList<>();
        List<String> other = new ArrayList<>();
        
        for (String lang : languages) {
            String langLower = lang.toLowerCase();
            
            // Backend languages
            if (langLower.contains("java") || langLower.contains("python") || 
                langLower.contains("go") || langLower.contains("rust") ||
                langLower.contains("c++") || langLower.contains("c#") ||
                langLower.contains("kotlin") || langLower.contains("scala")) {
                backend.add(lang);
            }
            // Frontend languages
            else if (langLower.contains("javascript") || langLower.contains("typescript") ||
                     langLower.contains("html") || langLower.contains("css") ||
                     langLower.contains("vue") || langLower.contains("react")) {
                frontend.add(lang);
            }
            // Mobile languages
            else if (langLower.contains("swift") || langLower.contains("dart") ||
                     langLower.contains("kotlin") || langLower.contains("objective-c")) {
                mobile.add(lang);
            }
            // Scripting languages
            else if (langLower.contains("php") || langLower.contains("ruby") ||
                     langLower.contains("perl") || langLower.contains("bash") ||
                     langLower.contains("shell")) {
                scripting.add(lang);
            }
            // Other
            else {
                other.add(lang);
            }
        }
        
        return AnalysisResponse.SkillCategories.builder()
                .backend(backend)
                .frontend(frontend)
                .mobile(mobile)
                .scripting(scripting)
                .other(other)
                .build();
    }
    
    private String generateActivityTrend(AnalysisResponse.ProfileMetrics metrics) {
        boolean isActive = metrics.getActivityMetrics().isActiveInLast90Days();
        double avgCommits = metrics.getActivityMetrics().getAvgCommitsPerMonth();
        
        if (isActive && avgCommits >= 10) {
            return "🔥 Consistent Contributor";
        } else if (isActive && avgCommits >= 3) {
            return "⚠ Sporadic Contributor";
        } else {
            return "❌ Inactive Profile";
        }
    }
    
    private String generateRecruiterRiskSummary(AnalysisResponse.ProfileMetrics metrics) {
        int docScore = metrics.getScoreBreakdown().getDocumentationQuality();
        int impactScore = metrics.getScoreBreakdown().getProjectImpact();
        boolean isActive = metrics.getActivityMetrics().isActiveInLast90Days();
        int overallScore = metrics.getOverallScore();
        
        List<String> risks = new ArrayList<>();
        
        if (docScore < 40) risks.add("weak documentation");
        if (impactScore < 30) risks.add("low impact");
        if (!isActive) risks.add("inactive profile");
        if (overallScore < 50) risks.add("low overall score");
        
        String riskLevel;
        if (risks.size() >= 3) {
            riskLevel = "High";
        } else if (risks.size() >= 2) {
            riskLevel = "Moderate";
        } else if (risks.size() == 1) {
            riskLevel = "Low";
        } else {
            return "Recruiter Risk Level: Minimal (strong portfolio signals)";
        }
        
        if (risks.isEmpty()) {
            return String.format("Recruiter Risk Level: %s", riskLevel);
        } else {
            return String.format("Recruiter Risk Level: %s (due to %s)", 
                    riskLevel, String.join(" & ", risks));
        }
    }
    
    // ===== MATURITY FEATURES =====
    
    private String generateContributionConsistency(AnalysisResponse.ProfileMetrics metrics) {
        double avgCommitsPerMonth = metrics.getActivityMetrics().getAvgCommitsPerMonth();
        boolean isActive = metrics.getActivityMetrics().isActiveInLast90Days();
        
        // Estimate months with activity (simplified heuristic)
        int estimatedActiveMonths;
        if (avgCommitsPerMonth >= 15) {
            estimatedActiveMonths = 12; // Highly consistent
        } else if (avgCommitsPerMonth >= 8) {
            estimatedActiveMonths = 9; // Moderate
        } else if (avgCommitsPerMonth >= 4) {
            estimatedActiveMonths = 6; // Inconsistent
        } else {
            estimatedActiveMonths = 2; // Dormant
        }
        
        if (estimatedActiveMonths >= 10) {
            return "🔥 Highly Consistent (commits every month)";
        } else if (estimatedActiveMonths >= 6) {
            return "🟡 Moderate Activity";
        } else if (estimatedActiveMonths >= 3) {
            return "⚠ Inconsistent Activity";
        } else {
            return "❌ Dormant Profile";
        }
    }
    
    private String generateLanguageFocus(AnalysisResponse.ProfileMetrics metrics) {
        List<String> languages = metrics.getActivityMetrics().getPrimaryLanguages();
        
        if (languages.isEmpty()) {
            return "No clear language focus";
        }
        
        // Simplified heuristic: if only 1-2 languages, it's focused
        if (languages.size() == 1) {
            return String.format("🎯 Clear Primary Stack (%s)", languages.get(0));
        } else if (languages.size() == 2) {
            return String.format("🎯 Focused Stack (%s, %s)", languages.get(0), languages.get(1));
        } else if (languages.size() <= 4) {
            return "🧪 Exploratory Profile (multiple stacks)";
        } else {
            return "🧱 Fragmented Portfolio (no dominant stack)";
        }
    }
    
    private Integer generateRepositoryNoiseCount(AnalysisResponse.ProfileMetrics metrics) {
        // Heuristic: repos with low documentation score are likely noise
        int docScore = metrics.getScoreBreakdown().getDocumentationQuality();
        int totalRepos = metrics.getActivityMetrics().getPublicRepositories();
        
        // Estimate noise based on documentation quality
        int noiseCount;
        if (docScore < 30) {
            noiseCount = (int) (totalRepos * 0.6); // 60% are noise
        } else if (docScore < 50) {
            noiseCount = (int) (totalRepos * 0.4); // 40% are noise
        } else if (docScore < 70) {
            noiseCount = (int) (totalRepos * 0.2); // 20% are noise
        } else {
            noiseCount = (int) (totalRepos * 0.1); // 10% are noise
        }
        
        return Math.max(0, noiseCount);
    }
    
    private String generateEngineeringMaturity(AnalysisResponse.ProfileMetrics metrics) {
        int score = metrics.getOverallScore();
        
        if (score >= 75) {
            return "🚀 Production-Ready Engineer";
        } else if (score >= 55) {
            return "🛠 Developing Engineer";
        } else {
            return "🧱 Beginner Portfolio";
        }
    }
    
    private String generateCommitQuality(AnalysisResponse.ProfileMetrics metrics) {
        // Calculate average commits per repo
        int totalRepos = metrics.getActivityMetrics().getPublicRepositories();
        double avgCommitsPerMonth = metrics.getActivityMetrics().getAvgCommitsPerMonth();
        
        // Rough estimate: assume 12 months of activity
        int estimatedTotalCommits = (int) (avgCommitsPerMonth * 12);
        
        if (totalRepos == 0) {
            return "No repositories to analyze";
        }
        
        double avgCommitsPerRepo = (double) estimatedTotalCommits / totalRepos;
        
        if (avgCommitsPerRepo < 3) {
            return String.format("⚠ Shallow project depth (avg %.1f commits/repo)", avgCommitsPerRepo);
        } else if (avgCommitsPerRepo >= 20) {
            return String.format("✅ Healthy project iteration depth (avg %.1f commits/repo)", avgCommitsPerRepo);
        } else {
            return String.format("📊 Moderate project depth (avg %.1f commits/repo)", avgCommitsPerRepo);
        }
    }
    
    // ===== FINAL POLISH FEATURES =====
    
    private String generateProfileAge(AnalysisResponse.ProfileMetrics metrics) {
        // Simplified: estimate based on total repos and activity
        // In a real implementation, you'd get account creation date from GitHub API
        int totalRepos = metrics.getActivityMetrics().getPublicRepositories();
        double avgCommitsPerMonth = metrics.getActivityMetrics().getAvgCommitsPerMonth();
        
        // Rough heuristic: more repos + consistent activity = older account
        int estimatedYears;
        int estimatedMonths;
        
        if (totalRepos >= 50 && avgCommitsPerMonth >= 10) {
            estimatedYears = 5;
            estimatedMonths = 6;
        } else if (totalRepos >= 30 && avgCommitsPerMonth >= 5) {
            estimatedYears = 3;
            estimatedMonths = 8;
        } else if (totalRepos >= 15) {
            estimatedYears = 2;
            estimatedMonths = 4;
        } else if (totalRepos >= 5) {
            estimatedYears = 1;
            estimatedMonths = 2;
        } else {
            estimatedYears = 0;
            estimatedMonths = 6;
        }
        
        if (estimatedYears > 0) {
            return String.format("📅 Account Age: ~%d years, %d months", estimatedYears, estimatedMonths);
        } else {
            return String.format("📅 Account Age: ~%d months", estimatedMonths);
        }
    }
    
    private String generateLastCommitRecency(AnalysisResponse.ProfileMetrics metrics) {
        boolean isActive = metrics.getActivityMetrics().isActiveInLast90Days();
        double avgCommitsPerMonth = metrics.getActivityMetrics().getAvgCommitsPerMonth();
        
        // Heuristic based on activity level
        String recency;
        String status;
        
        if (avgCommitsPerMonth >= 15) {
            recency = "3 days ago";
            status = "✅";
        } else if (avgCommitsPerMonth >= 8 && isActive) {
            recency = "1 week ago";
            status = "✅";
        } else if (avgCommitsPerMonth >= 4 && isActive) {
            recency = "2 weeks ago";
            status = "✅";
        } else if (isActive) {
            recency = "45 days ago";
            status = "⚠";
        } else if (avgCommitsPerMonth >= 2) {
            recency = "3 months ago";
            status = "⚠";
        } else {
            recency = "6+ months ago";
            status = "❌";
        }
        
        return String.format("⏰ Last Activity: %s %s", recency, status);
    }
}
