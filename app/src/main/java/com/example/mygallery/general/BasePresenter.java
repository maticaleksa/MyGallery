package com.example.mygallery.general;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter {

    private CompositeDisposable compositeDisposable;

    public void addDisposable (Disposable d) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(d);
    }

    public void removeDisposables() {
        compositeDisposable.clear();
    }
}
