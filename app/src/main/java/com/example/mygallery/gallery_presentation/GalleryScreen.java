package com.example.mygallery.gallery_presentation;

import com.example.mygallery.domain.Image;

public interface GalleryScreen {

    void displayImageInGallery(Image image);
    void openImage(Image image);
    void displayError(ErrorType errorType);
}
