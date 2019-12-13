package com.example.mygallery.gallery_presentation;

import com.example.mygallery.NetworkConnectivityNotifier;
import com.example.mygallery.exceptions.NoInternetException;
import com.example.mygallery.domain.Image;
import com.example.mygallery.ImagesRepository;
import com.example.mygallery.general.BasePresenter;
import com.example.picture_taking_module.TakePhoto;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

public class GalleryPresenter extends BasePresenter {

    private final NetworkConnectivityNotifier networkConnectivityNotifier;
    private final ImagesRepository repository;
    private final TakePhoto takePhoto;
    private Disposable pictureTakeDisposable;
    private boolean hasPendingAction;

    @Inject
    public GalleryPresenter(NetworkConnectivityNotifier networkConnectivityNotifier,
                            ImagesRepository repository,
                            TakePhoto takePhoto) {
        this.networkConnectivityNotifier = networkConnectivityNotifier;
        this.repository = repository;
        this.takePhoto = takePhoto;
    }

    public void onDisplay(GalleryScreen screen) {
        addDisposable(networkConnectivityNotifier.getNotifier()
                .subscribe(hasInternet -> {
                    if (hasInternet && hasPendingAction) {
                        hasPendingAction = false;
                        addDisposable(repository.getFromNetwork()
                        .subscribe(imageResult -> {
                            if (imageResult.success()) {
                                screen.displayImageInGallery(imageResult.getData());
                            }
                        }, throwable -> screen.displayError(ErrorType.UNKNOWN)));
                    }
                }));
        addDisposable(repository.getAll()
                .subscribe(imageResult -> {
                    if (imageResult.success()) {
                        screen.displayImageInGallery(imageResult.getData());
                    } else {
                        if (imageResult.getError() instanceof NoInternetException) {
                            hasPendingAction = true;
                        }
                        screen.displayError(ErrorType.NO_INTERNET);
                    }
                }, throwable -> screen.displayError(ErrorType.UNKNOWN)));
    }

    public void onTakePhotoPressed(GalleryScreen screen) {
        if (pictureTakeDisposable != null && !pictureTakeDisposable.isDisposed()) {
            pictureTakeDisposable.dispose();
        }
        pictureTakeDisposable = takePhoto.takePictureSingle()
                .subscribe(file ->
                        screen.displayImageInGallery(new Image(file, "camera photo",
                                Calendar.getInstance().getTime().toString(), "from camera")));
    }


}
