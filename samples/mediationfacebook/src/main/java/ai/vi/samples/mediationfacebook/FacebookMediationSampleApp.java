package ai.vi.samples.mediationfacebook;

import android.app.Application;

import ai.vi.mobileads.api.ViSdk;

public class FacebookMediationSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ViSdk.init(this);
    }
}
