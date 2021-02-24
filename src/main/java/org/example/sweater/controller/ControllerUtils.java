package org.example.sweater.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Получает ошибки валидации
 */
public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error", // имя поля модели
                FieldError::getDefaultMessage // сообщение, указанное в валидации
        );

        return bindingResult.getFieldErrors().stream().collect(collector);
    }
}
