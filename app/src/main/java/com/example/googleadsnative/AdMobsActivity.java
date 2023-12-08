package com.example.googleadsnative;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.ads.AdInspectorError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnAdInspectorClosedListener;

public class AdMobsActivity extends AppCompatActivity {
    protected RecyclerView recyclerView;
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-7820948025280373/3269152438";
    protected FeedAdapter feedAdapter;
    protected RecyclerView.LayoutManager layoutManager;
    private NativeAdsPool nativeAdsPool;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // set FeedAdapter as the adapter for RecyclerView.
        feedAdapter = new FeedAdapter();
        recyclerView.setAdapter(feedAdapter);
        // setup PagerSnapHelper
        new PagerSnapHelper().attachToRecyclerView(recyclerView);
        nativeAdsPool = new NativeAdsPool(this,ADMOB_AD_UNIT_ID);
        nativeAdsPool.init(
                (NativeAdsPool.OnPoolRefreshedListener) () -> feedAdapter.add(new FeedAdapter.AdFeedItem(nativeAdsPool.pop())));
        nativeAdsPool.refresh(/* numOfAds= */ 1);

    }

}