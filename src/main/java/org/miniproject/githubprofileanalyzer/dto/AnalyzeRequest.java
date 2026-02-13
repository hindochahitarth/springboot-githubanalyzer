package org.miniproject.githubprofileanalyzer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AnalyzeRequest {
    @NotBlank(message = "GitHub username or URL is required")
    private String username;
}
