package com.example.mygallery.dagger_setup;


import com.example.mygallery.gallery_presentation.GalleryActivity;
import com.example.mygallery.image_display_presentation.ImageDisplayActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract GalleryActivity contributeAndroidInjector();

    @ContributesAndroidInjector
    abstract ImageDisplayActivity imageDisplayActivity();
}
