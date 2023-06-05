package com.janus.learning.deeplearning.Controller;


import com.janus.learning.deeplearning.Dto.RepoResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public interface UserApi {


    @GetMapping("/{username}/repositories")
    @ResponseStatus(HttpStatus.OK)
    List<RepoResponseDto> getUserRepositories(@PathVariable("username") String username);
}
