package com.hook.startservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
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
        findViewById(R.id.bindService).setOnClickListener(this);
        findViewById(R.id.startActivity).setOnClickListener(this);

        HookUtil.hookHandlerMessage();

        System.out.println("resource="+getResources());
        String appName = getResources().getString(R.string.app_name);
        System.out.println("appName="+appName);

        Resources appResource = getApplication().getResources();
        System.out.println("app resource="+appResource);
        String appName2 = appResource.getString(R.string.app_name);
        System.out.println("appName2="+appName2);

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
            case R.id.startActivity:
                startActivity(new Intent(this,ProxyActivity.class));
                overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);
                break;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("HookUtil","onActivityResult--------------requestCode="+requestCode+",resultCode="+resultCode+",data="+data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
