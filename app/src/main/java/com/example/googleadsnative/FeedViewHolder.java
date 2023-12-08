package com.example.googleadsnative;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class FeedViewHolder extends RecyclerView.ViewHolder {
    public FeedViewHolder(View view) {
        super(view);
    }

    public abstract void attach();

    public abstract void detach();
}