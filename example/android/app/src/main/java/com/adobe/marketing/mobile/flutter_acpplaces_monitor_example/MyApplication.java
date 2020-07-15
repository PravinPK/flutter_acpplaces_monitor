/*
Copyright 2020 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/

package com.adobe.marketing.mobile.flutter_acpplaces_monitor_example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Identity;
import com.adobe.marketing.mobile.InvalidInitException;
import com.adobe.marketing.mobile.Lifecycle;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.Places;
import com.adobe.marketing.mobile.PlacesMonitor;
import com.adobe.marketing.mobile.Signal;
import com.adobe.marketing.mobile.WrapperType;

import io.flutter.app.FlutterApplication;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MyApplication extends FlutterApplication implements PluginRegistry.PluginRegistrantCallback {

    @Override
    public void onCreate() {
        super.onCreate();

        MobileCore.setApplication(this);
        MobileCore.setLogLevel(LoggingMode.VERBOSE);
        MobileCore.setWrapperType(WrapperType.FLUTTER);
        
        try {
            Identity.registerExtension();
            Lifecycle.registerExtension();
            Signal.registerExtension();
            Places.registerExtension();
            PlacesMonitor.registerExtension();
            MobileCore.start(new AdobeCallback() {
                @Override
                public void call(Object o) {
                    MobileCore.configureWithAppID("launch-EN81be7cedb7f14132968641a6ec683adf");
                }
            });
        } catch (InvalidInitException e) {
            Log.e("MyApplication", String.format("Error while registering extensions %s", e.getLocalizedMessage()));
        }

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) { /*no-op*/ }

            @Override
            public void onActivityStarted(Activity activity) { /*no-op*/ }

            @Override
            public void onActivityResumed(Activity activity) {
                MobileCore.setApplication(MyApplication.this);
                MobileCore.lifecycleStart(null);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                MobileCore.lifecyclePause();
            }

            @Override
            public void onActivityStopped(Activity activity) { /*no-op*/ }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) { /*no-op*/ }

            @Override
            public void onActivityDestroyed(Activity activity) { /*no-op*/ }
        });
    }

    @Override
    public void registerWith(PluginRegistry pluginRegistry) {
        GeneratedPluginRegistrant.registerWith(pluginRegistry);
    }
}
