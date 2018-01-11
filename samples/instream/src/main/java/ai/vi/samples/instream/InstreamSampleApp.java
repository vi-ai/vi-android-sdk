package ai.vi.samples.instream;

import android.app.Application;

import ai.vi.mobileads.api.ViSdk;


public class InstreamSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ViSdk.init(this);
    }
}
