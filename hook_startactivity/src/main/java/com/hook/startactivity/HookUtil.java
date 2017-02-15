package com.hook.startactivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HookUtil {

    private Class<?> proxyActivity;

    private Context context;

    public HookUtil(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    /**
     * hook  startActivity
     */
    public void hookAms() {

        //一路反射，直到拿到IActivityManager的对象
        try {
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            //   private static final Singleton<IActivityManager> gDefault = new Singleton<IActivityManager>()
            Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault"); //gDefault静态字段
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);
            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue); //拿到实际的ActivityManager对象


            //开始动态代理，用代理对象替换掉真实的ActivityManager，然后在代理方法中实现自己的操作
            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");
            AmsInvocationHandler handler = new AmsInvocationHandler(iActivityManagerObject);
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{IActivityManagerIntercept}, handler);

            //现在替换掉这个对象
            mInstance.set(defaultValue, proxy);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 为了不启动ProxyActivity，现在我们需要找一个合适的时机，把真实的Intent换过了来，启动我们真正想启动的Activity。
     * Activity的启动流程是由Handler发送消息来实现的。
        Handler处理消息的代码:
         public void dispatchMessage(Message msg) {
             if (msg.callback != null) {
                   handleCallback(msg);
             } else {
                  //优先检查mCallback，然后才是handleMessage,因此可以注入一个Callback对象，优先拦截处理
                 if (mCallback != null) {
                     if (mCallback.handleMessage(msg)) {
                         return;
                     }
                 }
                 handleMessage(msg);
             }
         }
     * <pre class="prettyprint">
     * public final class ActivityThread {
     *  final H mH = new H();
     *  public static ActivityThread currentActivityThread(){
     *       return sCurrentActivityThread;
     *   }
     *  private class H extends Handler {
     *     public static final int LAUNCH_ACTIVITY= 100;
     *     public void handleMessage(Message msg) {
     *        switch (msg.what) {
                 case LAUNCH_ACTIVITY: //开启Activity
                           handleLaunchActivity(r, null, "LAUNCH_ACTIVITY");
                  break;
     *     }
     *  }
     * }
     * </pre>
     */
    public void hookSystemHandler() {
        try {
           //ActivityThread.currentActivityThread();
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            //获取主线程对象
            Object activityThread = currentActivityThreadMethod.invoke(null);
            //获取mH字段
            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            //获取Handler
            Handler handler = (Handler) mH.get(activityThread);


            //获取原始的mCallBack字段
            Field mCallBack = Handler.class.getDeclaredField("mCallback");
            mCallBack.setAccessible(true);
            //这里设置了我们自己实现了接口的CallBack对象，在handleMessage前优先处理消息
            mCallBack.set(handler, new ActivityThreadHandlerCallback(handler)) ;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AmsInvocationHandler implements InvocationHandler {

        private Object iActivityManagerObject;

        private AmsInvocationHandler(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Log.i("HookUtil", method.getName());
            //我要在这里搞点事情
            if ("startActivity".contains(method.getName())) {
                Log.e("HookUtil","Activity已经开始启动");
                Log.e("HookUtil","小弟到此一游！！！");

                //换掉
                Intent intent = null;
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof Intent) {
                        //说明找到了startActivity的Intent参数
                        intent = (Intent) args[i];
                        //这个意图是不能被启动的，因为Acitivity没有在清单文件中注册
                        index = i;
                    }
                }

                //伪造一个代理的Intent，代理Intent启动的是proxyActivity
                Intent proxyIntent = new Intent();
                ComponentName componentName = new ComponentName(context, proxyActivity);
                proxyIntent.setComponent(componentName);
                proxyIntent.putExtra("oldIntent", intent);
                args[index] = proxyIntent;
            }
            return method.invoke(iActivityManagerObject, args); //调用真实对象
        }
    }


}