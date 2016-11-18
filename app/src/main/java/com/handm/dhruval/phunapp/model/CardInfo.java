
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

package com.handm.dhruval.phunapp.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dhruval on 11/17/2016.
 */

public class CardInfo {
    private static final String LOG_TAG = CardInfo.class.getSimpleName();

    public static final String JSON_ID = "id";
    public static final String JSON_TITLE = "title";
    public static final String JSON_DESCRIPTION = "description";
    public static final String JSON_TIMESTAMP = "timestamp";
    public static final String JSON_IMAGE = "image";
    public static final String JSON_DATE = "date";
    public static final String JSON_LOCATION_LINE_1 = "locationline1";
    public static final String JSON_LOCATION_LINE_2 = "locationline2";
    public static final String JSON_PHONE = "phone";

    public int cardID;
    public String title;
    public String description;
    public String timeStamp;
    public String image;
    public String date;
    public String location1;
    public String location2;
    public String phoneNum;

    public CardInfo(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                cardID = jsonObject.getInt(JSON_ID);
                title = jsonObject.getString(JSON_TITLE);
                description = jsonObject.getString(JSON_DESCRIPTION);
                timeStamp = jsonObject.getString(JSON_TIMESTAMP);
                image = jsonObject.getString(JSON_IMAGE);
                date = jsonObject.getString(JSON_DATE);
                location1 = jsonObject.getString(JSON_LOCATION_LINE_1);
                location2 = jsonObject.getString(JSON_LOCATION_LINE_2);
                if (jsonObject.has(JSON_PHONE)) {
                    phoneNum = jsonObject.getString(JSON_PHONE);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Failed to parse json object to cardInfo", e);
            }
        }
    }
}