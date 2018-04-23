package com.isoftston.issuser.fusioncharge.weights;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.fusioncharge.R;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.utils.SharePrefsUtils;
import com.isoftston.issuser.fusioncharge.views.LoginActivity;

/**
 * Created by issuser on 2018/4/23.
 */

public class CommenLogic {
    public static Context context;

    public CommenLogic(Context context) {
        context = context;
    }

    public static boolean isLogin() {
        if (TextUtils.isEmpty(PreferencesHelper.getData(Constant.LOGIN_STATUE))) {
            context.startActivity(LoginActivity.getLauncher(context));
            return false;
        } else {
            if (true) {//是否有正在处理的订单
                //【检测等待页】或【正在充电页面】
            } else if (true) {//有待支付订单
                CommonDialog dialog = new CommonDialog(context, context.getString(R.string.hint), 1);
                dialog.show();
                dialog.setPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //去支付
                    }
                });
            } else {
                if (true) {//当前有预约
                    CommonDialog dialog = new CommonDialog(context, context.getString(R.string.now_have_appointed), 1);
                    dialog.show();
                    return false;
                } else {
                    return true;
                }
            }
            return true;
        }
    }
}
