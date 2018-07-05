package com.software1605.androidtraining.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.adapotr.TextAdaport;
import com.software1605.androidtraining.entity.Text;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.List;

public class TextActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String URL = "http://wxscjy.free.ngrok.cc/text/findTextByUserId";
    List<Text> list;
    private ListView listView;
    private ImageView back;
    private User user;
    private TextView add;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
               String respones = (String) bundle.get("response");
                Log.i("---------->", "handleMessage: "+respones);
               try {
                   list = JSON.parseArray(respones,Text.class);
                   TextAdaport textAdaport = new TextAdaport(TextActivity.this,R.layout.listview_item,list);
                    listView.setAdapter(textAdaport);
               }catch (Exception e){
                   Toast.makeText(TextActivity.this,"连接服务器失败",Toast.LENGTH_SHORT).show();
               }
            }


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_item);
        listView = findViewById(R.id.listView);
        back = findViewById(R.id.back_btn);
        add = findViewById(R.id.textView6);
        //得到用户名转化为json
        user = UserInfo.getUserInfo().getUser();
        String name = user.getName() ;
        //联网请求数据
        OkhttpUtil.getByType(URL,"name",name,handler);
        //ListView
        back.setOnClickListener(this);
        add.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TextActivity.this,TextItemActivity.class);
                intent.putExtra("text",list.get(i));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.textView6:
                Intent intent = new Intent(TextActivity.this,TextItemAddActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
