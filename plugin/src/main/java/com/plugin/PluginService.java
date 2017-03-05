package com.plugin;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * @author 张全
 */

public class PluginService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("HookUtil","PluginService  onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("HookUtil","PluginService  onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("HookUtil","PluginService  onStartCommand ,intent="+intent);
        if(null!=intent){
            String value = intent.getStringExtra("key");
            Bundle bundleValue = intent.getBundleExtra("bundleKey");
            Log.e("HookUtil","PluginService  onStartCommand ,value="+value+",bundleValue="+bundleValue.getInt("key2"));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("HookUtil","PluginService  onDestroy");
    }
}
