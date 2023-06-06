package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@Builder
public class UserDataResponseDto {

    @NotNull
    private String userName;

    @NotNull
    private List<RepositoryResponseDto> repositories;

}
