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

public class GAMAdsActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewFragment";
    private static final String ADMOB_AD_UNIT_ID = "/1022441/FBMediationGAM";

    protected RecyclerView recyclerView;
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
        MobileAds.openAdInspector(getApplicationContext(), new OnAdInspectorClosedListener() {
            public void onAdInspectorClosed(@Nullable AdInspectorError error) {
                // Error will be non-null if ad inspector closed due to an error.
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}