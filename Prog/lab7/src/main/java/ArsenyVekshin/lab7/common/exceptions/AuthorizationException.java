package ArsenyVekshin.lab7.common.exceptions;

import java.io.IOException;

public class AuthorizationException extends IOException {
    public AuthorizationException(String mes) { super("Ошибка авторизации: " + mes); }
}
