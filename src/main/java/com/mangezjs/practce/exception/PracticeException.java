package com.mangezjs.practce.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PracticeException extends RuntimeException{

    private Errorcode errorCode;
    private String message;

    public PracticeException(Errorcode errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        if(message == null){
            return errorCode.getMessage();
        }
        return String.format("%s, %s", errorCode.getMessage(), message);
    }
}
