package com.arsenyvekshin.lab1_backend.controller;


import com.arsenyvekshin.lab1_backend.dto.MessageInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "test")
@RestController
public class HelloWorldController {

    @Operation(summary = "kurwa")
    @GetMapping(value = "/hello", produces = "application/json")
    public MessageInfoDto hello(){
        return new MessageInfoDto("hello world");
    }



}
