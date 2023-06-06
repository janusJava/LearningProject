package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@Builder
public class RepositoryResponseDto {

    @NotNull
    private String name;

    @NotNull
    private List<BranchResponseDto> branches;

}
