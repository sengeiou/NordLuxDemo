/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.test.nordluxdemo.ble.mesh;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.airoha.btdlib.core.AirohaLink;
import com.airoha.libfota.core.AirohaOtaMgr;
import com.airoha.libmesh.core.AirohaMeshMgr;
import com.test.nordluxdemo.R;

/**
 * BluetootLeService holds the reference of {@link AirohaLink}.
 * SDK users should customize their own design for the Service.
 * The reference is a bounded Service so that Activity can call manipulate the functions directly.
 * @author Daniel.Lee
 * @since 2017/01/16
 * @version 1.0.1.0
 */
public class BluetoothLeService extends Service {
    private final static String TAG = "Airoha_" + BluetoothLeService.class.getSimpleName();

    private AirohaLink mAirohaLink;
    private AirohaMeshMgr mAirohaMeshMgr;
    private AirohaOtaMgr mAirohaOtaMgr;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
        super.onCreate();
        //setForeground(getResources().getString(R.string.app_name));
        mAirohaLink = new AirohaLink(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mAirohaMeshMgr = null;
        mAirohaLink = null;
        super.onDestroy();
    }

    public void setHandler(Handler handler){

    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    public boolean connect(final String address) {
        return mAirohaLink.connect(address);
    }

    public void disconnect() {
        mAirohaLink.disconnect();
    }

    private void close() {
        mAirohaLink.close();
    }




    public AirohaLink getAirohaLink(){
        return mAirohaLink;
    }

    public AirohaMeshMgr getAirohaMeshMgr(){
        if (mAirohaMeshMgr == null) {
            mAirohaMeshMgr = new AirohaMeshMgr(mAirohaLink);
        }

        return mAirohaMeshMgr;
    }

    public AirohaOtaMgr getAirohaOtaMgr() {
        if(mAirohaOtaMgr == null) {
            mAirohaOtaMgr = new AirohaOtaMgr(mAirohaLink);
        }

        return mAirohaOtaMgr;
    }
}
