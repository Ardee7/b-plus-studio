package com.api.b_plus_studio.Exceptions;

public class UserException extends BaseServiceException {
    public UserException(String message) {
        super("UE001", message);
    }
}
