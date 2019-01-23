package com.lxliner.ciklumtestproject.exceptions;

public class WeakPasswordException extends Exception{

    public WeakPasswordException() {
        super();
    }

    public WeakPasswordException(String message) {
        super(message);
    }
}
