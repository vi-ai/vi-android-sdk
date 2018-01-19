package ai.vi.samples.mediationfacebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ai.vi.mobileads.api.ViAdCallback;
import ai.vi.mobileads.api.ViAdError;
import ai.vi.mobileads.api.ViAdEvent;
import ai.vi.mobileads.api.ViAdPlacement;
import ai.vi.mobileads.api.ViInterstitialAd;
import ai.vi.mobileads.api.ViSdk;
import ai.vi.mobileads.mediation.facebook.FacebookViInterstitialMediationAdapter;

public class FacebookInterstitialSampleActivity extends AppCompatActivity implements ViAdCallback{
    public static final String LOG_TAG = FacebookInterstitialSampleActivity.class.getSimpleName();

    private ViInterstitialAd viInterstitialAd;

    private Button loadAd;
    private Button playAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interstitial_activity);

        loadAd = (Button) findViewById(R.id.load_ad);
        playAd = (Button) findViewById(R.id.start_ad);

        playAd.setEnabled(false);

        String placementId = "plte1mikarbhbtiephy"; //This placement is for test only, to receive personal placementId please register at https://vi.ai/publisher-video-monetization/

        ViAdPlacement viAdPlacement = ViAdPlacement.create(placementId);
        viInterstitialAd = ViSdk.getInstance().createInterstitialAd(viAdPlacement, this);
        viInterstitialAd.registerMediatedAdapter(new FacebookViInterstitialMediationAdapter(this, "Facebook placement ID"));

        loadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!viInterstitialAd.isLoading() && !viInterstitialAd.isReady()) {
                    viInterstitialAd.loadAd();
                } else {
                    Toast.makeText(FacebookInterstitialSampleActivity.this, "Ad ready or currently loading", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viInterstitialAd.isReady()) {
                    playAd.setEnabled(false);
                    viInterstitialAd.startAd();
                } else {
                    Toast.makeText(FacebookInterstitialSampleActivity.this, "Ad is not prepared", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAdEvent(ViAdEvent viAdEvent) {
        Log.d(LOG_TAG, viAdEvent.type.toString());
        Toast.makeText(FacebookInterstitialSampleActivity.this, "Ad event: " + viAdEvent, Toast.LENGTH_SHORT).show();

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
                playAd.setEnabled(true);
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
        Toast.makeText(FacebookInterstitialSampleActivity.this, "Ad error: " + viAdError, Toast.LENGTH_SHORT).show();

        switch (viAdError.type){
            case AD_PLAYBACK_ERROR:{
                break;
            }
            case AD_LOADING_ERROR:{

            }
        }
    }
}
