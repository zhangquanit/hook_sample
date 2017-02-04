package com.wolearn.hooklistener;

import android.util.Log;
import android.view.View;

/**
 * Created by wulei
 * Data: 2016/10/17.
 */

public class OnLongClickListenerProxy implements View.OnLongClickListener{
    private View.OnLongClickListener realListener;
    private HookListenerContract.OnLongClickListener bizListener;

    public OnLongClickListenerProxy(View.OnLongClickListener realListener, HookListenerContract.OnLongClickListener bizListener){
        this.realListener = realListener;
        this.bizListener = bizListener;
    }

    @Override
    public boolean onLongClick(View v) {
        Log.e("OnLongClickProxy", "-------------OnLongClickListenerProxy-----------");
        if(bizListener != null) bizListener.doInListener(v);
        if(realListener != null) return realListener.onLongClick(v);
        return false;
    }
}
