package com.wolearn.hooklistener;

import android.util.Log;
import android.view.View;

/**
 * Created by wulei
 * Data: 2016/10/17.
 */

public class OnClickListenerProxy implements View.OnClickListener{
    private View.OnClickListener realListener;
    private HookListenerContract.OnClickListener bizListener;

    public OnClickListenerProxy(View.OnClickListener realListener, HookListenerContract.OnClickListener bizListener){
        this.realListener = realListener;
        this.bizListener = bizListener;
    }

    @Override
    public void onClick(View v) {
        Log.e("OnClickListenerProxy", "---------------OnClickListenerProxy-------------");
        if(bizListener != null) bizListener.doInListener(v);
        if(realListener != null) realListener.onClick(v);
    }
}
