package com.example.picture_taking_module;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

@PictureTakingSingleton
public class PictureTakerSubject {

    private Subject<File> subject = PublishSubject.create();

    @Inject
    public PictureTakerSubject(){
        subject = subject.toSerialized();
    }

    public Subject<File> getSubject() {
        return subject;
    }
}
