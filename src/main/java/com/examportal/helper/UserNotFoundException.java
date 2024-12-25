package com.examportal.helper;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("!!! User with this username not found !!!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
