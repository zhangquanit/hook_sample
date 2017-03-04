package com.hook.startservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
    Intent intent;
    ServiceConnection serviceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.startService).setOnClickListener(this);

        HookUtil.hookHandlerMessage();
    }

    public Intent getServiceIntent(){
        intent = new Intent(this, ProxyService.class);
        intent.putExtra("key","value");
        Bundle bundle = new Bundle();
        bundle.putInt("key2",3);
        intent.putExtra("bundleKey",bundle);
        return intent;
    }
    @Override
    public void onClick(View v) {
        Intent serviceIntent = getServiceIntent();
        switch (v.getId()) {
            case R.id.startService:
                startService(serviceIntent);
                break;
            case R.id.bindService:
                serviceConnection= new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        MyBinder myBinder= (MyBinder) service;
                        Log.e("HookUtil","--------------onServiceConnected");
                        myBinder.say("hello world");
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                        Log.e("HookUtil","--------------onServiceDisconnected");
                    }
                };
                bindService(serviceIntent,serviceConnection,Context.BIND_AUTO_CREATE);
                break;
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
       if(null!=serviceConnection) unbindService(serviceConnection);
    }

}
