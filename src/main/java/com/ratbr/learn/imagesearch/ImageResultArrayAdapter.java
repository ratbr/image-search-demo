package com.ratbr.learn.imagesearch;

import android.*;
import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by brathod on 6/9/13.
 */
public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {
    public ImageResultArrayAdapter(Context context, List<ImageResult> imageResults) {
        super(context, com.ratbr.learn.imagesearch.R.layout.item_image_result, imageResults);
    }

    @Override
    public View getView(int position, //item pos in the array
                        View convertView, //reusable view
                        ViewGroup parent  //grid view

    ) {
        ImageResult imageInfo = this.getItem(position);
        SmartImageView ivImage;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            ivImage = (SmartImageView) inflater.inflate(com.ratbr.learn.imagesearch.R.layout.item_image_result, parent, false);
        } else {
            ivImage = (SmartImageView) convertView;
            ivImage.setImageResource(R.color.transparent);
            ivImage.setImageUrl(imageInfo.getThumbUrl());
        }

        return ivImage;


    }
}
