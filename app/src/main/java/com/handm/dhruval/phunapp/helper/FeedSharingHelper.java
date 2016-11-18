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
