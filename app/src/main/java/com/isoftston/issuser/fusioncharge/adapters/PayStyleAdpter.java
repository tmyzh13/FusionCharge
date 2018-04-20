package com.isoftston.issuser.fusioncharge.adapters;

import android.content.Context;
import android.widget.CheckBox;

import com.corelibs.utils.adapter.BaseAdapterHelper;
import com.corelibs.utils.adapter.normal.QuickAdapter;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.model.beans.PayStyleBean;

/**
 * Created by issuser on 2018/4/20.
 */

public class PayStyleAdpter extends QuickAdapter<PayStyleBean> {

    private int currentPosition=0;

    public PayStyleAdpter(Context context){
        super(context, R.layout.item_pay_style);
    }

    public void setCurrentPosition(int position){
        this.currentPosition=position;
        notifyDataSetChanged();
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    @Override
    protected void convert(BaseAdapterHelper helper, PayStyleBean item, int position) {
        helper.setImageResource(R.id.iv_pay,item.imgRes)
                .setText(R.id.tv_name,item.name)
                .setText(R.id.tv_hint,item.hint);
        CheckBox cb_select=helper.getView(R.id.cb_select);

        if(currentPosition==position){
            cb_select.setChecked(true);
        }else{
            cb_select.setChecked(false);
        }
    }
}
