package com.plugin.sdk;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

/**
 * @author 张全
 */

public class PluginBaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
    }

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }
}
