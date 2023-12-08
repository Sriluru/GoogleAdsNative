package com.example.googleadsnative;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdInspectorError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnAdInspectorClosedListener;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt_admob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        txt_admob = findViewById(R.id.txt_admob);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Log.v("Way2NewsTest","--"+initializationStatus.getAdapterStatusMap());
                txt_admob.setText(initializationStatus.getAdapterStatusMap().toString());
            }
        });
        List<String> testDeviceIds = Arrays.asList("52797B51427B2D084226F9FBD77A7132","0AB9848A1103A259760E9F759AFD69B7");
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);


        findViewById(R.id.btnGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AdMobsActivity.class));
            }
        });
        findViewById(R.id.btnGAM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GAMAdsActivity.class));
            }
        });
        findViewById(R.id.btnGoogleBanner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GoogleBannerAdActivity.class));
            }
        });
        findViewById(R.id.btnBannerGAM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GAMBannerAdActivity.class));
            }
        });
    }
}