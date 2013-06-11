package com.ratbr.learn.imagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by brathod on 6/10/13.
 */
public class SearchSettingsActivity extends Activity {
    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    private EditText etSiteFilter;

    private SearchSettings settings;
    private String queryString;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_settings);
        settings = getIntent().getExtras().getParcelable(SearchSettings.SEARCHSETTINGS);
        queryString = getIntent().getStringExtra(SearchActivity.CURRENTQUERY);
        if (queryString == null) {
            queryString = "";
        }
        setupViews();
    }

    private void setupViews() {
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);

        spImageSize.setSelection(getIndex(spImageSize, settings.getImageSize()));
        spColorFilter.setSelection(getIndex(spColorFilter, settings.getColorFilter()));
        spImageType.setSelection(getIndex(spImageType, settings.getImageType()));
        etSiteFilter.setText(settings.getSiteFilter());

        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long l) {
                String selected = parentView.getItemAtPosition(position).toString();
                Object tagObj = parentView.getTag();

                String tag = "";
                if (tagObj != null) {
                    tag = tagObj.toString();
                }

                if (tag.equalsIgnoreCase("imgsz")) {
                    settings.setImageSize(selected);
                } else if (tag.equalsIgnoreCase("imgtype")) {
                    settings.setImageType(selected);
                } else if (tag.equalsIgnoreCase("imgcolor")) {
                    settings.setColorFilter(selected);
                }

                Toast.makeText(parentView.getContext(), tag + "=" + selected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        spImageSize.setOnItemSelectedListener(listener);
        spImageType.setOnItemSelectedListener(listener);
        spColorFilter.setOnItemSelectedListener(listener);
    }

    public void onSaveSettings(View view) {
        String siteFilter = etSiteFilter.getText().toString();

        if ( (siteFilter == null) || (siteFilter.isEmpty())) {
            returnToSearchActivity();
        } else if (URLUtil.isValidUrl(siteFilter.toLowerCase())) {
            returnToSearchActivity();
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "Invalid URL for Site Filter: " + siteFilter,
                    Toast.LENGTH_SHORT).show();

        }
    }

    private void returnToSearchActivity() {
        Log.e("ratbr", "querystring = " + queryString);
        Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
        intent.putExtra(SearchSettings.SEARCHSETTINGS, settings);
        intent.putExtra(SearchActivity.CURRENTQUERY, queryString);
        startActivity(intent);
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
            }
        }
        return index;
    }

}