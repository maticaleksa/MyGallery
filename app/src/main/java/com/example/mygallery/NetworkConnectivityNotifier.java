package com.example.mygallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class NetworkConnectivityNotifier {

    ConnectivityManager cm;
    private PublishSubject<Boolean> connectivitySubject = PublishSubject.create();

    @Inject
    public NetworkConnectivityNotifier(Context context) {
        cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                    boolean connected = false;
                    if (cm.getActiveNetworkInfo() != null)
                        connected = cm.getActiveNetworkInfo().isConnected();
                    Log.d("asdz", "A " + connected);
                    connectivitySubject.onNext(connected);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public Observable<Boolean> getNotifier() {
        return connectivitySubject;
    }
}
