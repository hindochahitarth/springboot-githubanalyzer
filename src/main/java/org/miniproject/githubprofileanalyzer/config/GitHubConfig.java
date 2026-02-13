package org.miniproject.githubprofileanalyzer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GitHubConfig {
    
    @Value("${github.api.base-url}")
    private String baseUrl;
    
    @Value("${github.api.token}")
    private String token;
    
    @Bean
    public WebClient gitHubWebClient() {
        WebClient.Builder builder = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Accept", "application/vnd.github.v3+json");
        
        if (token != null && !token.isEmpty()) {
            builder.defaultHeader("Authorization", "Bearer " + token);
        }
        
        return builder.build();
    }
}
