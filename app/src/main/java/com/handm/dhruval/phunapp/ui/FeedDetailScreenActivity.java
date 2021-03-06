
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

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handm.dhruval.phunapp.R;
import com.handm.dhruval.phunapp.helper.FeedSharingHelper;
import com.handm.dhruval.phunapp.model.CardInfo;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeedDetailScreenActivity extends AppCompatActivity {
    private static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final String EXPECTED_DATE_FORMAT = "MMM dd, yyyy 'at' HH:mma";

    TextView title, time, description;
    ImageView imageView;
    String mPhoneNum;
    ScrollView scrollView;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        actionBar.setTitle("");

        setContentView(R.layout.activity_feed_detail_screen);

        initView();
        setViewContents();
    }

    /** Initialize all the view in the layout and set color in Actionbar if user scrolls. */
    private void initView() {
        title = (TextView)findViewById(R.id.detail_title);
        time = (TextView)findViewById(R.id.detail_time);
        imageView = (ImageView)findViewById(R.id.imageView);
        description = (TextView)findViewById(R.id.detail_description);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                final int scrollY = scrollView.getScrollY();

                if (scrollY > 0) {
                    actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
                }else {
                    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
                }
            }
        });
    }

    /** set Json data in to views. */
    private void setViewContents() {
        Intent intent = getIntent();

        parseDate(intent.getStringExtra(CardInfo.JSON_TIMESTAMP));

        title.setText(intent.getStringExtra(CardInfo.JSON_TITLE));
        time.setText(parseDate(intent.getStringExtra(CardInfo.JSON_TIMESTAMP)));
        description.setText(intent.getStringExtra(CardInfo.JSON_DESCRIPTION));
        mPhoneNum = intent.getStringExtra(CardInfo.JSON_PHONE);

        String detailImageURL = intent.getStringExtra(CardInfo.JSON_IMAGE);
        Picasso.with(getApplicationContext())
                .load(detailImageURL)
                .placeholder(R.drawable.placeholder_nomoon)
                .error(R.drawable.placeholder_nomoon)
                .into(imageView);
    }

    /** Parse date string in to date and change to expected format then convert again in to string. */
    public static String  parseDate(String dateTimeStr) {
        Date date = new Date();
        try {
            date = (new SimpleDateFormat(UTC_DATE_FORMAT)).parse(dateTimeStr.replaceAll("Z$", "+0000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedDate = new SimpleDateFormat(EXPECTED_DATE_FORMAT).format(date);
        return formattedDate.replace("AM", "am").replace("PM", "pm");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_call:
                if (mPhoneNum == null || mPhoneNum.isEmpty()) {
                    Toast.makeText(this, "Phone number not mentioned", Toast.LENGTH_LONG).show();
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + mPhoneNum));
                    startActivity(callIntent);
                }
                return true;
            case R.id.action_share:
                FeedSharingHelper feedSharingHelper = new FeedSharingHelper(this);
                feedSharingHelper.shareClickAction(description.getText().toString());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
