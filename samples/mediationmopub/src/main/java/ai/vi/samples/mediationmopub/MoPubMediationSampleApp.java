package ai.vi.samples.mediationmopub;

import android.app.Application;

import ai.vi.mobileads.api.ViSdk;

public class MoPubMediationSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ViSdk.init(this);
    }
}
