package com.software1605.androidtraining.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.adapotr.TextAdaport;
import com.software1605.androidtraining.entity.Text;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.List;

public class LikeItemActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String URL = "http://wxscjy.free.ngrok.cc/text/findTextByUserId";
    List<Text> list;
    private ListView listView;
    private ImageView back;
    private User user;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
               String respones = (String) bundle.get("response");
               list = JSON.parseArray(respones,Text.class);

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_item);
        listView = findViewById(R.id.listView);
        back = findViewById(R.id.back_btn);
        //得到用户名转化为json
        user = UserInfo.getUserInfo().getUser();
        String name = JSON.toJSONString(user.getName()) ;
        //联网请求数据
        OkhttpUtil.postByJson(URL,name,handler);
        //ListView
        TextAdaport textAdaport = new TextAdaport(this,R.layout.listview_item,list);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;

        }
    }
}
