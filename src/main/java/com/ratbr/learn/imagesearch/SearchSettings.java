package com.ratbr.learn.imagesearch;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by brathod on 6/10/13.
 */
public class SearchSettings implements Parcelable {
    public static final String SEARCHSETTINGS = "searchsettings";
    private String imageSize = "icon";
    private String imageType = "face";
    private String colorFilter = "purple";
    private String siteFilter = "";


    public SearchSettings() {

    }

    public SearchSettings(Parcel in) {
        imageSize = in.readString();
        imageType = in.readString();
        colorFilter = in.readString();
        siteFilter = in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(imageSize);
        parcel.writeString(imageType);
        parcel.writeString(colorFilter);
        parcel.writeString(siteFilter);
    }

    public String getImageSize() {
        return imageSize;
    }

    public void setImageSize(String imageSize) {
        this.imageSize = imageSize;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getColorFilter() {
        return colorFilter;
    }

    public void setColorFilter(String colorFilter) {
        this.colorFilter = colorFilter;
    }

    public String getSiteFilter() {
        return siteFilter;
    }

    public void setSiteFilter(String siteFilter) {
        this.siteFilter = siteFilter;
    }

    public String buildQueryParams() {
        StringBuffer sb = new StringBuffer();

        if ((colorFilter != null) && (!colorFilter.isEmpty())) {
            sb.append("imgcolor=").append(colorFilter);
        }
        if ((imageSize != null) && (!imageSize.isEmpty())) {
            if (sb.length() != 0) sb.append("&");
            sb.append("imgsz=").append(imageSize);
        }
        if ((imageType != null) && (!imageType.isEmpty())) {
            if (sb.length() != 0) sb.append("&");
            sb.append("imgtype=").append(imageType);
        }
        if ((siteFilter != null) && (!siteFilter.isEmpty())) {
            if (sb.length() != 0) sb.append("&");
            sb.append("as_sitesearch=").append(colorFilter);
        }

        return sb.toString();
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public SearchSettings createFromParcel(Parcel in) {
                    return new SearchSettings(in);
                }

                public SearchSettings[] newArray(int size) {
                    return new SearchSettings[size];
                }
            };
}