package com.janus.learning.deeplearning.Exception.Handler;

import com.janus.learning.deeplearning.Dto.ErrorResponseDto;
import com.janus.learning.deeplearning.Exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(NotAcceptableException.class)
//    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
//    public ResponseEntity<ErrorResponseDto> handleNotAcceptableException(NotAcceptableException ex) {
//        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
//                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
//                .message(ex.getMessage())
//                .build();
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
//    }


}