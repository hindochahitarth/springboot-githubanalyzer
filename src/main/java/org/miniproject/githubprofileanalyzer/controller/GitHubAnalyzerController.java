package org.miniproject.githubprofileanalyzer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miniproject.githubprofileanalyzer.dto.AnalyzeRequest;
import org.miniproject.githubprofileanalyzer.dto.AnalysisResponse;
import org.miniproject.githubprofileanalyzer.service.AIInsightService;
import org.miniproject.githubprofileanalyzer.service.ScoringService;
import org.miniproject.githubprofileanalyzer.util.GitHubUrlValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GitHubAnalyzerController {
    
    private final ScoringService scoringService;
    private final AIInsightService aiInsightService;
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "GitHub Portfolio Analyzer",
                "version", "1.0.0"
        ));
    }
    
    @PostMapping("/analyze")
    public ResponseEntity<AnalysisResponse> analyzeProfile(@Valid @RequestBody AnalyzeRequest request) {
        log.info("Analyzing GitHub profile: {}", request.getUsername());
        
        // Validate and extract username from URL if provided
        String username = GitHubUrlValidator.extractUsername(request.getUsername());
        
        // Calculate metrics using scoring service
        AnalysisResponse.ProfileMetrics metrics = scoringService.calculateMetrics(username);
        
        // Generate AI insights
        AnalysisResponse response = aiInsightService.generateInsights(metrics);
        
        log.info("Analysis complete for user: {} with score: {}", username, metrics.getOverallScore());
        
        return ResponseEntity.ok(response);
    }
}
