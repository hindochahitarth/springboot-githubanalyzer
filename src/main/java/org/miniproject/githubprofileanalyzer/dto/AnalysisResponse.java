package org.miniproject.githubprofileanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResponse {
    
    // Executive Summary
    private String executiveSummary;
    
    // Overall Assessment
    private OverallAssessment overallAssessment;
    private List<String> whatRecruitersNoticeFirst;
    
    // Signals and Flags
    private List<Signal> strongSignals;
    private List<RedFlag> redFlags;
    
    // Repository Strategy
    private RepositoryStrategy repositoryStrategy;
    
    // Impact & Discoverability
    private ImpactAndDiscoverability impactAndDiscoverability;
    
    // Recruiter Verdict
    private RecruiterVerdict recruiterVerdict;
    
    // Polish Features
    private String flagshipProject;
    private String confidenceLevel; // "High", "Moderate", "Low"
    private List<String> fixPriorities;
    private CompletenessStats completenessStats;
    private ScoreSimulation scoreSimulation;
    
    // Intelligence Features
    private List<String> top3StrongestRepos;
    private String weakestDimension;
    private SkillCategories skillCategories;
    private String activityTrend;
    private String recruiterRiskSummary;
    
    // Maturity Features
    private String contributionConsistency;
    private String languageFocus;
    private Integer repositoryNoiseCount;
    private String engineeringMaturity;
    private String commitQuality;
    
    // Final Polish Features
    private String profileAge;
    private String lastCommitRecency;
    
    // Tech Stack Evaluation
    private TechStackEvaluation techStackEvaluation;
    
    // UI/UX Evaluation
    private UiUxEvaluation uiUxEvaluation;
    
    // Recommendations
    private List<String> recommendedPages;
    private List<String> recommendedAppFlow;
    private Map<String, List<String>> recommendedFolderStructure;
    
    // Action Plan
    private ThirtyDayActionPlan thirtyDayActionPlan;
    private List<String> threeImmediateHighImpactFixes;
    
    // Resume Summary
    private String resumeReadyProfileSummary;
    
    // Raw Metrics (for dashboard display)
    private ProfileMetrics profileMetrics;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OverallAssessment {
        private String portfolioStrength;
        private String engineeringLevel;
        private String hireReadiness;
        private String confidenceReasoning;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Signal {
        private String signal;
        private String whyItMatters;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedFlag {
        private String issue;
        private String impact;
        private String severity;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RepositoryStrategy {
        private List<String> highlightThese;
        private List<String> improveThese;
        private List<String> considerArchiving;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TechStackEvaluation {
        private String backendArchitectureQuality;
        private String frontendArchitectureQuality;
        private List<String> missingBestPractices;
        private List<String> backendImprovementSuggestions;
        private List<String> frontendImprovementSuggestions;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UiUxEvaluation {
        private String uiThemeAssessment;
        private String layoutClarity;
        private String designConsistency;
        private List<String> improvementSuggestions;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThirtyDayActionPlan {
        private String week1;
        private String week2;
        private String week3;
        private String week4;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileMetrics {
        private String username;
        private int overallScore;
        private String grade;
        private ScoreBreakdown scoreBreakdown;
        private ActivityMetrics activityMetrics;
        private List<String> pinnedRepoSummary;
        private List<String> topRepoSummary;
        private String lastActivityDate; // ISO 8601 timestamp from GitHub API
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScoreBreakdown {
        private int documentationQuality;
        private int codeStructure;
        private int activityConsistency;
        private int repositoryOrganization;
        private int projectImpact;
        private int technicalDepth;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActivityMetrics {
        private int publicRepositories;
        private boolean activeInLast90Days;
        private double avgCommitsPerMonth;
        private int totalStars;
        private int totalForks;
        private List<String> primaryLanguages;
        private boolean testsPresent;
        private boolean deploymentLinksPresent;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImpactAndDiscoverability {
        private String businessRelevance;
        private String communityValidation;
        private String productionReadiness;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecruiterVerdict {
        private String decision; // "Strong Hire", "Hire", "Maybe", "Not Ready"
        private String shortReason;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompletenessStats {
        private int professionalRepos;
        private int totalRepos;
        private String message;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScoreSimulation {
        private String improvementArea;
        private int currentScore;
        private int projectedScore;
        private String projectedGrade;
        private String message;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillCategories {
        private List<String> backend;
        private List<String> frontend;
        private List<String> mobile;
        private List<String> scripting;
        private List<String> other;
    }
}
