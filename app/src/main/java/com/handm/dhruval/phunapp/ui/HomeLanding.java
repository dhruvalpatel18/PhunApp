package com.handm.dhruval.phunapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private RecyclerView.LayoutManager mLayoutManager;

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
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        emptyTextView = (TextView) findViewById(R.id.emptyTextView);
        emptyTextView.setText(R.string.loading);

        progressBar = (ProgressBar) findViewById(R.id.main_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        cardInfoAdapter = new CardInfoAdapter(this, cardInfoList);
        recyclerView.setAdapter(cardInfoAdapter);

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
