package ai.vi.samples.externaltracker;

import android.app.Application;

import ai.vi.mobileads.adapter.countly.ViMobileAdsCountlyTracker;
import ai.vi.mobileads.api.ViOptions;
import ai.vi.mobileads.api.ViSdk;
import ly.count.android.sdk.Countly;
import ly.count.android.sdk.DeviceId;

public class ExternalTrackerSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ViOptions viOptions = new ViOptions();
        viOptions.setDebuggable(true); //Remove for production

        ViSdk.init(this, viOptions);

        Countly.sharedInstance().init(this, "Server URL", "App Key", null, DeviceId.Type.ADVERTISING_ID); //Before launch please fill correct data

        ViSdk.getInstance().registerExternalAdapter(new ViMobileAdsCountlyTracker());
    }
}
