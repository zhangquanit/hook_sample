package com.hook.startactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_startAct).setOnClickListener(this);
        findViewById(R.id.btn_startTarget).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startAct:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn_startTarget:
                Intent intent = new Intent(this, TargetActivity.class);
                startActivity(intent);
                break;
        }
    }
}
