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
 * Created by issuser on 2018/4/23.
 */

public class AppointmentTimeOutDialog extends Dialog {

    private ImageView iv_delete;
    private TextView tv_re_appointment;

    public AppointmentTimeOutDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_appointment_time_out);

        iv_delete=findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_re_appointment=findViewById(R.id.tv_re_appointment);
    }

    public void setReAppointment(View.OnClickListener listener){
        tv_re_appointment.setOnClickListener(listener);
    }

    public void setIvDeleteListener(View.OnClickListener listener){
        iv_delete.setOnClickListener(listener);
    }

}
