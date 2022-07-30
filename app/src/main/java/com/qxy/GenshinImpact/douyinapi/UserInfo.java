package com.qxy.GenshinImpact.douyinapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bytedance.sdk.open.aweme.CommonConstants;
import com.bytedance.sdk.open.aweme.authorize.model.Authorization;
import com.bytedance.sdk.open.aweme.common.handler.IApiEventHandler;
import com.bytedance.sdk.open.aweme.common.model.BaseReq;
import com.bytedance.sdk.open.aweme.common.model.BaseResp;
import com.bytedance.sdk.open.douyin.DouYinOpenApiFactory;
import com.bytedance.sdk.open.douyin.api.DouYinOpenApi;
import com.qxy.GenshinImpact.HttpTest;
import com.qxy.GenshinImpact.MainActivity;
import com.qxy.GenshinImpact.R;

public class UserInfo extends Activity implements IApiEventHandler {
    DouYinOpenApi douYinOpenApi;

    public static String authCode;

    String url = "https://open.douyin.com/oauth/userinfo/";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        douYinOpenApi = DouYinOpenApiFactory.create(this);
        douYinOpenApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq req) {

    }


    @Override
    public void onResp(BaseResp resp) {
        // 授权成功可以获得authCode
        if (resp.getType() == CommonConstants.ModeType.SEND_AUTH_RESPONSE) {
            Authorization.Response response = (Authorization.Response) resp;
            authCode = ((Authorization.Response) resp).authCode;
            System.out.println(authCode);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    System.out.println("------------getuserinfo------------");
                    HttpTest httpTest = new HttpTest();
                    httpTest.getToken(authCode);
                    httpTest.getUserInfo(url);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.tv_username.setText(HttpTest.userMessage.getData().getNickname());
                        }
                    });
                }
            }).start();
            if (resp.isSuccess()) {
                Toast.makeText(this, "授权成功，获得权限：" + response.grantedPermissions,
                        Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "授权失败" + response.grantedPermissions,
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    @Override
    public void onErrorIntent(@Nullable Intent intent) {
        // 错误数据
        Toast.makeText(this, "intent出错啦", Toast.LENGTH_LONG).show();
        finish();
    }
}
