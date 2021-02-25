package org.example.sweater.exception;

import org.springframework.security.core.AuthenticationException;

/* Создали свой эксепшен для аутентификации в спринг секьюрити*/
public class UserNotFoundInDataBase extends AuthenticationException {
    public UserNotFoundInDataBase(String msg) {
        super(msg);
    }
}

