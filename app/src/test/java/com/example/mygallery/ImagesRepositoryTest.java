package com.example.mygallery;

import android.content.Context;
import android.os.Environment;

import com.example.mygallery.domain.Image;
import com.example.mygallery.exceptions.NoInternetException;
import com.example.mygallery.exceptions.UnknownNetworkException;
import com.example.mygallery.general.Result;
import com.example.mygallery.network_domain.NetworkImage;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ImagesRepositoryTest {

    private ApiEndpointsInterface apiEndpointsInterface;
    private FileDownloader fileDownloader;
    private Context context;
    private ImagesRepository imagesRepository;
    private Scheduler testScheduler;
    private File file;
    @Before
    public void setup() throws IOException, UnknownNetworkException {
        file = new File("file");
        testScheduler = Schedulers.trampoline();
        context = mockContext();
        fileDownloader = generateFileDownloader();
        apiEndpointsInterface = mock(ApiEndpointsInterface.class);
    }

    @Test
    public void testGetAllReturnsImageSuccessfully() throws IOException {
        NetworkImage networkImage = getNetworkImage();
        Call<List<NetworkImage>> call = generateSuccessfulCalls(networkImage);
        when(apiEndpointsInterface.getImages()).thenReturn(call);
        imagesRepository = new ImagesRepository(apiEndpointsInterface, fileDownloader, context,
                generateConnectionChecker(true), testScheduler, testScheduler);
        Result<Image> expectedResult = Result.success(new Image(file, networkImage.title,
                networkImage.date, networkImage.comment));
        imagesRepository.getAll()
                .test()
                .assertValue(imageResult -> imageResult.equals(expectedResult));
    }

    @Test
    public void testGetAllReturnsUnsuccessfulResultWhenNoNetwork() {
        imagesRepository = new ImagesRepository(apiEndpointsInterface, fileDownloader, context,
                generateConnectionChecker(false), testScheduler, testScheduler);
        imagesRepository.getAll()
                .test()
                .assertValue(imageResult -> imageResult.equals(Result.error(new NoInternetException())));
    }



    private Context mockContext() {
        context = mock(Context.class);
        File f = mock(File.class);
        when(f.listFiles()).thenReturn(new File[0]);
        when(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)).thenReturn(f);
        return context;
    }


    private Call<List<NetworkImage>> generateSuccessfulCalls(NetworkImage networkImage) throws IOException {
        Call<List<NetworkImage>> call = mock(Call.class);
        Response<List<NetworkImage>> response =
                Response.success(Collections.singletonList(networkImage));
        when(call.execute()).thenReturn(response);
        return call;
    }

    private NetworkImage getNetworkImage() {
        return new NetworkImage("comment", "url","date", "title");
    }

    private InternetConnectionChecker generateConnectionChecker(boolean connected) {
        InternetConnectionChecker checker = mock(InternetConnectionChecker.class);
        Observable<Boolean> connectionObs = Observable.just(connected);
        when(checker.isConnected()).thenReturn(connectionObs);
        return checker;
    }

    private FileDownloader generateFileDownloader() throws IOException, UnknownNetworkException {
        FileDownloader fd = mock(FileDownloader.class);
        when(fd.downloadFile(any(), any(), any())).thenReturn(file);
        return fd;
    }

}