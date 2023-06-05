package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BranchResponseDto {
    private String name;
    private String lastCommitSha;
}
