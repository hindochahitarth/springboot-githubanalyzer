package org.miniproject.githubprofileanalyzer.util;

import lombok.extern.slf4j.Slf4j;
import org.miniproject.githubprofileanalyzer.exception.GitHubApiException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class GitHubUrlValidator {
    
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]([a-zA-Z0-9-]{0,38}[a-zA-Z0-9])?$");
    private static final Pattern GITHUB_URL_PATTERN = Pattern.compile(
        "^https?://(?:www\\.)?github\\.com/([a-zA-Z0-9]([a-zA-Z0-9-]{0,38}[a-zA-Z0-9])?)/?.*$"
    );
    
    /**
     * Validates and extracts GitHub username from either a username or GitHub URL
     * @param input GitHub username or profile URL
     * @return Validated GitHub username
     * @throws GitHubApiException if input is invalid
     */
    public static String extractUsername(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new GitHubApiException("GitHub username or URL cannot be empty");
        }
        
        String trimmedInput = input.trim();
        
        // Check for common typos
        if (trimmedInput.toLowerCase().contains("gihub.com")) {
            throw new GitHubApiException("Invalid URL: Did you mean 'github.com'? (You typed 'gihub.com')");
        }
        
        // Check if it's a URL
        Matcher urlMatcher = GITHUB_URL_PATTERN.matcher(trimmedInput);
        if (urlMatcher.matches()) {
            String username = urlMatcher.group(1);
            log.info("Extracted username '{}' from URL: {}", username, trimmedInput);
            return username;
        }
        
        // Check if it's a valid username
        if (USERNAME_PATTERN.matcher(trimmedInput).matches()) {
            log.info("Valid username provided: {}", trimmedInput);
            return trimmedInput;
        }
        
        // Neither valid URL nor username
        throw new GitHubApiException(
            "Invalid GitHub username or URL. Please provide either:\n" +
            "- A valid GitHub username (e.g., 'torvalds')\n" +
            "- A GitHub profile URL (e.g., 'https://github.com/torvalds')"
        );
    }
    
    /**
     * Checks if the input is a valid GitHub username
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return USERNAME_PATTERN.matcher(username.trim()).matches();
    }
    
    /**
     * Checks if the input is a valid GitHub profile URL
     */
    public static boolean isValidGitHubUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }
        return GITHUB_URL_PATTERN.matcher(url.trim()).matches();
    }
}
