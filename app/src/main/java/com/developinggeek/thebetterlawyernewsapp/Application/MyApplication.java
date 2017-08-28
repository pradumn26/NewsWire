package com.developinggeek.thebetterlawyernewsapp.Application;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by Pradumn on 19-Aug-17.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
