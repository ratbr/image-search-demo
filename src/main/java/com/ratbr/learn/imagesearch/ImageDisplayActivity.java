package com.ratbr.learn.imagesearch;

import android.app.Activity;
import android.os.Bundle;

import com.loopj.android.image.SmartImageView;

/**
 * Created by brathod on 6/9/13.
 */
public class ImageDisplayActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        String url = getIntent().getStringExtra("url");
        SmartImageView ivImage  = (SmartImageView) findViewById(R.id.ivResult);
        ivImage.setImageUrl(url);
    }


}