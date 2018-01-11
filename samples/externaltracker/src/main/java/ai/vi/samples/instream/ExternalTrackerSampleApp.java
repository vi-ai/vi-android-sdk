package ai.vi.samples.instream;

import android.app.Application;

import ai.vi.mobileads.adapter.countly.ViMobileAdsCountlyTracker;
import ai.vi.mobileads.api.ViSdk;
import ly.count.android.sdk.Countly;
import ly.count.android.sdk.DeviceId;

public class ExternalTrackerSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ViSdk.init(this);

        Countly.sharedInstance().init(this, "Server URL", "App Key", null, DeviceId.Type.ADVERTISING_ID);

        ViSdk.getInstance().registerExternalAdapter(new ViMobileAdsCountlyTracker());
    }
}
