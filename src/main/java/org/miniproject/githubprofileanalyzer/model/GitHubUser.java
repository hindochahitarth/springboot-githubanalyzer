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
    private LocalDateTime updatedAt;
}
