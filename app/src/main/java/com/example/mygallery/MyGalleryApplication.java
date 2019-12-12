package com.example.mygallery;

import android.app.Application;

import com.akaita.java.rxjava2debug.RxJava2Debug;
import com.example.mygallery.dagger_setup.DaggerAppComponent;
import com.example.picture_taking_module.DaggerPictureTakingComponent;
import com.example.picture_taking_module.PictureTaking;
import com.example.picture_taking_module.PictureTakingComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class MyGalleryApplication extends Application implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        RxJava2Debug.enableRxJava2AssemblyTracking(new String[]{"com.example.mygallery"});
        PictureTakingComponent pictureTakingComponent = DaggerPictureTakingComponent.builder()
                .bindContext(getApplicationContext())
                .build();
        PictureTaking.setInstance(pictureTakingComponent);
        DaggerAppComponent.builder()
                .bindPictureTakingComponent(pictureTakingComponent)
                .bindContext(this)
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }
}
