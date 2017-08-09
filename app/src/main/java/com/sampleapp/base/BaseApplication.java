package com.sampleapp.base;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.sampleapp.BuildConfig;
import com.sampleapp.R;
import com.sampleapp.receivers.ConnectivityReceiver;
import com.sampleapp.utils.UtilsModule;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


/**
 * Initialization of required libraries
 */
public class BaseApplication extends Application {

    private AppComponent mAppComponent;
    private static BaseApplication context;

    public BaseApplication() {
        context = this;
    }
    public static synchronized BaseApplication getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        //Fabric initialization for Crashlytics
        Fabric.with(this, new Crashlytics());

        //create component
        mAppComponent = DaggerAppComponent.builder()
                .utilsModule(new UtilsModule(this)).build();

        //configure timber for logging
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        //calligraphy for fonts
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        //realm init
        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}
