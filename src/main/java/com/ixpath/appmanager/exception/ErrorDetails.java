package com.ixpath.appmanager.exception;


public class ErrorDetails {
    private String message;
    private String code;

    public ErrorDetails(String message, String codeError) {
        this.message = message;
        this.code = codeError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodeError() {
        return code;
    }

    public void setCodeError(String codeError) {
        this.code = codeError;
    }
}
