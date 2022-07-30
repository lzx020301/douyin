package com.qxy.GenshinImpact;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.DouYinOpenConfig;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.GenshinImpact.douyinapi.DouYinEntryActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DouYinOpenApi douYinOpenApi;
    public static int flags = 0;
    public static TextView tv_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        douYinOpenApi = DouYinOpenApiFactory.create(this);

        findViewById(R.id.btn_white).setOnClickListener(this);
        findViewById(R.id.btn_userinfo).setOnClickListener(this);

        tv_username = findViewById(R.id.tv_username);
        findViewById(R.id.btn_u).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_white:
                getToken();
                break;
            case R.id.btn_userinfo:
                getUserInfo();
                break;
            case R.id.btn_u:
                tv_username.setText(HttpTest.userMessage.getData().getNickname());
        }
    }

    public boolean getUserInfo(){
        Authorization.Request request = new Authorization.Request();
        request.scope = "user_info";
        request.callerLocalEntry = "com.qxy.GenshinImpact.douyinapi.UserInfo";
        return douYinOpenApi.authorize(request);
    }

    public boolean getToken(){
        Authorization.Request request = new Authorization.Request();
        request.scope = "user_info";
        return douYinOpenApi.authorize(request);
    }


}