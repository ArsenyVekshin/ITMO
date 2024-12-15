package com.arsenyvekshin.lab1_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private int code;

    @JsonProperty("details")
    private String details;
}
