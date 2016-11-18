
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

package com.handm.dhruval.phunapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.handm.dhruval.phunapp.R;
import com.handm.dhruval.phunapp.helper.FeedSharingHelper;
import com.handm.dhruval.phunapp.helper.RoundedTransformation;
import com.handm.dhruval.phunapp.model.CardInfo;
import com.handm.dhruval.phunapp.ui.FeedDetailScreenActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dhruval on 11/17/2016.
 */

public class CardInfoAdapter extends RecyclerView.Adapter<CardInfoAdapter.ViewHolder> {
    private List<CardInfo> cardInfoList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleView;
        private TextView locationView;
        private TextView descriptionView;
        private TextView share;
        private View view;

        public ViewHolder(View view) {
            super(view);

            descriptionView = (TextView) view.findViewById(R.id.description);
            titleView = (TextView) view.findViewById(R.id.title);
            locationView = (TextView) view.findViewById(R.id.location);
            imageView = (ImageView) view.findViewById(R.id.image);
            share = (TextView)view.findViewById(R.id.share);
            this.view = view;
        }
    }

    public CardInfoAdapter(Context context, List<CardInfo> cardInfoList) {
        this.context = context;
        this.cardInfoList = cardInfoList;
    }

    @Override
    public CardInfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CardInfo cardInfo = cardInfoList.get(position);

        holder.titleView.setText(cardInfo.title);
        holder.descriptionView.setText(cardInfo.description);
        holder.locationView.setText(cardInfo.location1 + ", " + cardInfo.location2);

        Picasso.with(context)
                .load(cardInfo.image)
                .resize(50, 50)
                .centerCrop()
                .transform(new RoundedTransformation(100, 0))
                .placeholder(R.drawable.placeholder_nomoon)
                .error(R.drawable.placeholder_nomoon)
                .into(holder.imageView);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeedDetailsScreen(context, cardInfo);
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedSharingHelper feedSharingHelper = new FeedSharingHelper(context);
                feedSharingHelper.shareClickAction(cardInfo.description);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardInfoList.size();
    }

    /** Passed updated cardInfoList so adapter can update with detail list */
    public void setCardInfoList(List<CardInfo> cardInfoList){
        this.cardInfoList = cardInfoList;
    }

    /** Initiate Intent and put details in it and jump to FeedDetailScreenActivity. */
    private void openFeedDetailsScreen(Context context, CardInfo cardInfo) {
        Intent intent = new Intent(context, FeedDetailScreenActivity.class);
        intent.putExtra(CardInfo.JSON_TITLE, cardInfo.title);
        intent.putExtra(CardInfo.JSON_TIMESTAMP, cardInfo.timeStamp);
        intent.putExtra(CardInfo.JSON_DESCRIPTION, cardInfo.description);
        intent.putExtra(CardInfo.JSON_IMAGE, cardInfo.image);
        intent.putExtra(CardInfo.JSON_PHONE, cardInfo.phoneNum);

        context.startActivity(intent);
    }
}

