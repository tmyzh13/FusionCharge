package com.isoftston.issuser.fusioncharge.model;


import com.corelibs.utils.PreferencesHelper;
import com.isoftston.issuser.fusioncharge.model.beans.UserBean;


public class UserHelper {
    public static void saveUser(UserBean user) {
        PreferencesHelper.saveData(user);
    }

    public static UserBean getSavedUser() {
        return PreferencesHelper.getData(UserBean.class);
    }

    public static long getUserId() {
        return getSavedUser().id;
    }
    public static void clearUserInfo(Class user){
        PreferencesHelper.remove(user);
    }





    public static  void clearInfo(String key){
        PreferencesHelper.remove(key);
    }
}
