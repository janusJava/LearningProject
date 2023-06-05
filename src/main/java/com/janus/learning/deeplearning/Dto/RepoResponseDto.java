package com.janus.learning.deeplearning.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class RepoResponseDto {

    private String repositoryName;
    private String ownerLogin;
    private String branchName;
    private String lastCommit;


}
