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
    String url = "https://open.douyin.com/oauth/userinfo/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        getToken();
        findViewById(R.id.btn_userinfo).setOnClickListener(this);
        tv_username = findViewById(R.id.tv_username);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_userinfo:
                getUserInfo();
                break;
        }
    }

    public void getUserInfo(){
        HttpTest httpTest = new HttpTest();

        new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("------------getuserinfo------------");
                httpTest.getUserInfo(url);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.tv_username.setText(HttpTest.userMessage.getData().getNickname());
                    }
                });
            }
        }).start();
    }

    public boolean getToken(){
        Authorization.Request request = new Authorization.Request();
        request.scope = "trial.whitelist,user_info";
        return douYinOpenApi.authorize(request);
    }

}