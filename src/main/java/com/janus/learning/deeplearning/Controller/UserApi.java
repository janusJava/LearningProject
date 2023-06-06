package com.janus.learning.deeplearning.Controller;


import com.janus.learning.deeplearning.Dto.UserDataResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public interface UserApi {

    @GetMapping(value = "/{username}/repositories")
    @ResponseStatus(HttpStatus.OK)
    UserDataResponseDto getUserRepoData(@PathVariable("username") String username, @RequestHeader(HttpHeaders.ACCEPT) String accept);
}

