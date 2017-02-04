package com.wolearn.hooklistener;

import android.util.Log;
import android.view.View;

/**
 * Created by wulei
 * Data: 2016/10/17.
 */

public class OnFocusChangeListenerProxy implements View.OnFocusChangeListener{
    private View.OnFocusChangeListener realListener;
    private HookListenerContract.OnFocusChangeListener bizListener;

    public OnFocusChangeListenerProxy(View.OnFocusChangeListener realListener, HookListenerContract.OnFocusChangeListener bizListener){
        this.realListener = realListener;
        this.bizListener = bizListener;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.e("OnFocusChangeProxy", "---------------OnFocusChangeListenerProxy-------------");
        if(bizListener != null) bizListener.doInListener(v, hasFocus);
        if(realListener != null) realListener.onFocusChange(v, hasFocus);
    }
}
