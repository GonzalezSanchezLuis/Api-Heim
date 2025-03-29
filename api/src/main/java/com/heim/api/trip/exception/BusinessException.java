package com.heim.api.trip.exception;

public class BusinessException  extends RuntimeException{
    private  String errorCode;

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
