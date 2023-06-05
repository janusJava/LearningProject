package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RepositoryResponseDto {
    private String name;
    private List<BranchResponseDto> branches;

}
