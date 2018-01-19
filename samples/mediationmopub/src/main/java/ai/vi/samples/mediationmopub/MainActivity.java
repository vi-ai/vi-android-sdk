package ai.vi.samples.mediationmopub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import ai.vi.mobileads.api.ViAdCallback;
import ai.vi.mobileads.api.ViAdError;
import ai.vi.mobileads.api.ViAdEvent;
import ai.vi.mobileads.api.ViAdPlacement;
import ai.vi.mobileads.api.ViInterstitialAd;
import ai.vi.mobileads.api.ViSdk;
import ai.vi.mobileads.mediation.mopub.MoPubViInterstitialMediationAdapter;

public class MainActivity extends AppCompatActivity implements ViAdCallback{
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private ViInterstitialAd viInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String placementId = "pltzz5odrd8hpomu6ix"; //This placement is for test only, to receive personal placementId please register at https://vi.ai/publisher-video-monetization/

        ViAdPlacement viAdPlacement = ViAdPlacement.create(placementId);
        viInterstitialAd = ViSdk.getInstance().createInterstitialAd(viAdPlacement, this);
        viInterstitialAd.registerMediatedAdapter(new MoPubViInterstitialMediationAdapter(this, "MoPub placement ID"));

        findViewById(R.id.load_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!viInterstitialAd.isLoading() && !viInterstitialAd.isReady()) {
                    viInterstitialAd.loadAd();
                } else {
                    Toast.makeText(MainActivity.this, "Ad ready or currently loading", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.start_ad).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viInterstitialAd.isReady()) {
                    viInterstitialAd.startAd();
                } else {
                    Toast.makeText(MainActivity.this, "Ad is not prepared", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAdEvent(ViAdEvent viAdEvent) {
        Log.d(LOG_TAG, viAdEvent.type.toString());
        Toast.makeText(MainActivity.this, "Ad event: " + viAdEvent, Toast.LENGTH_SHORT).show();

        switch (viAdEvent.type){
            case AD_PAUSED:{
                break;
            }
            case AD_CLICKED: {
                break;
            }
            case AD_CLOSE:{

                break;
            }
            case AD_COMPLETED:{

                break;
            }
            case AD_LOADED:{
                break;
            }
            case AD_RESUMED:{
                break;
            }
            case AD_STARTED:{

            }
        }
    }

    @Override
    public void onAdError(ViAdError viAdError) {
        Log.d(LOG_TAG, viAdError.type.toString());
        Toast.makeText(MainActivity.this, "Ad error: " + viAdError, Toast.LENGTH_SHORT).show();

        switch (viAdError.type){
            case AD_PLAYBACK_ERROR:{
                break;
            }
            case AD_LOADING_ERROR:{

            }
        }
    }
}
