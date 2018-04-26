package com.isoftston.issuser.fusioncharge.model.beans;

/**
 * Created by yizh on 2016/3/21.
 */
public class UserBean {

    public long id;
    public long appUserId;
    public String photoUrl;
    public String name;
    public String phone;
    public String nickName;
    public int sex;
    public String sexName;
    public String email;
    public String token;
    public String type;
    /**
     * 用户名
     */
    public String userName;

    /**
     * 真实姓名
     */
    public String realName;

    /**
     * 用户电话号码
     */
    private String accountId;
    /**
     * 用户id
     */
    public String userId;
    /**
     * 用户昵称
     */
    public String userNickname;
    /**
     * 头像图片路径
     */
    public String avatar;
    /**
     * 性别 1为男，2为女，0为未知
     */
    public String gender = "0";




}
