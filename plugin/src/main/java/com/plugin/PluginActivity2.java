package com.plugin;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

/**
 * @author 张全
 */

public class PluginActivity2 extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_layout2);
        Log.e("HookUtil","PluginActivity2 onCreate compnentName="+getIntent().getComponent());
    }

    //-------使用Application中替换过的Resource，也可以定义接口，通过接口来获取Resource
    @Override
    public Resources getResources() {
        return getApplication().getResources();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("HookUtil","PluginActivity2 onResume compnentName="+getIntent().getComponent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("HookUtil","PluginActivity2 onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("HookUtil","PluginActivity2 onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("HookUtil","PluginActivity2 onDestroy");
    }
}
