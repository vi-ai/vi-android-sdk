package ai.vi.samples.mediationmopub;

import android.app.Application;

import ai.vi.mobileads.api.ViSdk;

/**
 * @author Alexey Yur (ay@vi.ai)
 */

public class MoPubMediationSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ViSdk.init(this);
    }
}
