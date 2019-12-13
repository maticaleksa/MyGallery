package com.example.mygallery.gallery_presentation;

import com.example.mygallery.ImagesRepository;
import com.example.mygallery.NetworkConnectivityNotifier;
import com.example.mygallery.domain.Image;
import com.example.mygallery.exceptions.NoInternetException;
import com.example.mygallery.general.Result;
import com.example.picture_taking_module.TakePhoto;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GalleryPresenterTest {

    private GalleryPresenter presenter;
    private NetworkConnectivityNotifier networkConnectivityNotifier;
    private TakePhoto takePhoto;
    private PublishSubject<Boolean> connectivitySubject = PublishSubject.create();
    private ImagesRepository imagesRepository;
    private GalleryScreen screen;

    @Before
    public void setUp() {
        screen = spy(GalleryScreen.class);
        networkConnectivityNotifier = mock(NetworkConnectivityNotifier.class);
        when(networkConnectivityNotifier.getNotifier()).thenReturn(connectivitySubject);
    }

    @Test
    public void testDisplaysPicturesWhenThereIsNetwork() {
        imagesRepository = mock(ImagesRepository.class);
        Image image = mock(Image.class);
        Observable<Result<Image>>  imageObs = Observable.just(Result.success(image));
        when(imagesRepository.getAll()).thenReturn(imageObs);
        presenter = new GalleryPresenter(networkConnectivityNotifier, imagesRepository, getTakePhoto());
        presenter.onDisplay(screen);
        verify(screen).displayImageInGallery(image);
    }

    @Test
    public void testNoInternetDisplayedWhenNoInternet() {
        imagesRepository = mock(ImagesRepository.class);
        Observable<Result<Image>> obs = Observable.just(Result.error(new NoInternetException()));
        when(imagesRepository.getAll()).thenReturn(obs);
        presenter = new GalleryPresenter(networkConnectivityNotifier, imagesRepository, getTakePhoto());
        presenter.onDisplay(screen);
        verify(screen).displayError(ErrorType.NO_INTERNET);
    }

    @Test
    public void testImagesAreDisplayedWhenNetworkReturns() {
        imagesRepository = mock(ImagesRepository.class);
        Observable<Result<Image>> obs = Observable.just(Result.error(new NoInternetException()));
        when(imagesRepository.getAll()).thenReturn(obs);
        presenter = new GalleryPresenter(networkConnectivityNotifier, imagesRepository, getTakePhoto());
        presenter.onDisplay(screen);
        Image image = mock(Image.class);
        obs = Observable.just(Result.success(image));
        when(imagesRepository.getFromNetwork()).thenReturn(obs);
        connectivitySubject.onNext(true);
        verify(screen).displayImageInGallery(image);
    }


    // TODO: 12/13/2019 implement more tests...
    private TakePhoto getTakePhoto() {
        return mock(TakePhoto.class);
    }

}