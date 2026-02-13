package org.miniproject.githubprofileanalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Repository {
    private String name;
    private String fullName;
    private String description;
    private String htmlUrl;
    private boolean fork;
    private int stargazersCount;
    private int forksCount;
    private int openIssuesCount;
    private String language;
    private List<String> topics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime pushedAt;
    private int size;
    private String defaultBranch;
    private boolean hasReadme;
    private boolean hasTests;
    private boolean hasDeployment;
}
