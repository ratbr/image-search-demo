package com.ratbr.learn.imagesearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {
    public static final String CURRENTQUERY = "currentquery";
    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    List<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter adapter;

    SearchSettings settings;
    int start = 0;

    public static final String  SEARCH_URL_PREFIX = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&q=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();

        inferIntent(getIntent());

        if (settings == null) {
            settings = new SearchSettings();
        }

        adapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ImageDisplayActivity.class);
                ImageResult imageResult = imageResults.get(i);
                intent.putExtra("url", imageResult.getFullUrl());
                startActivity(intent);
            }
        });
    }

    private void inferIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                settings = bundle.getParcelable(SearchSettings.SEARCHSETTINGS);
            }

            String currentQuery = intent.getStringExtra(CURRENTQUERY);
            if ( (currentQuery != null) && (!currentQuery.isEmpty())) {
                etQuery.setText(currentQuery);
            }
        }
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(getApplicationContext(), SearchSettingsActivity.class);
        intent.putExtra(SearchSettings.SEARCHSETTINGS, settings);
        intent.putExtra(CURRENTQUERY, etQuery.getText().toString());
        startActivity(intent);
        return true;
    }

    public void onImageSearch(View view) {
        String query = etQuery.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        //https://ajax.googleapis.com/ajax/services/search/images?q=Android&v=1.0
        String currentQuery = SEARCH_URL_PREFIX + Uri.encode(query) + "&start=" + start;
        String settingsString = settings.buildQueryParams();
        if (!settingsString.isEmpty()) {
            currentQuery = currentQuery + "&" + settingsString;
        }
        Log.d("DEBUG", currentQuery);

        client.get(currentQuery, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(JSONObject response) {
                JSONArray imageJsonResults = null;

                try {
                    imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    adapter.addAll(ImageResult.fromJsonArray(imageJsonResults));

                    Log.d("DEBUG", imageResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void onLoadMore(View view) {
        start += 8;
        onImageSearch(view);
    }
    
}
