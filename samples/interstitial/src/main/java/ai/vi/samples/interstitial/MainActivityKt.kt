package ai.vi.samples.interstitial

import ai.vi.mobileads.api.*
import ai.vi.samples.androidsamples.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityKt : AppCompatActivity(), ViAdCallback {

    private var viInterstitialAd: ViInterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val placementId = "plt366ozya7gvgvmxvm" //This placement is for test only, to receive personal placementId please register at https://vi.ai/publisher-video-monetization/

        val viAdPlacement = ViAdPlacement.create(placementId)
        viInterstitialAd = ViSdk.getInstance().createInterstitialAd(viAdPlacement, this)

        start_ad.isEnabled = false

        viInterstitialAd?.let { viAd ->
            load_ad.setOnClickListener {
                if (!viAd.isLoading && !viAd.isReady) {
                    viAd.loadAd()
                } else {
                    Toast.makeText(this@MainActivityKt, "Ad ready or currently loading", Toast.LENGTH_SHORT).show()
                }
            }

            start_ad.setOnClickListener {
                if (viAd.isReady) {
                    start_ad.isEnabled = false
                    viAd.startAd()
                } else {
                    Toast.makeText(this@MainActivityKt, "Ad is not prepared", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onAdEvent(viAdEvent: ViAdEvent) {
        Log.d(LOG_TAG, viAdEvent.type.toString())
        Toast.makeText(this@MainActivityKt, "Ad event: $viAdEvent", Toast.LENGTH_SHORT).show()

        when (viAdEvent.type) {
            ViAdEvent.ViAdEventType.AD_PAUSED -> { }
            ViAdEvent.ViAdEventType.AD_CLICKED -> { }
            ViAdEvent.ViAdEventType.AD_CLOSE -> { }
            ViAdEvent.ViAdEventType.AD_COMPLETED -> { }
            ViAdEvent.ViAdEventType.AD_LOADED -> {
                start_ad.isEnabled = true
            }
            ViAdEvent.ViAdEventType.AD_RESUMED -> { }
            ViAdEvent.ViAdEventType.AD_STARTED -> { }
            null -> { }
        }
    }

    override fun onAdError(viAdError: ViAdError) {
        Log.d(LOG_TAG, viAdError.type.toString())
        Toast.makeText(this@MainActivityKt, "Ad error: $viAdError", Toast.LENGTH_SHORT).show()

        when (viAdError.type) {
            ViAdError.ViAdErrorType.AD_PLAYBACK_ERROR -> { }
            ViAdError.ViAdErrorType.AD_LOADING_ERROR -> { }
            null -> { }
        }
    }

    companion object {
        val LOG_TAG = MainActivityKt::class.simpleName
    }
}
