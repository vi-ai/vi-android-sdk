package ai.vi.samples.instream;

import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import ai.vi.mobileads.api.ViAdCallback;
import ai.vi.mobileads.api.ViAdError;
import ai.vi.mobileads.api.ViAdEvent;
import ai.vi.mobileads.api.ViAdPlacement;
import ai.vi.mobileads.api.ViAdView;
import ai.vi.mobileads.api.ViSdk;
import ai.vi.mobileads.api.ViVideoAd;

public class MainActivity extends AppCompatActivity implements ViAdCallback {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private String bunnyVideoPath;

    private Button playAd;
    private Button loadAd;

    private ViAdView viAdView;
    private ViVideoAd viVideoAd;
    private VideoView videoView;
    private RelativeLayout videoHolder;

    private boolean isCacheEnabled = true;
    private boolean isAutoPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instream_activity);
        bunnyVideoPath = "android.resource://" + getPackageName() + "/" + R.raw.trailer_480p;

        playAd = (Button) findViewById(R.id.play_ad);
        loadAd = (Button) findViewById(R.id.load_ad);
        videoView = (VideoView) findViewById(R.id.video_view);
        viAdView = (ViAdView) findViewById(R.id.ad_view);
        videoHolder = (RelativeLayout) findViewById(R.id.video_holder);

        String placementId = "pltdaysgxk6bliwd3n1"; //This placement is for test only, to receive personal placementId please register at https://vi.ai/publisher-video-monetization/

        ViAdPlacement placement = ViAdPlacement.create(placementId);
        viVideoAd = ViSdk.getInstance().createVideoAd(placement, viAdView, this);
        viVideoAd.setCacheEnable(isCacheEnabled);

        videoView.setVideoURI(Uri.parse(bunnyVideoPath));
        playAd.setEnabled(false);

        loadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viVideoAd.isReady() && !viVideoAd.isLoading()) {
                    viVideoAd.loadAd(isAutoPlay);
                } else {
                    Toast.makeText(MainActivity.this, "Ad is ready or loading", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viVideoAd.isReady()) {
                    playAd.setEnabled(false);
                    if(videoView.isPlaying()) {
                        videoView.pause();
                    }

                    viAdView.setVisibility(View.VISIBLE);
                    viVideoAd.startAd();
                } else {
                    Toast.makeText(MainActivity.this, "Ad not loaded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        handleVideoPlayback();
    }

    private void handleVideoPlayback() {
        if(videoView.getCurrentPosition() > videoView.getDuration()) {
            videoView.seekTo(0);
        }

        videoView.start();
    }

    @Override
    public void onAdEvent(ViAdEvent viAdEvent) {
        Toast.makeText(MainActivity.this, "ViAdEvent: " + viAdEvent.type, Toast.LENGTH_SHORT).show();
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
                handleVideoPlayback();
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
        Toast.makeText(MainActivity.this, "ViAdError: " + viAdError.message, Toast.LENGTH_SHORT).show();
        Log.d(LOG_TAG, viAdError.type.toString());

        switch (viAdError.type){
            case AD_PLAYBACK_ERROR:{
                break;
            }
            case AD_LOADING_ERROR:{

            }
        }
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        handleAdViewSize(newConfig.orientation);
    }

    private void handleAdViewSize(final int orientation){
        final View parentView = (View) videoHolder.getParent();
        final int oldWidht = parentView.getWidth();
        final int oldHeight = parentView.getHeight();

        videoHolder.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(oldWidht == parentView.getWidth() && oldHeight == parentView.getHeight()){
                    return;
                }

                videoHolder.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                ConstraintLayout.LayoutParams clp = (ConstraintLayout.LayoutParams) videoHolder.getLayoutParams();
                final View parentView = (View) videoHolder.getParent();

                float parentAspect = (float) parentView.getWidth() / (float) parentView.getHeight();
                float fullHdAspect = 16f / 9f;

                if(orientation == Configuration.ORIENTATION_LANDSCAPE && parentAspect > fullHdAspect){
                    clp.dimensionRatio = "W, 16:9";
                } else {
                    clp.dimensionRatio = "H, 16:9";
                }

                videoHolder.setLayoutParams(clp);
            }
        });
    }
}
