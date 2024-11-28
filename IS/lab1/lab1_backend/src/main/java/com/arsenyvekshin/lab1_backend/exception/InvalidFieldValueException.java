package com.arsenyvekshin.lab1_backend.exception;

import java.io.IOException;

public class InvalidFieldValueException extends IOException {
    public InvalidFieldValueException(String mes) {
        super(mes);
    }

}
