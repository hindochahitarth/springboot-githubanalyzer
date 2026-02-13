package org.miniproject.githubprofileanalyzer.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.miniproject.githubprofileanalyzer.exception.GitHubApiException;
import org.miniproject.githubprofileanalyzer.model.GitHubUser;
import org.miniproject.githubprofileanalyzer.model.Repository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class GitHubApiClient {
    
    private final WebClient gitHubWebClient;
    
    @SuppressWarnings("unchecked")
    public GitHubUser getUser(String username) {
        try {
            Map<String, Object> response = (Map<String, Object>) gitHubWebClient.get()
                    .uri("/users/{username}", username)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .onErrorResume(e -> {
                        log.error("Error fetching user: {}", username, e);
                        return Mono.error(new GitHubApiException("User not found: " + username));
                    })
                    .block();
            
            return mapToGitHubUser(response);
        } catch (Exception e) {
            throw new GitHubApiException("Failed to fetch user: " + username, e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Repository> getUserRepositories(String username) {
        try {
            List<Map<String, Object>> response = (List<Map<String, Object>>) (List<?>) gitHubWebClient.get()
                    .uri("/users/{username}/repos?per_page=100&sort=updated", username)
                    .retrieve()
                    .bodyToFlux(Map.class)
                    .collectList()
                    .onErrorResume(e -> {
                        log.error("Error fetching repositories for user: {}", username, e);
                        return Mono.error(new GitHubApiException("Failed to fetch repositories"));
                    })
                    .block();
            
            return response.stream()
                    .map(this::mapToRepository)
                    .toList();
        } catch (Exception e) {
            throw new GitHubApiException("Failed to fetch repositories for user: " + username, e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Integer> getLanguageStats(String owner, String repo) {
        try {
            return (Map<String, Integer>) (Map<?, ?>) gitHubWebClient.get()
                    .uri("/repos/{owner}/{repo}/languages", owner, repo)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .onErrorResume(e -> Mono.just(Map.of()))
                    .block();
        } catch (Exception e) {
            log.warn("Failed to fetch language stats for {}/{}", owner, repo);
            return Map.of();
        }
    }
    
    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserProfile(String username) {
        try {
            Map<String, Object> userData = (Map<String, Object>) gitHubWebClient.get()
                    .uri("/users/{username}", username)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .onErrorResume(e -> {
                        log.error("Error fetching user profile: {}", username, e);
                        return Mono.error(new GitHubApiException("User profile not found: " + username));
                    })
                    .block();
            
            if (userData != null) {
                // Extract relevant fields including updated_at for last activity
                Map<String, Object> profile = new HashMap<>();
                profile.put("login", userData.get("login"));
                profile.put("name", userData.get("name"));
                profile.put("bio", userData.get("bio"));
                profile.put("public_repos", userData.get("public_repos"));
                profile.put("followers", userData.get("followers"));
                profile.put("following", userData.get("following"));
                profile.put("created_at", userData.get("created_at"));
                profile.put("updated_at", userData.get("updated_at")); // Last activity timestamp
                return profile;
            }
        } catch (Exception e) {
            throw new GitHubApiException("Failed to fetch user profile: " + username, e);
        }
        
        return new HashMap<>();
    }
    
    public boolean hasReadme(String owner, String repo) {
        try {
            gitHubWebClient.get()
                    .uri("/repos/{owner}/{repo}/readme", owner, repo)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    private GitHubUser mapToGitHubUser(Map<String, Object> data) {
        return GitHubUser.builder()
                .login((String) data.get("login"))
                .name((String) data.get("name"))
                .bio((String) data.get("bio"))
                .avatarUrl((String) data.get("avatar_url"))
                .htmlUrl((String) data.get("html_url"))
                .publicRepos((Integer) data.getOrDefault("public_repos", 0))
                .followers((Integer) data.getOrDefault("followers", 0))
                .following((Integer) data.getOrDefault("following", 0))
                .createdAt(parseDateTime((String) data.get("created_at")))
                .updatedAt(parseDateTime((String) data.get("updated_at")))
                .build();
    }
    
    private Repository mapToRepository(Map<String, Object> data) {
        return Repository.builder()
                .name((String) data.get("name"))
                .fullName((String) data.get("full_name"))
                .description((String) data.get("description"))
                .htmlUrl((String) data.get("html_url"))
                .fork((Boolean) data.getOrDefault("fork", false))
                .stargazersCount((Integer) data.getOrDefault("stargazers_count", 0))
                .forksCount((Integer) data.getOrDefault("forks_count", 0))
                .openIssuesCount((Integer) data.getOrDefault("open_issues_count", 0))
                .language((String) data.get("language"))
                .topics((List<String>) data.getOrDefault("topics", List.of()))
                .createdAt(parseDateTime((String) data.get("created_at")))
                .updatedAt(parseDateTime((String) data.get("updated_at")))
                .pushedAt(parseDateTime((String) data.get("pushed_at")))
                .size((Integer) data.getOrDefault("size", 0))
                .defaultBranch((String) data.get("default_branch"))
                .hasReadme(false) // Will be checked separately if needed
                .hasTests(false)  // Will be checked separately if needed
                .hasDeployment(false) // Will be checked separately if needed
                .build();
    }
    
    private LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null) return null;
        return LocalDateTime.parse(dateTime.substring(0, 19));
    }
}
