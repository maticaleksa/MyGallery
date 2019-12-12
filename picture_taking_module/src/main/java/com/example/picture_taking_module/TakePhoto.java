package com.example.picture_taking_module;

import android.content.Context;
import android.content.Intent;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class TakePhoto {

    private final Context context;
    private final PictureTakerSubject pictureTakerSubject;

    @Inject
    public TakePhoto(Context context, PictureTakerSubject pictureTakerSubject) {
        this.context = context;
        this.pictureTakerSubject = pictureTakerSubject;
    }

    public Observable<File> takePictureSingle() {
        return pictureTakerSubject.getSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    Intent intent = new Intent(context, EmptyPictureTakingActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                });
    }
}
