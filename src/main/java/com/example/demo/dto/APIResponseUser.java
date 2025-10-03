package com.example.demo.dto;

public class APIResponseUser {
    private boolean success;
    private String message;
    public APIResponseUser(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
