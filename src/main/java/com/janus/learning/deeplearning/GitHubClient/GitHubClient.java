package com.janus.learning.deeplearning.GitHubClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.janus.learning.deeplearning.Dto.RepoResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitHubClient {

    private static final String GITHUB_API_URL = "https://api.github.com";
    private final WebClient webClient = WebClient.create(GITHUB_API_URL);

    public List<RepoResponseDto> getUserRepositories(String username) {
        return webClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .blockOptional()
                .map(jsonNode -> {
                    List<RepoResponseDto> repositories = new ArrayList<>();
                    jsonNode.forEach(repo -> {
                        String repoName = repo.get("name").asText();
                        String lastCommitSha = getLastCommitSha(repo.get("commits_url").asText());
                        RepoResponseDto repositoryInfo = RepoResponseDto.builder()
                                .ownerLogin(username)
                                .repositoryName(repoName)
                                .lastCommit(lastCommitSha)
                                .build();
                        repositories.add(repositoryInfo);
                    });
                    return repositories;
                })
                .orElse(null);
    }

    private String getLastCommitSha(String commitsUrl) {
        String url = commitsUrl.replace("{/sha}", "");
        JsonNode commitsNode = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .blockOptional()
                .orElse(null);
        if (commitsNode != null) {
            return commitsNode.get(0).get("sha").asText();
        }
        return null;
    }
}
