package ai.vi.samples.interstitial;

import android.app.Application;

import ai.vi.mobileads.api.ViOptions;
import ai.vi.mobileads.api.ViSdk;


public class InterstitialSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ViOptions viOptions = new ViOptions();
        viOptions.setDebuggable(true); //Remove for production

        ViSdk.init(this, viOptions);
    }
}
