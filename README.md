[![GitHub release](https://img.shields.io/github/release/vi-ai/vi-android-sdk.svg)](https://github.com/vi-ai/vi-android-sdk/releases)

# video intelligence Android SDK integration documentation

1. [SDK Integration](https://github.com/vi-ai/vi-android-sdk#step-1--include-the-vimobileadssdk-library-in-your-application)
2. [Instream Ads](https://github.com/vi-ai/vi-android-sdk#requesting-ads-how-to-serve-in-stream-ads-using-the-vi-sdk)
3. [Interstitial Ads](https://github.com/vi-ai/vi-android-sdk#requesting-ads-how-to-serve-interstitial-ads-using-the-vi-sdk)
4. [Ads Mediation](https://github.com/vi-ai/vi-android-sdk#client-side-mediation-how-to-enable-ad-mediation-support)
5. [External Trackers](https://github.com/vi-ai/vi-android-sdk#external-tracker-how-add-external-trackers-for-viadssdk)

## Step 1 | Include the ViMobileAdsSDK library in your application.

To add ViMobileAdsSDK to your application, is required to add our maven repository link and dependency.

```groovy
repositories {
    maven { 
        url 'https://maven.vi.ai/repository/mobileads-public/' 
    }
}

dependencies {
    compile('ai.vi.mobileads:mobileads:x.y.z') {
        transitive true
    }
}
```

*please replace x.y.z to current SDK version*

Finally, our SDK has a required dependency on Google Play Services Ads, so please make sure to include it in your app’s list of dependencies.

```groovy
dependencies {
    compile 'com.google.android.gms:play-services-ads:10.2.0'
}
```

Our SDK requires the INTERNET permission, and the (optional) location services permission.
Add this permissions to AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/><!--Optional-->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/><!--Optional-->
```
___
## Step 2 | Initialize ViMobileAdsSdk

Initialize our SDK as early as possible by calling the init method on the singleton instance and passing in a
reference to the Application object, where mApp is a reference to the Application:
```java
import ai.vi.mobileads.api.ViSdk;
 
ViSdk.init(this);
```

Ideally you should call start within the onCreate method of your app’s Application
subclass. For example:

```java
import android.app.Application;
import ai.vi.mobileads.api.ViSdk;
 
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ViSdk.init(this);
    }
}
```

For development you can enable debug mode by adding ```ViOptions``` to ```ViSdk.init()``` method: 

```java
import android.app.Application;
import ai.vi.mobileads.api.ViSdk;
import ai.vi.mobileads.api.ViOptions;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        ViOptions viOptions = new ViOptions();
        viOptions.setDebuggable(true);
        
        ViSdk.init(this, viOptions);
    }
}
```

If you are an SDK rather than app developer, you should initialize us as soon as you are able to access the
Application object.

If you will try to get ViSdk singleton instance (ViSdk.getInstance()) before call init SDK wil throw IllegalStateException("SDK currently not initialized");

___
# Requesting Ads: How to serve In-Stream Ads using the vi SDK

## Step 1 | Add ViAdView into your UI

Put ViAdView into your layout 
```xml
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ai.vi.mobileads.api.ViAdView
        android:id="@+id/adView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        />

</RelativeLayout>
```
or create it by self and add into current layout
___
## Step 2 | Initialize viVideoAd and load an ad
Create ViAdPlacement with the placement id from console
```java
import ai.vi.mobileads.api.ViAdPlacement;
 
ViAdPlacement placement = ViAdPlacement.create("Your placement id from console");
```
Initialize the ViAdManager with the placement code from console

```java
import ai.vi.mobileads.api.ViVideoAd;
 
ViVideoAd videoAdManager = ViSdk.getInstance().createVideoAd(placement, adView);
```
If you want to get notification about AD workflow you should create ```ViVideoAd``` with Callback

```java
import ai.vi.mobileads.api.ViVideoAd;
 
ViVideoAd viVideoAd = ViSdk.getInstance().createVideoAd(placement, viAdView, new ViAdCallback() {
            @Override
            public void onAdEvent(ViAdEvent viAdEvent) {
                switch (viAdEvent.type){
                    case AD_PAUSED:{
                        //notifies that ad is paused
                        break;
                    }
                    case AD_CLICKED: {
                        //notifies that user click into curent ad
                        break;
                    }
                    case AD_CLOSE: {
                        //notifies that ad is closed
                        break;
                    }
                    case AD_COMPLETED:{
                        //notifies that ad playback completed
                        break;
                    }
                    case AD_LOADED:{
                        //notifies that ad loaded and ready for play
                        //you can start ad playback by videoAdManager.startAd()
                        break;
                    }
                    case AD_RESUMED:{
                        //notifies that ad is resumed
                        break;
                    }
                    case AD_STARTED:{
                        //notifies that ad playback started
                    }
                }
            }

            @Override
            public void onAdError(ViAdError viAdError) {
                switch (viAdError.type){
                    case AD_PLAYBACK_ERROR:{
                        //notifies about ad playback error
                        break;
                    }
                    case AD_LOADING_ERROR:{
                        //notifies about ad loading error
                    }
                }
            }
        });
```

Load ad
```java
viVideoAd.loadAd();
```
Load ad with **auto start** playback when ad is ready
```java
viVideoAd.loadAd(true);
```
___
## Step 3 | Ad playback control

Pause current ad playback
```java
viVideoAd.pause();
```
Resume current ad playback
```java
viVideoAd.resume();
```
Close current ad
```java
viVideoAd.closeAd();
```
___
# Requesting Ads: How to serve Interstitial Ads using the vi SDK

## Step 1 | Add the Interstitial activity to your manifest

```xml
<activity android:name="ai.vi.mobileads.api.ViAdActivity" android:configChanges="orientationlscreenSizelkeyboardHidden" android:windowSoftinputMode="stateHidden"/>
```

## Step 2 | Initialize ViInterstitialAd and load the ad

```java
ViinterstitalAd interstitialAd = ViSdk.getinstance().createinterstitialAd(placement, new ViAdCallback() {
    @Override    
    public void onAdEvent(ViAdEvent event) {
        //All add events arrive here
    }
    @Override    
    public void onAdError(ViAdError error){
  
    }
  });
interstitialAd.loadAd();
```

Note that for Interstitial ads, we cannot load the ad and automatically begin its playback. Rather, you control when the ad starts playing.

When the ad has finished loading, you will receive the ```AD_LOADED``` event in the onAdEvent method of ViAdCallback. Note that you will not be able to trigger ```startAd()``` before the ad has finished loading. This is because Interstitial ads are cached before the ```AD_LOADED``` event is triggered, guaranteeing that the ad will start immediately after the Interstitial Activity is called.

## Step 3 | Display the ad

```java
interstitialAd.startAd();
```

You can also trigger the ad immediately after it completes loading by triggering it in the callback

````java
@Override
public void onAdEvent(ViAdEvent event) {
   if(event.type = ViAdEvent.ViAdEventType.AD_LOADED){
      if(interstitialAd =! null) {
        //You'll get notified that the ad is loaded and ready
        interstitialAd.startAd();
      }
   }
}
````

## Step 4 | Controlling Ad state

You can check if the ad has been downloaded and is ready for playback.

````java
interstitialAd.isReady();
````

Also keep in mid that all Interstitial Ads are cached. Please call ```closeAd()``` onDestroy of the activity, where ```loadAd()``` was called.

````java
interstitialAd.closeAd();
````
___
# Client Side Mediation: How to enable Ad mediation support

ViMobileAds SDK supports three ad providers:
1. AdMob
2. MoPub
3. Facebook

## Step 1 | Add adapter dependency

In order to enable client side ad mediation, we have provided an adapter for each individual ad provider. Should you wish to mediate a specific ad provider, all you would need to do is add the required adapter as a dependency to your project.

```groovy
dependencies {

    //AdMob mediation adapter
    compile('ai.vi.mobileads:admob-adapter:x.y.z') {
        transitive true
    }
        
    //Facebook mediation adapter
    compile('ai.vi.mobileads:facebook-adapter:x.y.z') {
        transitive true
    }
    
    //MoPub mediation adapter
    compile('ai.vi.mobileads:mopub-adapter:x.y.z') {
        transitive true
    }
}
```

*please replace x.y.z to current SDK version (adapters are supported from version 2.0.0 and above)*

## Step 2 | Register mediation adapter in ViAd

Mediation adapters should be added to Vi Ads where you want mediation to be present. Here is simple facebook example :

```java
ViAdPlacement viAdPlacement = ViAdPlacement.create("Vi Ad placementId");
ViIntersititalAd viInterstitialAd = ViSdk.getInstance().createInterstitialAd(viAdPlacement, this);
ViIntersititalAd viInterstitialAd.registerMediatedAdapter(new FacebookViInterstitialMediationAdapter(this, "Facebook placement ID"));
```

That’s it. When there is no available vi Direct demand, the Facebook ad will be sourced and displayed in your application

___
# External tracker: How add external trackers for ViAdsSDK

ViAdSDK provides ability to add global trackers on all events that happens within the SDK. This tracking should be registered once after SDK initialization.
This can be achieved with implementation of ``` ViExternalAdTracker ``` interface.

```java
ViSdk.getInstance().registerExternalAdapter(new ViExternalAdTracker() {
        @Override
        public void adStart(String placementId) {
             
        }
 
        @Override
        public void adComplete(String placementId) {
 
        } 
 
        @Override
        public void adPaused(String placementId) {
 
        }
 
        @Override
        public void adResumed(String placementId) {
 
        }
 
        @Override
        public void adSkip(String placementId) {
 
        }
 
        @Override
        public void adError(String placementId) {
  
        }
 
        @Override
        public void adClicked(String placementId) {
 
        }
 
        @Override
        public void adVolumeChanged(String placementId, boolean isMuted) {
 
        }
    });

```

For all events, the placementID determines on which placement the event occurred.
Apart from custom events, the vi SDK also includes a pre-built adapter for ```count.ly``` analytics.


## Step 1 | Add count.ly adapter as dependency
```groovy
compile('ai.vi.mobileads:countly-adapter:x.y.z') {
    transitive true
}
```
*please replace x.y.z to current SDK version (adapters are supported from version 2.0.0 and above)*


## Step 2 | Register count.ly adapter

```java
import ai.vi.mobileads.adapter.countly.ViMobileAdsCountlyTracker;
 
ViSdk.getInstance().registerExternalAdapter(new ViMobileAdsCountlyTracker());
```

# Documentation
Java API docs of can be found [here](https://cdn.rawgit.com/vi-ai/vi-android-sdk/8c06355/libs/sdk/2.0.1/docs/index.html)
