package com.scio.quantum.deduplicator.exceptions;

public class InvalidYearFormatException extends Exception {

    public InvalidYearFormatException(String msg) {
        super(msg);
        System.out.println(msg);
    }

}
