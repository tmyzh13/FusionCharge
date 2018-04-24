package com.isoftston.issuser.fusioncharge.weights;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.isoftston.issuser.fusioncharge.R;

/**
 * Created by issuser on 2018/4/24.
 */

public class NearTargetDialog extends Dialog{
    public NearTargetDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout);
    }
}
