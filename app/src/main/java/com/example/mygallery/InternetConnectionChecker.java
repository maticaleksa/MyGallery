package com.example.mygallery;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import io.reactivex.Observable;

public class InternetConnectionChecker {

    private final Context context;
    private final ConnectivityManager connectivityManager;

    @Inject
    public InternetConnectionChecker(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Observable<Boolean> isConnected() {
        return Observable.fromCallable(() -> {
            NetworkInfo networkInfo =  connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null || !networkInfo.isConnected()) {
                return false;
            }
            return true;
        });
    }
}
