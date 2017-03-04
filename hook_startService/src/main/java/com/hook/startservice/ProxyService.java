package com.hook.startservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author 张全
 */

public class ProxyService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("HookUtil","ProxyService  onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("HookUtil","ProxyService  onBind");
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("HookUtil","ProxyService  onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("HookUtil","ProxyService  onDestroy");
    }


}
