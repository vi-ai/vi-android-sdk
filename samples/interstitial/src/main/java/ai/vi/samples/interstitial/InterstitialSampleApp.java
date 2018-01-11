package ai.vi.samples.interstitial;

import android.app.Application;

import ai.vi.mobileads.api.ViExternalAdTracker;
import ai.vi.mobileads.api.ViSdk;


public class InterstitialSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ViSdk.init(this);
    }
}
