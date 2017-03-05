package com.hook.startactivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.reflect.Field;

public class ActivityThreadHandlerCallback implements Handler.Callback {

        private Handler handler;

        public ActivityThreadHandlerCallback(Handler handler) {
            this.handler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            Log.i("HookUtil", "handleMessage， msg="+msg);
            //替换之前的Intent
            if (msg.what ==100) {
                Log.i("HookUtil","lauchActivity");
                handleLauchActivity(msg);
            }

            handler.handleMessage(msg);
            return true;
        }

        private void handleLauchActivity(Message msg) {
            Object obj = msg.obj;//ActivityClientRecord
            try{
                 //拿到开启ProxyActivity的Intent
                Field intentField = obj.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent proxyInent = (Intent) intentField.get(obj);
                 //拿到实际要开启的Activity的Intent
                Intent realIntent = proxyInent.getParcelableExtra("oldIntent");
                if (realIntent != null) {
                    proxyInent.setComponent(realIntent.getComponent());//替换要开启的类名
                }
            }catch (Exception e){
                Log.i("HookUtil","lauchActivity falied");
            }

        }
    }