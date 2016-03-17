package com.intratuin.testmarket;

public enum Message {
    ERROR_REGISTRATION("Sorry, this email address is already registered, choose another."),
    SUCCESS_REGISTRATION("Registration is successful"),
    ERROR_LOGIN("Sorry, your username and password are incorrect - please try again."),
    SUCCESS_LOGIN("Login is successful"),;
    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
