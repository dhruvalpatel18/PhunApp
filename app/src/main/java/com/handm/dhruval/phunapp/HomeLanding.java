package com.handm.dhruval.phunapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.handm.dhruval.phunapp.model.CardInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeLanding extends AppCompatActivity {

    private static final String LOG_TAG = HomeLanding.class.getSimpleName();
    private static final String BASE_URL = "https://raw.githubusercontent.com/phunware/dev-interview-homework/master/feed.json";
    private LinearLayout linearLayout;

    RecyclerView recyclerView;
    List<CardInfo> cardInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_landing);

        getFeed();
    }

    private void getFeed() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                parseResponseToCardInfo(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                Log.d(LOG_TAG, "Failed to get response" + statusCode);
            }
        });
    }

    private void parseResponseToCardInfo(JSONArray response) {

        Log.i(LOG_TAG, response.toString());

    }
}
