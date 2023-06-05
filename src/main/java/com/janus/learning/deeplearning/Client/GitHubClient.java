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

    private List<RepositoryResponseDto> getRepoNames(String userName) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://api.github.com/users/" + userName + "/repos";
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String myJSONString = response.getBody();
        JsonArray jsonArray = new Gson().fromJson(myJSONString, JsonArray.class);
        List<RepositoryResponseDto> repositories = new ArrayList<>();
        jsonArray.forEach(repo -> {
            String repoName = repo.getAsJsonObject().get("name").getAsString();
            RepositoryResponseDto repository = RepositoryResponseDto.builder()
                    .name(repoName)
                    .build();
            repositories.add(repository);
        });
        return repositories;
    }

    private List<RepositoryResponseDto> getReposWithBranchesNamesAndSha(List<RepositoryResponseDto> repositories, String userName) {
        List<RepositoryResponseDto> repositoryResult = new ArrayList<>();
        repositories.forEach(repo -> {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api.github.com/repos/" + userName + "/" + repo.getName() + "/branches";
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
                repo.setBranches(branches);
            });
            repositoryResult.add(repo);
        });
        return repositoryResult;
    }

    public UserDataResponseDto getUserRepoData(String userName) {
        List<RepositoryResponseDto> repositoriesNames = getRepoNames(userName);
        List<RepositoryResponseDto> repositories = getReposWithBranchesNamesAndSha(repositoriesNames, userName);
        return UserDataResponseDto.builder()
                .userName(userName)
                .repositories(repositories)
                .build();
    }


}

