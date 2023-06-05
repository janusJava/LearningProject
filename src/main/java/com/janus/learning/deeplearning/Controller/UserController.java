package com.janus.learning.deeplearning.Controller;


import com.janus.learning.deeplearning.Dto.RepoResponseDto;
import com.janus.learning.deeplearning.GitHubClient.GitHubClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final GitHubClient gitHubClient;

    @Override
    public List<RepoResponseDto> getUserRepositories(@PathVariable String username) {
        log.info("UserController: Getting user repositories {user:{}}", username);
        List<RepoResponseDto> result = gitHubClient.getUserRepositories(username);
        log.debug("Got repositories: Got user repositories {result: {}}", result);
        return result;
    }
}
