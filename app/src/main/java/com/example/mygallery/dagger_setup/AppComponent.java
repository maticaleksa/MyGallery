package com.example.mygallery.dagger_setup;

import android.content.Context;

import com.example.mygallery.MyGalleryApplication;
import com.example.picture_taking_module.PictureTakingUnscopedComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
@Singleton
@Component(dependencies = PictureTakingUnscopedComponent.class,
        modules = {NetworkModule.class, ActivitiesModule.class, AndroidInjectionModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder bindContext(Context context);

        Builder bindPictureTakingComponent(PictureTakingUnscopedComponent component);
    }

    void inject (MyGalleryApplication application);
    PictureTakingUnscopedComponent pictureTakingComponent();
}
