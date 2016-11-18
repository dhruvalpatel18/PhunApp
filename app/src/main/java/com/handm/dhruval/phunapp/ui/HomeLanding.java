
/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.handm.dhruval.phunapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handm.dhruval.phunapp.R;
import com.handm.dhruval.phunapp.adapter.CardInfoAdapter;
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
    private GridLayoutManager gridLayoutManager;

    CardInfoAdapter cardInfoAdapter;
    RecyclerView recyclerView;
    TextView emptyTextView;
    ProgressBar progressBar;
    List<CardInfo> cardInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_landing);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth / 400);

        gridLayoutManager = new GridLayoutManager(this, columns);
        recyclerView.setLayoutManager(gridLayoutManager);

        emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        emptyTextView.setText(R.string.loading);

        progressBar = (ProgressBar) findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        cardInfoAdapter = new CardInfoAdapter(this, cardInfoList);
        recyclerView.setAdapter(cardInfoAdapter);

        getFeed();

    }

    /** Call to BASE_URL and get Json date and will pass response to parse data. */
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

    /** Parse Json response and create List of CardInfo class' objects. */
    private void parseResponseToCardInfo(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);

                CardInfo cardInfo = new CardInfo(jsonObject);
                cardInfoList.add(cardInfo);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Failed to parse json object", e);
            }
        }

        if (cardInfoList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);

            cardInfoAdapter.setCardInfoList(cardInfoList);
            cardInfoAdapter.notifyDataSetChanged();
        }
    }
}
