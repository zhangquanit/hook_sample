package com.plugin2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * @author 张全
 */

public class PluginActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_layout);
        findViewById(R.id.btn_pluginActivity2).setOnClickListener(this);
        findViewById(R.id.btn_setResult).setOnClickListener(this);

        Log.e("HookUtil","PluginActivity onCreate compnentName="+getIntent().getComponent());
    }

    //-------使用Application中替换过的Resource，也可以定义接口，通过接口来获取Resource
    @Override
    public Resources getResources() {
        return getApplication().getResources();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("HookUtil","PluginActivity onResume compnentName="+getIntent().getComponent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("HookUtil","PluginActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("HookUtil","PluginActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("HookUtil","PluginActivity onDestroy");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_pluginActivity2:
                startActivity(new Intent(this,PluginActivity2.class));
                break;
            case R.id.btn_setResult:
                Intent result = new Intent();
                result.putExtra("key","value");
                setResult(1,result);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
