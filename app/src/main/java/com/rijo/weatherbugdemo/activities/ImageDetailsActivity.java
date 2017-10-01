package com.rijo.weatherbugdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rijo.weatherbugdemo.R;
import com.rijo.weatherbugdemo.model.Image;
import com.rijo.weatherbugdemo.model.Images;

public class ImageDetailsActivity extends AppCompatActivity {

    private static final String IMAGE_BASE_URL="https://s3.amazonaws.com/sc.va.util.weatherbug.com/interviewdata/mobilecodingchallenge/";
    private Image image;
    TextView tittleTV,descriptionTV;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        if (savedInstanceState != null) {
            //restoring from previous state
            image=savedInstanceState.getParcelable(Image.KEY);
        }
        else {
            image=getIntent().getParcelableExtra(Image.KEY);
        }
        tittleTV=(TextView)findViewById(R.id.titleTV);
        descriptionTV=(TextView)findViewById(R.id.descriptionTV);
        imageView=(ImageView)findViewById(R.id.imageView);
        setupGui();
    }

    private void setupGui() {
        Glide.with(getApplicationContext())
                .load(IMAGE_BASE_URL+image.getFilename())
                .into(imageView);
        tittleTV.setText(image.getTitle());
        descriptionTV.setText(image.getDescription());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Image.KEY,image);
    }
}
