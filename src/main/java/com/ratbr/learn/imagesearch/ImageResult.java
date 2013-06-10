package com.ratbr.learn.imagesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by brathod on 6/9/13.
 */
public class ImageResult {
    private String fullUrl;
    private String thumbUrl;

    public ImageResult(JSONObject jsonObject) {
        try {
            this.fullUrl = jsonObject.getString("url");
            this.thumbUrl = jsonObject.getString("tbUrl");
        } catch (JSONException e) {
            this.fullUrl = null;
            this.thumbUrl = null;
        }
    }

    @Override
    public String toString() {
        return this.thumbUrl;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public static List<ImageResult> fromJsonArray(JSONArray imageJsonResults) {
        List<ImageResult> results = new ArrayList<ImageResult>();

        for (int i=0; i < imageJsonResults.length();i++) {
            try {
                results.add(new ImageResult(imageJsonResults.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }
}
