package com.isoftston.issuser.fusioncharge.weights;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.isoftston.issuser.fusioncharge.R;

/**
 * Created by issuser on 2018/4/20.
 */

public class PayFailDialog extends Dialog {

    private ImageView iv_cancel;
    private TextView tv_try_again;
    private Context context;

    public PayFailDialog(@NonNull Context context) {
        super(context, R.style.MyDialog1);
        this.context=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_fail);

        iv_cancel=findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_try_again=findViewById(R.id.tv_try_again);
        tv_try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setCanceledOnTouchOutside(false);
    }

    public void setTryAgainListener(View.OnClickListener listener){
        tv_try_again.setOnClickListener(listener);
    }
}
