package com.hook.startactivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_startAct).setOnClickListener(this);
        findViewById(R.id.btn_startTarget).setOnClickListener(this);
        Toast.makeText(this,"heel",Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_startAct:
                Toast.makeText(this,"heel",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.btn_startTarget:
                Intent intent = new Intent();
//                intent.setComponent(new ComponentName(getPackageName(),"com.hook.startactivity.TargetActivity"));
                intent.setComponent(new ComponentName("com.plugin","com.plugin.PluginActivity")); //插件包
                startActivity(intent);
                break;
        }
    }
}
