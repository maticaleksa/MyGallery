package com.example.mygallery.general;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private BasePresenter presenter;

    public void setPresenter(BasePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        if (presenter!=null)
            presenter.removeDisposables();
        super.onDestroy();
    }
}
