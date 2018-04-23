package com.isoftston.issuser.fusioncharge.model.beans;

import java.io.Serializable;

/**
 * Created by issuser on 2018/4/23.
 */

public class CommentsBean implements Serializable{

    public String name;
    public String time;
    public String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
