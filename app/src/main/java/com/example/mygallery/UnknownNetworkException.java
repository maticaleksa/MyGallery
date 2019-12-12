package com.example.mygallery;

public class UnknownNetworkException extends Exception {

    public UnknownNetworkException(String msg) {
        super(msg);
    }

    public static UnknownNetworkException couldNotDownloadImage() {
        return new UnknownNetworkException("Something went wrong, couldn't download image");
    }

    public static UnknownNetworkException couldNotDownloadImagesMetaData() {
        return new UnknownNetworkException("Something went wrong, couldn't download images metadata");
    }
}
