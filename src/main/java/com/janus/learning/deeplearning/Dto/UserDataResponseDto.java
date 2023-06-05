package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDataResponseDto {

    private String userName;
    private List<RepositoryResponseDto> repositories;
}
