package com.company.app.view.alert;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.company.app.R;


/**
 * Created by Jango on 2015/6/5.
 */
public class BaseAlertDialog extends Dialog {

    Context mContext = null;
    int layoutId = 0;


    public BaseAlertDialog(Context context, int layoutId) {
        super(context, R.style.Theme_Base_Dialog);
        this.layoutId = layoutId;
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(layoutId);
    }


}
