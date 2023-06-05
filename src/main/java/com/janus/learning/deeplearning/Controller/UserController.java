package com.janus.learning.deeplearning.Controller;


import com.janus.learning.deeplearning.Client.GitHubClient;
import com.janus.learning.deeplearning.Dto.UserDataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final GitHubClient gitHubClient;

    @Override
    public UserDataResponseDto getUserRepoData(@PathVariable String username) {
        log.info("UserController: Getting user repositories {user:{}}", username);
        UserDataResponseDto result = gitHubClient.getUserRepoData(username);
        log.debug("UserController: Got user repositories {result: {}}", result);
        return result;
    }
}
