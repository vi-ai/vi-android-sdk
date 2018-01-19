package ai.vi.samples.mediationadmob;

import android.app.Application;

import ai.vi.mobileads.api.ViOptions;
import ai.vi.mobileads.api.ViSdk;

/**
 * @author Alexey Yur (ay@vi.ai)
 */

public class AdMobMediationSampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ViOptions viOptions = new ViOptions();
        viOptions.setDebuggable(true); //Remove for production

        ViSdk.init(this, viOptions);
    }
}
