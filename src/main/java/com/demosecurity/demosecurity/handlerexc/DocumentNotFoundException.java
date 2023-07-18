package com.demosecurity.demosecurity.handlerexc;

public class DocumentNotFoundException extends Exception {
    public DocumentNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
