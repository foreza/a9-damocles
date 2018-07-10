package com.vartyr.damocles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.amazon.device.ads.Ad;
import com.amazon.device.ads.AdError;
import com.amazon.device.ads.AdLayout;
import com.amazon.device.ads.AdProperties;
import com.amazon.device.ads.AdRegistration;
import com.amazon.device.ads.AdTargetingOptions;
import com.amazon.device.ads.DefaultAdListener;
import com.amazon.device.ads.InterstitialAd;

import static com.amazon.device.ads.AdProperties.AdType.IMAGE_BANNER;

public class Sword extends AppCompatActivity {

    private AdLayout adView;
    private InterstitialAd interstitialAd;
    private String LOG_TAG = "DAMOCLES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sword);

        AdRegistration.enableLogging(true);
        AdRegistration.enableTesting(true);


        try {
            AdRegistration.setAppKey("1d2f2130e1f04c148aea12ffc08134db"); // sample-app-v1_pub-2
            AdRegistration.registerApp(getApplicationContext());
        } catch (final IllegalArgumentException e) {
            Log.e(LOG_TAG, "IllegalArgumentException thrown: " + e.toString());
            return;
        }


        loadAdWithView();



    }

    @Override
    protected void onStart() {
        super.onStart();

        // Load a new interstitial each time we tre
        loadInterstitial();
    }

    // TODO: Implement
    protected void loadAdWithProgrammatic() {

    }


    // This method call will load an Ad into the view based off of the xml defined earlier
    protected void loadAdWithView() {
        // Create the AmazonAdLayout and bind to XML
        this.adView =  (AdLayout) findViewById(R.id.ad_view);
        this.adView.setListener(new SampleAdListener());
        this.adView.loadAd(); // Retrieves an ad on background thread
    }

    protected void loadInterstitial(){

        this.interstitialAd = new InterstitialAd(this);
        this.interstitialAd.setListener(new SampleAdListener());
        this.interstitialAd.loadAd();

        Log.e(LOG_TAG, "interstitialAd loadAd call made");


    }

    public void showInterstitial(View view){
        if (interstitialAd.isReady()){
            interstitialAd.showAd();
        }
        else {
            Log.i(LOG_TAG, "Interstitial not yet ready.");
        }
    }

    class SampleAdListener extends DefaultAdListener {
        /**
         * This event is called once an ad loads successfully.
         */
        @Override
        public void onAdLoaded(final Ad ad, final AdProperties adProperties) {
            Log.i(LOG_TAG, adProperties.getAdType().toString() + " ad loaded successfully.");
            if (adProperties.getAdType() == IMAGE_BANNER){
                adView.showAd();        // Will show the banner ad
            }
        }

        /**
         * This event is called if an ad fails to load.
         */
        @Override
        public void onAdFailedToLoad(final Ad ad, final AdError error) {
            Log.w(LOG_TAG, "Ad failed to load. Code: " + error.getCode() + ", Message: " + error.getMessage());
        }

        /**
         * This event is called after a rich media ad expands.
         */
        @Override
        public void onAdExpanded(final Ad ad) {
            Log.i(LOG_TAG, "Ad expanded.");
            // You may want to pause your activity here.
        }

        /**
         * This event is called after a rich media ad has collapsed from an expanded state.
         */
        @Override
        public void onAdCollapsed(final Ad ad) {
            Log.i(LOG_TAG, "Ad collapsed.");
            // Resume your activity here, if it was paused in onAdExpanded.
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.adView.destroy();
    }
}
