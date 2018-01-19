package ai.vi.samples.externaltracker;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import ai.vi.mobileads.api.ViAdCallback;
import ai.vi.mobileads.api.ViAdError;
import ai.vi.mobileads.api.ViAdEvent;
import ai.vi.mobileads.api.ViAdPlacement;
import ai.vi.mobileads.api.ViAdView;
import ai.vi.mobileads.api.ViSdk;
import ai.vi.mobileads.api.ViVideoAd;
import ai.vi.samples.instream.R;

public class MainActivity extends AppCompatActivity implements ViAdCallback {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private String bunnyVideoPath;

    private Button playAd;

    private ViAdView viAdView;
    private ViVideoAd viVideoAd;
    private VideoView videoView;

    private boolean isCacheEnabled = true;
    private boolean isAutoPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instream_activity);
        bunnyVideoPath = "android.resource://" + getPackageName() + "/" + R.raw.trailer_480p;

        playAd = (Button) findViewById(R.id.play_ad);
        videoView = (VideoView) findViewById(R.id.video_view);
        viAdView = (ViAdView) findViewById(R.id.ad_view);

        String placementId = "pltuaisg4nafb0tdac2"; //This placement is for test only, to receive personal placementId please register at https://vi.ai/publisher-video-monetization/

        ViAdPlacement placement = ViAdPlacement.create(placementId);
        viVideoAd = ViSdk.getInstance().createVideoAd(placement, viAdView, this);
        viVideoAd.setCacheEnable(isCacheEnabled);
        viVideoAd.loadAd(isAutoPlay);

        playAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viVideoAd.isReady()) {
                    viAdView.setVisibility(View.VISIBLE);
                    viVideoAd.startAd();
                }
            }
        });
    }

    private void playVideo() {
        videoView.setVideoURI(Uri.parse(bunnyVideoPath));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });
    }

    @Override
    public void onAdEvent(ViAdEvent viAdEvent) {
        Log.d(LOG_TAG, viAdEvent.type.toString());

        switch (viAdEvent.type){
            case AD_PAUSED:{
                break;
            }
            case AD_CLICKED: {
                break;
            }
            case AD_CLOSE:
            case AD_COMPLETED:{
                viAdView.setVisibility(View.GONE);
                playVideo();
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

        switch (viAdError.type){
            case AD_PLAYBACK_ERROR:{
                break;
            }
            case AD_LOADING_ERROR:{

            }
        }
    }
}
