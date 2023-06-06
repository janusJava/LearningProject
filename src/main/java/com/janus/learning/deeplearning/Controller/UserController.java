package com.janus.learning.deeplearning.Controller;


import com.janus.learning.deeplearning.Client.GitHubClient;
import com.janus.learning.deeplearning.Dto.UserDataResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController implements UserApi {

    private final GitHubClient gitHubClient;

    @Override
    public UserDataResponseDto getUserRepoData(@PathVariable String username, @RequestHeader(HttpHeaders.ACCEPT) String accept) {
        log.info("UserController: Getting user repositories {user:{}}", username);
        if (accept == null || !accept.equals("application/json")) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Nieobsługiwalny format danych.");
        }
        UserDataResponseDto result = gitHubClient.getUserRepoData(username);
        log.debug("UserController: Got user repositories {result: {}}", result);
        return result;
    }
}
