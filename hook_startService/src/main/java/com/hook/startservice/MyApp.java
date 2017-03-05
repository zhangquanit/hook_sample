package com.hook.startservice;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张全
 */

public class MyApp extends Application {
    List<String> dexPathList;

    {
        dexPathList = new ArrayList<>();
        dexPathList.add(new File(Environment.getExternalStorageDirectory(), "plugin-debug.apk").getAbsolutePath());
        dexPathList.add(new File(Environment.getExternalStorageDirectory(), "plugin2-debug.apk").getAbsolutePath());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            loadPluginResources();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.instant_run.demo-2/base.apk"],nativeLibraryDirectories=[/vendor/lib64, /system/lib64]]]
         com.android.tools.fd.runtime.MyIncrementalClassLoader@352d6250
         java.lang.BootClassLoader@12276c49
         */
        ClassLoader classLoader = getClassLoader();
        while (classLoader != null) {
            System.out.println(classLoader);
            classLoader = classLoader.getParent();
        }
        /**
         * 当前PathClassLoader委托IncrementalClassLoader加载dex
         */
    }

    @Override
    protected void attachBaseContext(Context base) {
        setUpClassLoader(base);
        super.attachBaseContext(base);
    }

    public void setUpClassLoader(Context context) {
        ClassLoader classLoader = MyApp.class.getClassLoader();
        String nativeLibraryPath = null;
        try {
            nativeLibraryPath = (String) classLoader.getClass().getMethod("getLdLibraryPath", new Class[0]).invoke(classLoader, new Object[0]);
        } catch (Throwable t) {
            Log.e("InstantRun", "Failed to determine native library path " + t.getMessage());
            nativeLibraryPath = new File("/data/data/" + getPackageName(), "lib").getPath();
        }

        StringBuilder pathBuilder = new StringBuilder();
        for (int i = 0; i < dexPathList.size(); i++) {
            if (i > 0) {
                pathBuilder.append(File.pathSeparator);
            }
            pathBuilder.append(dexPathList.get(i));
        }
        String mDexPath=pathBuilder.toString();

        // 无法直接从外部路径加载.dex文件，需要指定APP内部路径作为缓存目录（.dex文件会被解压到此目录）
        String codeCacheDir = context.getDir("dexouput",Context.MODE_PRIVATE).getAbsolutePath();
        MyIncrementalClassLoader.inject(classLoader, nativeLibraryPath, codeCacheDir, mDexPath);
    }

    //--------------------------------------------------------------------------------
    private Resources.Theme mTheme;
    private Resources mResources;

    @Override
    public AssetManager getAssets() {
        return mResources == null ? super.getAssets() : mResources.getAssets();
    }

    @Override
    public Resources getResources() {
        return mResources == null ? super.getResources() : mResources;
    }

    @Override
    public Resources.Theme getTheme() {
        return mTheme == null ? super.getTheme() : mTheme;
    }


    /**
     * 加载插件资源
     */
    public void loadPluginResources() throws Exception {
        try {

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, dexPathList.get(1));

            Resources superRes = super.getResources();
            mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
            mTheme = mResources.newTheme();
            mTheme.setTo(super.getTheme());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HookUtil", "##loadPluginResources出错");
            throw e;
        }
    }

}
