package com.example.mygallery;

import android.content.Context;
import android.os.Environment;

import com.example.mygallery.domain.Image;
import com.example.mygallery.exceptions.NoInternetException;
import com.example.mygallery.exceptions.UnknownNetworkException;
import com.example.mygallery.general.Result;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Retrieves images from the api and from local storage.
 */
public class ImagesRepository {

    private final ApiEndpointsInterface apiEndpointsInterface;
    private final FileDownloader fileDownloader;
    private final Context context;
    private final InternetConnectionChecker internetConnectionChecker;
    // These scheduler injections should be removed and rxjava plugins (in tests) should be used but I found out about it a bit late
    private final Scheduler mainThreadScheduler;
    private final Scheduler ioScheduler;

    @Inject
    public ImagesRepository(ApiEndpointsInterface apiEndpointsInterface,
                            FileDownloader fileDownloader,
                            Context context,
                            InternetConnectionChecker internetConnectionChecker,
                            @Named("main_thread") Scheduler mainThreadScheduler,
                            @Named("io") Scheduler ioScheduler) {
        this.apiEndpointsInterface = apiEndpointsInterface;
        this.fileDownloader = fileDownloader;
        this.context = context;
        this.internetConnectionChecker = internetConnectionChecker;
        this.mainThreadScheduler = mainThreadScheduler;
        this.ioScheduler = ioScheduler;
    }

    /**
     * Returns a stream that emits images from local storage and the api.
     */
    public Observable<Result<Image>> getAll() {
        Observable<Result<Image>> fromDisk = Observable.fromCallable(() ->
                Arrays.asList(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles()))
                .flatMapIterable(files -> files)
                .map(file -> Result.success(new Image(file, "camera photo",
                        new Date(file.lastModified()).toString(), "from camera")));


        return fromDisk.mergeWith(getFromNetwork());
    }

    /**
     * Returns a stream that emits images from the api.
     */
    public Observable<Result<Image>> getFromNetwork() {
        return internetConnectionChecker.isConnected()
                .flatMap(hasInternet -> {
                    if (!hasInternet) {
                        return Observable.just(Result.error(new NoInternetException()));
                    } else {
                        return Observable.fromCallable(() -> apiEndpointsInterface.getImages().execute())
                                .map(listResponse -> {
                                    if (listResponse.body() == null) {
                                        throw UnknownNetworkException.couldNotDownloadImagesMetaData();
                                    }
                                    return listResponse.body();
                                })
                                .flatMapIterable(networkImages -> networkImages)
                                .map(networkImage -> {
                                    File file = fileDownloader.downloadFile(networkImage.imageUrl,
                                            networkImage.title, ".jpg");
                                    return Result.success(new Image(file, networkImage.title,
                                            networkImage.date, networkImage.comment));
                                })
                                .observeOn(mainThreadScheduler)
                                .subscribeOn(ioScheduler);
                    }
                });
    }
}
