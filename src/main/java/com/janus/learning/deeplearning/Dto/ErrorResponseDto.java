package com.janus.learning.deeplearning.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private int statusCode;

    private String message;
    
}
