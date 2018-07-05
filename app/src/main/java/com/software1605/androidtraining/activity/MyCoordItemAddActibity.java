package com.software1605.androidtraining.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.entity.CoordItem;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.Map;

public class MyCoordItemAddActibity extends AppCompatActivity implements View.OnClickListener{

    private static final String URL="http://wxscjy.free.ngrok.cc/coord/insertCoord";
    private ImageView back;
    private EditText lo,lg,name;
    private TextView userName;
    private Button update;
    private User user = UserInfo.getUserInfo().getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coord_item_add_actibity);
        userName = findViewById(R.id.userName);
        lo = findViewById(R.id.longitude_item);
        lg = findViewById(R.id.context_item);
        name = findViewById(R.id.title);
        update = findViewById(R.id.update);
        back = findViewById(R.id.back_admin);
        back.setOnClickListener(this);
        update.setOnClickListener(this);
        userName.setText(user.getUserName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_admin:
                Intent intent = new Intent(MyCoordItemAddActibity.this,MyCoordActivity.class);
                startActivity(intent);

                finish();
                break;
            case R.id.update:
                if (name.getText().toString().equals("") || lo.getText().toString().equals("") || lg.getText().toString().equals(""))
                {
                    Toast.makeText(MyCoordItemAddActibity.this,"请填写正确的格式",Toast.LENGTH_SHORT).show();
                }else {
                    CoordItem coordItem = new CoordItem();
                    coordItem.setUserName(userName.getText().toString());
                    coordItem.setName(name.getText().toString());
                    coordItem.setLongitude(Double.parseDouble(lo.getText().toString()));
                    coordItem.setLatitude(Double.parseDouble(lg.getText().toString()));
                    String json = JSON.toJSONString(coordItem);
                    OkhttpUtil.postByJson(URL,json,handler);

                }
                break;

        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
                String resp = bundle.getString("response");
                try {
                    Map map = JSON.parseObject(resp,Map.class);
                    String stateInfo = (String) map.get("stateInfo");
                    Toast.makeText(MyCoordItemAddActibity.this, stateInfo,Toast.LENGTH_SHORT).show();
                    int state = (int) map.get("state");
                    if (state == 200){
                        Intent intent = new Intent(MyCoordItemAddActibity.this,MyCoordActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.i("--------->", "handleMessage: +myItemCoord "+resp);
                }catch (Exception e){
                    Toast.makeText(MyCoordItemAddActibity.this,"服务器报错,连接服务器失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

}
