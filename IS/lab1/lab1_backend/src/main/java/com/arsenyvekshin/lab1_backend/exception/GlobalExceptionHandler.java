package com.arsenyvekshin.lab1_backend.exception;

import com.arsenyvekshin.lab1_backend.dto.MessageInfoDto;
import io.minio.errors.MinioException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MinioException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public MessageInfoDto handleSDKException(Exception ex) {
        return new MessageInfoDto(ex.getMessage());
    }
/*
    @ExceptionHandler(AwsServiceException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public MessageInfoDto handleAwsServiceException(Exception ex) {
        return new MessageInfoDto(ex.getMessage());
    }
*/

    @ExceptionHandler({IllegalArgumentException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageInfoDto handleIllegalArgumentException(Exception ex) {
        return new MessageInfoDto(ex.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageInfoDto handleIOException(Exception ex) {
        return new MessageInfoDto(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageInfoDto handleRuntimeException(Exception ex) {
        return new MessageInfoDto(ex.getMessage());
    }

}
