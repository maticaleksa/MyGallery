package com.example.mygallery.image_display_presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mygallery.domain.Image;
import com.example.mygallery.R;

public class ImageDisplayActivity extends AppCompatActivity {

    public static final String IMAGE_EXTRA = "imageExtra";
    private Image image;
    private TextView title;
    private TextView comment;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_image_display);
        if (savedInstanceState != null &&savedInstanceState.get(IMAGE_EXTRA) != null) {
            image = (Image) savedInstanceState.get(IMAGE_EXTRA);
        } else {
            image = (Image) getIntent().getExtras().get(IMAGE_EXTRA);
        }
        title = findViewById(R.id.title);
        comment = findViewById(R.id.date);
        date = findViewById(R.id.date);
        Glide.with(this)
                .load(image.file)
                .into((ImageView) findViewById(R.id.image));
        setInfo();
    }

    private void setInfo() {
        title.setText(image.title);
        comment.setText(image.comment);
        date.setText(image.date);
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(IMAGE_EXTRA, image);
        super.onSaveInstanceState(outState);
    }
}
