package com.isoftston.issuser.fusioncharge.weights;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.isoftston.issuser.fusioncharge.R;

/**
 * Created by issuser on 2018/4/20.
 */

public class CheckChargeFailDialog extends Dialog{

    private Context context;
    private ImageView iv_cancel;

    public CheckChargeFailDialog(@NonNull Context context) {
        super(context, R.style.MyDialog1);
        this.context=context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_charge_fail);
        iv_cancel=findViewById(R.id.iv_cancel);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCanceledOnTouchOutside(false);
    }

    public void setCancelOnClickListener(View.OnClickListener listener){
        iv_cancel.setOnClickListener(listener);
    }
}
