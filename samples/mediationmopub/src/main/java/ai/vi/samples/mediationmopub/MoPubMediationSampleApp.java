package ai.vi.samples.mediationmopub;

import android.app.Application;

import ai.vi.mobileads.api.ViOptions;
import ai.vi.mobileads.api.ViSdk;

public class MoPubMediationSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ViOptions viOptions = new ViOptions();
        viOptions.setDebuggable(true); //Remove for production

        ViSdk.init(this, viOptions);
    }
}
