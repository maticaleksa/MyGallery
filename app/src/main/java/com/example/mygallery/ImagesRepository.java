package com.example.mygallery;

import android.content.Context;
import android.os.Environment;

import com.example.mygallery.domain.Image;
import com.example.mygallery.general.Result;
import com.example.mygallery.network_domain.NetworkToDomainConverter;

import java.io.File;
import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ImagesRepository {

    private final ApiEndpointsInterface apiEndpointsInterface;
    private final NetworkToDomainConverter domainConverter;
    private final FileDownloader fileDownloader;
    private final Context context;

    @Inject
    public ImagesRepository(ApiEndpointsInterface apiEndpointsInterface,
                            NetworkToDomainConverter domainConverter,
                            FileDownloader fileDownloader,
                            Context context) {
        this.apiEndpointsInterface = apiEndpointsInterface;
        this.domainConverter = domainConverter;
        this.fileDownloader = fileDownloader;
        this.context = context;
    }

    public Observable<Result<Image>> getAll() {
        Observable<Result<Image>> fromDisk = Observable.fromCallable(() ->
                Arrays.asList(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles()))
                .flatMapIterable(files -> files)
                .map(file -> Result.success(new Image(file, "title", "from_camera")));


        return fromDisk.mergeWith(Observable.fromCallable(() -> apiEndpointsInterface.getImages().execute())
                .map(listResponse -> {
                    if (listResponse.body() == null) {
                        throw UnknownNetworkException.couldNotDownloadImagesMetaData();
                    }
                    return listResponse.body();
                })
                .flatMapIterable(networkImages -> networkImages)
                .map(networkImage -> {
                    File file = fileDownloader.downloadFile(networkImage.imageUrl, networkImage.title, ".jpg");
                    return Result.success(new Image(file, networkImage.title, networkImage.date));
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()));
    }

}
