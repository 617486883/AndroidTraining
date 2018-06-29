package com.software1605.androidtraining.daoImpl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.software1605.androidtraining.dao.UserDao;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
         * 从网络获取用户数据
        */
public class UserDaoInterentImpl implements UserDao {
    public static final String CKECK_LOGIN_URL = "http://wxscjy.free.ngrok.cc/user/login";

    @Override
    public void checkLogin(String name , String password, final Handler handler) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();
        formBody.add("name",name);
        formBody.add("password",password);
        Request request = new Request.Builder().url(CKECK_LOGIN_URL).post(formBody.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Error-->",e.getLocalizedMessage());
           //     Toast.makeText(context,"网络连接失败!,清检查网络",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 0x123;
                Bundle bundle = new Bundle();
                bundle.putString("response",response.body().string());
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }

}
