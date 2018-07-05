package com.software1605.androidtraining.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.adapotr.CoordAdaport;
import com.software1605.androidtraining.entity.CoordItem;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.List;

public class MyCoordActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String URL= "http://wxscjy.free.ngrok.cc/coord/findByUser";
    private ImageView backBtn;
    private ListView listView;
    private TextView add_btn;
    private CoordAdaport coordAdaport;
    private List<CoordItem> list;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
                String resp = bundle.getString("response");
                try {
                    list = JSON.parseArray(resp,CoordItem.class);
                    coordAdaport = new CoordAdaport(MyCoordActivity.this,R.layout.coor_listview_item,list);
                    listView.setAdapter(coordAdaport);
                }catch (Exception e){
                    Toast.makeText(MyCoordActivity.this,"服务器报错,连接服务器失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coord);
        backBtn = findViewById(R.id.mycoord_back);
        listView =findViewById(R.id.mycood_listView);
        add_btn = findViewById(R.id.textview19);
        add_btn.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        User user = UserInfo.getUserInfo().getUser();
        if (user!=null){
            OkhttpUtil.getByType(URL,"name",user.getName(),handler);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MyCoordActivity.this,MyCoordItemActivity.class);
                intent.putExtra("coord",list.get(i));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mycoord_back:
                finish();
                break;
            case R.id.textview19:
                Intent intent = new Intent(MyCoordActivity.this,MyCoordItemAddActibity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
