package com.qxy.GenshinImpact.JsonData;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Auto-generated: 2022-07-30 18:54:42
 *
 * @author json996.com
 * @website http://www.json996.com/json2java.html
 */
public class UserMessage {

    private UserData data;
    private String message;
    public UserMessage(){

    }
    public void setData(UserData data) {
        this.data = data;
    }
    public UserData getData() {
        return data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
