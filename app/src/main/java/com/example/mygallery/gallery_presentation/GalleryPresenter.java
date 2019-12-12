package com.example.mygallery.gallery_presentation;

import com.example.mygallery.domain.Image;
import com.example.mygallery.ImagesRepository;
import com.example.mygallery.general.BasePresenter;
import com.example.picture_taking_module.TakePhoto;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class GalleryPresenter extends BasePresenter {

    private final ImagesRepository repository;
    private final TakePhoto takePhoto;
    private Disposable pictureTakeDisposable;

    @Inject
    public GalleryPresenter(ImagesRepository repository, TakePhoto takePhoto) {
        this.repository = repository;
        this.takePhoto = takePhoto;
    }

    public void onDisplay(GalleryScreen screen) {
        addDisposable(repository.getAll()
                .subscribe(imageResult -> {
                    if (imageResult.success()) {
                        screen.displayImageInGallery(imageResult.getData());
                    } else {
                        //display error
                    }
                }));
    }

    public void onTakePhotoPressed(GalleryScreen screen) {
        if (pictureTakeDisposable != null && !pictureTakeDisposable.isDisposed()) {
            pictureTakeDisposable.dispose();
        }
        pictureTakeDisposable = takePhoto.takePictureSingle()
                .subscribe(file ->
                        screen.displayImageInGallery(new Image(file, "takenPhoto", Calendar.getInstance().getTime().toString())));
    }


}
