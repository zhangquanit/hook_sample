package com.hook.startservice;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * @author 张全
 */

public class TargetService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("HookUtil","TargetService  onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("HookUtil","TargetService  onBind");
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("HookUtil","TargetService  onStartCommand ,intent="+intent);
        if(null!=intent){
            String value = intent.getStringExtra("key");
            Bundle bundleValue = intent.getBundleExtra("bundleKey");
            Log.e("HookUtil","TargetService  onStartCommand ,value="+value+",bundleValue="+bundleValue.getInt("key2"));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("HookUtil","TargetService  onDestroy");
    }
}
