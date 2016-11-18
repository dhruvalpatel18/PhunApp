
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

package com.handm.dhruval.phunapp.helper;

import android.content.Context;
import android.content.Intent;

import com.handm.dhruval.phunapp.R;

/**
 * Created by dhruval on 11/17/2016.
 */

public class FeedSharingHelper {

    Context context;

    public FeedSharingHelper(Context context){
        this.context = context;
    }

    public void shareClickAction( String detail) {
        Intent shareIntent = new Intent();
        shareIntent.setType("text/plain");
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, detail);

        context.startActivity(Intent.createChooser(shareIntent, context.getResources().getString(R.string.share_to)));
    }
}
