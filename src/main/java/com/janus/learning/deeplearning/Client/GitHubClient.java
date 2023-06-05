package com.janus.learning.deeplearning.Client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.janus.learning.deeplearning.Dto.BranchResponseDto;
import com.janus.learning.deeplearning.Dto.RepositoryResponseDto;
import com.janus.learning.deeplearning.Dto.UserDataResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitHubClient {

    private static final String GITHUB_API_URL = "https://api.github.com";

    private List<String> getRepoNames(String userName) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = GITHUB_API_URL + "/users/" + userName + "/repos";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String myJSONString = response.getBody();
        JsonArray jsonArray = new Gson().fromJson(myJSONString, JsonArray.class);
        List<String> repositories = new ArrayList<>();
        jsonArray.forEach(repo -> {
            if (repo.getAsJsonObject().get("fork").getAsString().equals("false")) {
                String repoName = repo.getAsJsonObject().get("name").getAsString();
                repositories.add(repoName);
            }
        });
        return repositories;
    }

    private List<RepositoryResponseDto> getReposWithBranchesNamesAndSha(List<String> repositories, String userName) {
        List<RepositoryResponseDto> repositoryResult = new ArrayList<>();
        repositories.forEach(repo -> {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = GITHUB_API_URL + "/repos/" + userName + "/" + repo + "/branches";
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            String myJSONString = response.getBody();
            JsonArray jsonArray = new Gson().fromJson(myJSONString, JsonArray.class);
            List<BranchResponseDto> branches = new ArrayList<>();
            jsonArray.forEach(branch -> {
                String branchName = branch.getAsJsonObject().get("name").getAsString();
                String commitSha = branch.getAsJsonObject().get("commit").getAsJsonObject().get("sha").getAsString();
                BranchResponseDto branchObj = BranchResponseDto.builder()
                        .name(branchName)
                        .lastCommitSha(commitSha)
                        .build();
                branches.add(branchObj);
                RepositoryResponseDto repositoryResponseDto = RepositoryResponseDto.builder()
                        .name(repo)
                        .branches(branches)
                        .build();
                repositoryResult.add(repositoryResponseDto);
            });
        });
        return repositoryResult;
    }

    public UserDataResponseDto getUserRepoData(String userName) {
        List<String> repositoriesNames = getRepoNames(userName);
        List<RepositoryResponseDto> repositories = getReposWithBranchesNamesAndSha(repositoriesNames, userName);
        return UserDataResponseDto.builder()
                .userName(userName)
                .repositories(repositories)
                .build();
    }
    
}

