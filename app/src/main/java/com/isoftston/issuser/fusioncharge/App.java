package com.isoftston.issuser.fusioncharge;

import android.app.Application;
import android.os.StrictMode;

import com.corelibs.api.ApiFactory;
import com.corelibs.common.Configuration;
import com.corelibs.utils.GalleryFinalConfigurator;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.isoftston.issuser.fusioncharge.constants.Urls;

/**
 * Created by issuser on 2018/4/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        ToastMgr.init(getApplicationContext());
        Configuration.enableLoggingNetworkParams();
        ApiFactory.getFactory().add(Urls.ROOT); //初始化Retrofit接口工厂
        PreferencesHelper.init(getApplicationContext());
        GalleryFinalConfigurator.config(getApplicationContext());

        //galleryfinal 7.0
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

}
