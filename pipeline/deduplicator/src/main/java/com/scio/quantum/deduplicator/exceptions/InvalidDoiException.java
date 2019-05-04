package com.scio.quantum.deduplicator.exceptions;

public class InvalidDoiException extends Exception {

    public InvalidDoiException(String msg) {
        super(msg);
        System.out.println(msg);
    }

}
