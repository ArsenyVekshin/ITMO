package com.arsenyvekshin.lab1_backend.exceptions;

import java.io.IOException;

public class InvalidFieldValueException extends IOException {
    public InvalidFieldValueException(String mes) { super(mes); }

}
