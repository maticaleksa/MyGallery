package com.example.mygallery.dagger_setup;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulerModule {

    @Provides
    @Named("main_thread")
    public static Scheduler mainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("io")
    public static Scheduler ioThreadScheduler() {
        return Schedulers.io();
    }
}
