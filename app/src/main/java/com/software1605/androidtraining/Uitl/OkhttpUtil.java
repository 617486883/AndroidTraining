package com.software1605.androidtraining.Uitl;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp联网工具类
 */
public class OkhttpUtil {
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    /**
     *  该方法用于POST请求，并且返回为JSON，传参为Json 返回Handler 值为0x123
     * @param url  地址
      * @param json 数据
     * @param handler 线程
     */
    public static void postByJson(String url, String json, final Handler handler){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().post(requestBody).url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 0x123;
                Bundle bundle = new Bundle();
                bundle.putString("response","联网失败");
                message.setData(bundle);
                handler.sendMessage(message);
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


    /**
     /* *
     *
     * @param url
     * @param jsonType  上传类型是Text user等
     * @param json
     * @param file
     * @param handler
     */
    public static void postFile(String url,String jsonType, String json, File file, final Handler handler){
        OkHttpClient okHttpClient = new OkHttpClient();
        //RequestBody multipartBody = new MultipartBody.Builder()
                //.setType(MultipartBody.ALTERNATIVE).addPart()
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "head_image", fileBody)
                //.addFormDataPart("imagetype", imageType)
                .addFormDataPart(jsonType,json)
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 0x001;
                Bundle bundle = new Bundle();
                bundle.putString("response","错误提示");
                message.setData(bundle);
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 0x001;
                Bundle bundle = new Bundle();
                bundle.putString("response",response.body().string());
                message.setData(bundle);
                handler.sendMessage(message);
            }
        });

    }
    /**
     * 传递一个参数
     */
    public static void getByType(String url , String type, String data, final Handler handler){
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add(type,data)
                .build();

        Request request = new Request.Builder().post(body).url(url).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

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
