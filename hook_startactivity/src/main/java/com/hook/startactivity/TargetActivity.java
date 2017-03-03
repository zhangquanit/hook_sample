package com.hook.startactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * @author 张全
 */

public class TargetActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target);
        Log.e("HookUtil","TargetActivity onCreate compnentName="+getIntent().getComponent());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("HookUtil","TargetActivity onResume compnentName="+getIntent().getComponent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("HookUtil","TargetActivity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("HookUtil","TargetActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("HookUtil","TargetActivity onDestroy");
    }
}
