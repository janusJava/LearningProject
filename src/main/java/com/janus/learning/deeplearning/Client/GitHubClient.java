package com.janus.learning.deeplearning.Client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.janus.learning.deeplearning.Dto.BranchResponseDto;
import com.janus.learning.deeplearning.Dto.RepositoryResponseDto;
import com.janus.learning.deeplearning.Dto.UserDataResponseDto;
import com.janus.learning.deeplearning.Exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class GitHubClient {

    private static final String GITHUB_API_URL = "https://api.github.com";

    private List<String> getRepoNamesAndValidateStatusCode(String userName) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = GITHUB_API_URL + "/users/" + userName + "/repos";
        List<String> responseList = new ArrayList<>();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String myJSONString = response.getBody();
                JsonArray jsonArray = new Gson().fromJson(myJSONString, JsonArray.class);
                jsonArray.forEach(repo -> {
                    if (repo.getAsJsonObject().get("fork").getAsString().equals("false")) {
                        String repoName = repo.getAsJsonObject().get("name").getAsString();
                        responseList.add(repoName);
                    }
                });
            }
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException("Nie znaleziono zasobów.");
            } else {
                ex.printStackTrace();
            }
        }
        return responseList;
    }

    private List<RepositoryResponseDto> getReposWithBranchesNamesAndSha(List<String> repositories, String userName) {
        List<RepositoryResponseDto> repositoryResult = new ArrayList<>();
        repositories.forEach(repo -> {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = GITHUB_API_URL + "/repos/" + userName + "/" + repo + "/branches";
            try {
                ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
                if (response.getStatusCode() == HttpStatus.OK) {
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
                    });
                    RepositoryResponseDto repositoryResponseDto = RepositoryResponseDto.builder()
                            .name(repo)
                            .branches(branches)
                            .build();
                    repositoryResult.add(repositoryResponseDto);
                }
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new NotFoundException("Nie znaleziono zasobów.");
                } else {
                    ex.printStackTrace();
                }
            }
        });
        return repositoryResult;
    }

    public UserDataResponseDto getUserRepoData(String userName) {
        List<String> responseList = getRepoNamesAndValidateStatusCode(userName);
        List<RepositoryResponseDto> repositories = getReposWithBranchesNamesAndSha(responseList, userName);
        return UserDataResponseDto.builder()
                .userName(userName)
                .repositories(repositories)
                .build();
    }

}

