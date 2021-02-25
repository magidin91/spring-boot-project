package org.example.sweater.entity.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true) //игнорируем неизвестные поля
public class CaptchaResponseDto {
    private boolean success;

    /* @JsonAlias("error-codes") определяет альтернативное имя поля, т.к. java не поддерживает имя переменной с дефисом */
    @JsonAlias("error-codes")
    private Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}