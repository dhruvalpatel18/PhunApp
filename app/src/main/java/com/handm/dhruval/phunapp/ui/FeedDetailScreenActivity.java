package com.handm.dhruval.phunapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.handm.dhruval.phunapp.R;
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

    private void initView() {
        title = (TextView)findViewById(R.id.detail_title);
        time = (TextView)findViewById(R.id.detail_time);
        imageView = (ImageView)findViewById(R.id.imageView);
        description = (TextView)findViewById(R.id.detail_description);
    }

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
}
