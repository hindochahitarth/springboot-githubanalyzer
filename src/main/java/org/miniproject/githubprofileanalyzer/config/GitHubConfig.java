package org.miniproject.githubprofileanalyzer.config;

import io.netty.channel.ChannelOption;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.net.InetSocketAddress;
import java.time.Duration;

@Configuration
public class GitHubConfig {
    
    @Value("${github.api.base-url}")
    private String baseUrl;
    
    @Value("${github.api.token}")
    private String token;
    
    @Bean
    public WebClient gitHubWebClient() {
        // Configure HttpClient to use IPv4 and avoid IPv6 DNS issues
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .responseTimeout(Duration.ofSeconds(10))
                .resolver(DefaultAddressResolverGroup.INSTANCE);
        
        WebClient.Builder builder = WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Accept", "application/vnd.github.v3+json");
        
        if (token != null && !token.isEmpty()) {
            builder.defaultHeader("Authorization", "Bearer " + token);
        }
        
        return builder.build();
    }
}
