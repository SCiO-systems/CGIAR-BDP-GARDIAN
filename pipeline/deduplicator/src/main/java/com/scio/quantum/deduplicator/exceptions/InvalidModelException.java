package com.scio.quantum.deduplicator.exceptions;

public class InvalidModelException extends Exception {

    public InvalidModelException(String msg) {
        super(msg);
        System.out.println(msg);
    }

}
