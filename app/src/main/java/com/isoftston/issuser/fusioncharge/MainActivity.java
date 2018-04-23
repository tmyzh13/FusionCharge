package com.isoftston.issuser.fusioncharge;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.fusioncharge.constants.Constant;
import com.isoftston.issuser.fusioncharge.utils.ChoiceManager;
import com.isoftston.issuser.fusioncharge.utils.SharePrefsUtils;
import com.isoftston.issuser.fusioncharge.utils.Tools;
import com.isoftston.issuser.fusioncharge.views.LoginActivity;
import com.isoftston.issuser.fusioncharge.views.home.HomeListFragment;
import com.isoftston.issuser.fusioncharge.views.home.MapFragment;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.main_left_drawer_layout)
    LinearLayout main_left_drawer_layout;
    @Bind(R.id.main_right_drawer_layout)
    LinearLayout main_right_drawer_layout;
    @Bind(R.id.view_main_statue)
    View view_main_statue;
    @Bind(R.id.view_statue)
    View view_statue;
    @Bind(R.id.tv_map)
    TextView tv_map;
    @Bind(R.id.tv_list)
    TextView tv_list;
    @Bind(R.id.iv_user_icon)
    ImageView iv_user_icon;
    @Bind(R.id.cb_charge_direct)
    CheckBox cb_charge_direct;
    @Bind(R.id.cb_charge_alternating)
    CheckBox cb_charge_alternating;
    @Bind(R.id.cb_free)
    CheckBox cb_free;
    @Bind(R.id.cb_busy)
    CheckBox cb_busy;
    @Bind(R.id.et_distance)
    EditText et_distance;

    private Context context = MainActivity.this;

    private MapFragment mapFragment;
    private HomeListFragment homeListFragment;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        ViewGroup.LayoutParams lp = view_main_statue.getLayoutParams();
        lp.height = Tools.getStatueHeight(context);
        view_main_statue.setLayoutParams(lp);
        view_statue.setLayoutParams(lp);
        view_main_statue.setBackgroundResource(R.drawable.nan_bg);
        view_statue.setBackgroundColor(context.getResources().getColor(R.color.transparent_black));

        mapFragment = new MapFragment();
        homeListFragment = new HomeListFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content, mapFragment).commit();

        Glide.with(context).load("http://imgsrc.baidu.com/baike/pic/item/bd7faf355e43afc1a71e1220.jpg")
                .override(320, 320).into(iv_user_icon);
    }

    @OnClick(R.id.iv_user)
    public void openLeft() {
        if (drawerLayout.isDrawerOpen(main_right_drawer_layout)) {
            drawerLayout.closeDrawer(main_right_drawer_layout);
        }
        if (TextUtils.isEmpty(PreferencesHelper.getData(Constant.LOGIN_STATUE))) {
            startActivity(LoginActivity.getLauncher(context));
        } else {
            drawerLayout.openDrawer(main_left_drawer_layout);
        }

    }

    @OnClick(R.id.iv_choice)
    public void openRight() {
        if (drawerLayout.isDrawerOpen(main_left_drawer_layout)) {
            drawerLayout.closeDrawer(main_left_drawer_layout);
        }
        drawerLayout.openDrawer(main_right_drawer_layout);
    }

    @OnClick(R.id.tv_map)
    public void choiceMap() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        tv_map.setTextColor(getResources().getColor(R.color.app_blue));
        tv_list.setTextColor(getResources().getColor(R.color.white));
        ViewGroup.LayoutParams lp = tv_map.getLayoutParams();
        lp.width = Tools.dip2px(context, 75);
        tv_map.setLayoutParams(lp);
        ViewGroup.LayoutParams lp1 = tv_list.getLayoutParams();
        lp1.width = Tools.dip2px(context, 65);
        tv_list.setLayoutParams(lp1);
        tv_map.setBackgroundResource(R.drawable.tv_corner_white);
        tv_list.setBackgroundColor(getResources().getColor(R.color.transparent));

        ft.hide(homeListFragment);
        if (!mapFragment.isAdded()) {
            ft.add(R.id.content, mapFragment).show(mapFragment);
        } else {
            ft.show(mapFragment);
        }
        ft.commit();

    }

    @OnClick(R.id.tv_list)
    public void choiceList() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        tv_map.setTextColor(getResources().getColor(R.color.white));
        tv_list.setTextColor(getResources().getColor(R.color.app_blue));
        ViewGroup.LayoutParams lp = tv_map.getLayoutParams();
        lp.width = Tools.dip2px(context, 65);
        tv_map.setLayoutParams(lp);
        ViewGroup.LayoutParams lp1 = tv_list.getLayoutParams();
        lp1.width = Tools.dip2px(context, 75);
        tv_list.setLayoutParams(lp1);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        tv_list.setBackgroundResource(R.drawable.tv_corner_white);
        tv_map.setBackgroundColor(getResources().getColor(R.color.transparent));
//            }
//        },500);


        ft.hide(mapFragment);
        if (!homeListFragment.isAdded()) {
            ft.add(R.id.content, homeListFragment).show(homeListFragment);
        } else {
            ft.show(homeListFragment);
        }
        ft.commit();
    }

    @OnClick(R.id.tv_confirm)
    public void choiceCondition() {
        if (TextUtils.isEmpty(et_distance.getText().toString().trim())) {
            ChoiceManager.getInstance().setDistance("");
        } else {
            ChoiceManager.getInstance().setDistance(et_distance.getText().toString());
        }
        String type = "";
        String statue = "";
        if (cb_charge_direct.isChecked()) {
            type += "0";
        }
        if (cb_charge_alternating.isChecked()) {
            type += "1";
        }
        if (cb_free.isChecked()) {
            statue += "0";
        }
        if (cb_busy.isChecked()) {
            statue += "1";
        }
        ChoiceManager.getInstance().setStatue(statue);
        ChoiceManager.getInstance().setType(type);
    }

    @OnClick(R.id.tv_reset)
    public void resetCondition() {
        cb_busy.setChecked(false);
        cb_free.setChecked(false);
        cb_charge_direct.setChecked(false);
        cb_charge_alternating.setChecked(false);
        et_distance.setText("");
        ChoiceManager.getInstance().resetChoice();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 物理返回键拦截
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.e("yzh", "11111111111");
            doublePressBackToast();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * 双击两次返回键退出应用
     */
    private boolean isBackPressed = false;//判断是否已经点击过一次回退键

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            showToast(getString(R.string.double_press_for_exit));
        } else {
            AppManager.getAppManager().finishAllActivity();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }
}
