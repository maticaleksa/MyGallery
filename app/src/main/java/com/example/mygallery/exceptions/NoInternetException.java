package com.example.mygallery.exceptions;

/**
 * Exception thrown when action that requires internet connection is started.
 */
public class NoInternetException extends Exception {
    public NoInternetException() {
        super("Device is not connected to internet");
    }
}
