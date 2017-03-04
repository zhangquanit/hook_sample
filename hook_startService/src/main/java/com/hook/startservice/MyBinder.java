package com.hook.startservice;

import android.os.Binder;
import android.util.Log;

public class MyBinder extends Binder {
        public void say(String str){
            Log.e("HookUtil","ProxyService  MyBinder ,say="+str);
        }
    }