package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class BranchResponseDto {

    @NotNull
    private String name;
    
    @NotNull
    private String lastCommitSha;

}
