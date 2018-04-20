package com.isoftston.issuser.fusioncharge.weights;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.corelibs.views.NoScrollingListView;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.adapters.FeeDialogListAdapter;
import com.isoftston.issuser.fusioncharge.model.beans.ChargeFeeBean;

import java.util.List;

/**
 * Created by issuser on 2018/4/19.
 */

public class ChargeFeeDialog extends Dialog {

    private Context context;
    private NoScrollingListView listView;
    private ImageView iv_cancel;
    private FeeDialogListAdapter adapter;

    public ChargeFeeDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_charge_fee);

        listView=(NoScrollingListView)findViewById(R.id.lv);
        iv_cancel=findViewById(R.id.iv_cancel);

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        adapter=new FeeDialogListAdapter(context);
        listView.setAdapter(adapter);

    }

    public void setFeeDatas(List<ChargeFeeBean> list){
        adapter.replaceAll(list);
        adapter.notifyDataSetChanged();
    }
}
