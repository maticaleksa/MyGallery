package com.example.mygallery;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.mygallery.exceptions.UnknownNetworkException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class FileDownloader {

    private final ApiEndpointsInterface apiEndpointsInterface;
    private final Context context;

    @Inject
    public FileDownloader(ApiEndpointsInterface apiEndpointsInterface, Context context) {
        this.apiEndpointsInterface = apiEndpointsInterface;
        this.context = context;
    }

    @NonNull
    public File downloadFile(@NonNull String url, @NonNull String fileName, @NonNull String suffix) throws IOException, UnknownNetworkException {
        File file = File.createTempFile(fileName, suffix, context.getExternalCacheDir());
        OutputStream stream = new FileOutputStream(file);
        Response<ResponseBody> response = apiEndpointsInterface.getImage(url).execute();
        if (response.body() == null) {
            throw UnknownNetworkException.couldNotDownloadImage();
        }
        InputStream inputStream = response.body().byteStream();
        byte[] b = new byte[2048];
        int length;

        while ((length = inputStream.read(b)) != -1) {
            stream.write(b, 0, length);
        }
        stream.close();
        return file;
    }
}
