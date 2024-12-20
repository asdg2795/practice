package com.mangezjs.practce.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum Errorcode {
    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Email is duplicated"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "No corresponding user was found for this email."),
    ALL_INPUT_NAME_AND_EMAIL(HttpStatus.NO_CONTENT, "You must enter both your name and email.")

;
    private HttpStatus status;
    private String message;
}
