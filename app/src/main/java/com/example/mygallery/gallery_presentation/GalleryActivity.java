package com.example.mygallery.gallery_presentation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mygallery.ErrorDialogFragment;
import com.example.mygallery.domain.Image;
import com.example.mygallery.image_display_presentation.ImageDisplayActivity;
import com.example.mygallery.R;
import com.example.mygallery.general.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class GalleryActivity extends BaseActivity implements GalleryScreen {

    private static int REQUEST_CODE = 2;

    @Inject
    GalleryPresenter presenter;

    private RecyclerView list;
    private List<Image> imageList = new ArrayList<>();
    private ImageListAdapter imageListAdapter = new ImageListAdapter(this);

    private ImageView imageView;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        setRecycler();

        findViewById(R.id.button).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    presenter.onTakePhotoPressed(this);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                }
            } else {
                presenter.onTakePhotoPressed(this);
            }
            });
        presenter.onDisplay(this);
    }

    private void setRecycler() {
        list = findViewById(R.id.list);
        list.setAdapter(imageListAdapter);
        list.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void displayImageInGallery(Image image) {
        imageList.add(image);
        imageListAdapter.setData(imageList);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            presenter.onTakePhotoPressed(this);
        }
    }

    @Override
    public void displayError(ErrorType errorType) {
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        switch (errorType) {
            case NO_INTERNET:
                errorDialogFragment.displayDialog(getString(R.string.no_internet_err), getSupportFragmentManager());
                break;
            case UNKNOWN:
                errorDialogFragment.displayDialog(getString(R.string.unknown_err), getSupportFragmentManager());
        }
    }

    @Override
    public void openImage(Image image) {
        Intent intent = new Intent(this, ImageDisplayActivity.class);
        intent.putExtra(ImageDisplayActivity.IMAGE_EXTRA, image);
        startActivity(intent);
    }
}
