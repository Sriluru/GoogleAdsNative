package com.example.googleadsnative;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdInspectorError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MediaAspectRatio;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnAdInspectorClosedListener;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import java.util.concurrent.LinkedBlockingQueue;

public class NativeAdsPool {

    private final Context context;
    private LinkedBlockingQueue<NativeAd> nativeAdsPool = null;
    private AdLoader adLoader;
    private String adUnit;
    /** Listen to the refresh event from pool to handle new feeds. */
    public interface OnPoolRefreshedListener {
        public void onPoolRefreshed();
    }

    public NativeAdsPool(Context context,String adUnit) {
        this.context = context;
        this.adUnit = adUnit;
        nativeAdsPool = new LinkedBlockingQueue<>();
    }

    public void init(final OnPoolRefreshedListener listener) {

        AdLoader.Builder builder = new AdLoader.Builder(this.context, adUnit);
        VideoOptions videoOptions;
        videoOptions = new VideoOptions.Builder().setStartMuted(false).setCustomControlsRequested(true).build();
        NativeAdOptions adOptions =
                new NativeAdOptions.Builder()
                        .setMediaAspectRatio(MediaAspectRatio.ANY)
                        .setVideoOptions(videoOptions)
                        .build();
        builder.withNativeAdOptions(adOptions);
        builder.forNativeAd(
                new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(final NativeAd nativeAd) {
                        push(nativeAd);
                        Log.v("Way2NewsTest","--"+nativeAd.getResponseInfo().getMediationAdapterClassName()+"---"+nativeAd.getResponseInfo().getLoadedAdapterResponseInfo().getAdSourceName());
                        listener.onPoolRefreshed();
                    }
                });
        builder.withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Toast.makeText(context, loadAdError.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Way2NewsTest", loadAdError.toString());
                    }
                });
        adLoader = builder.build();


    }

    public void push(NativeAd ad) {
        nativeAdsPool.add(ad);
    }

    public NativeAd pop() {
        return nativeAdsPool.poll();
    }

    public void refresh(int numberOfAds) {
        this.adLoader.loadAds(new AdRequest.Builder().build(), numberOfAds);
    }
}
