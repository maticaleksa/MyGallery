package com.example.mygallery.exceptions;

public class UnknownNetworkException extends Exception {

    public UnknownNetworkException(String msg) {
        super(msg);
    }

    public static UnknownNetworkException couldNotDownloadImage() {
        return new UnknownNetworkException("Something went wrong, couldn't download the file");
    }

    public static UnknownNetworkException couldNotDownloadImagesMetaData() {
        return new UnknownNetworkException("Something went wrong, couldn't download images metadata");
    }
}
