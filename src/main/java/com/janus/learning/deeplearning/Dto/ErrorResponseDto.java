package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
@Builder
public class ErrorResponseDto {

    @NotNull
    private int statusCode;

    @NotNull
    private String message;

}
