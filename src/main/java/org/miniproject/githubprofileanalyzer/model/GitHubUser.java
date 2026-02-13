package org.miniproject.githubprofileanalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GitHubUser {
    private String login;
    private String name;
    private String bio;
    private String avatarUrl;
    private String htmlUrl;
    private int publicRepos;
    private int followers;
    private int following;
    private LocalDateTime createdAt;
    private String updatedAt; // ISO 8601 string from GitHub API (e.g., "2026-02-13T14:17:51Z")
}
