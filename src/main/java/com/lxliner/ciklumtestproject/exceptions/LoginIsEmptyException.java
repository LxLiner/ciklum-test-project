package com.lxliner.ciklumtestproject.exceptions;

public class LoginIsEmptyException extends Exception {

    public LoginIsEmptyException() {
        super();
    }

    public LoginIsEmptyException(String message) {
        super(message);
    }
}
