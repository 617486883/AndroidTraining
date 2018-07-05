package com.software1605.androidtraining.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.HashMap;
import java.util.Map;

//修改用户
public class UserManagerAcitivity extends AppCompatActivity implements View.OnClickListener{
    private static final String URL = "http://wxscjy.free.ngrok.cc/user/update";
    private EditText changeName,changePassword;
    private ImageView updateBtn;
    private TextView quxiaoBtn;
    User user = UserInfo.getUserInfo().getUser();
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermanager);
        changeName = findViewById(R.id.changeName);
        changePassword = findViewById(R.id.changePassNew);
        updateBtn = findViewById(R.id.update_btn);
        quxiaoBtn = findViewById(R.id.quxiao_btn);
        updateBtn.setOnClickListener(this);
        quxiaoBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.update_btn:
                if (changePassword.getText().toString().equals("")&& changeName.getText().toString().equals("")){
                    Toast.makeText(UserManagerAcitivity.this,"请输入正确的内容",Toast.LENGTH_SHORT).show();
                }else {
                    if (user!=null ){
                        Map map = new HashMap();

                        map.put("name",user.getName());
                        if (!changeName.getText().toString().equals("")){
                            map.put("userName",changeName.getText().toString());
                        }
                        if (!changePassword.getText().toString().equals("")){
                            map.put("password",changePassword.getText().toString());
                        }

                        String json = JSON.toJSONString(map);
                        OkhttpUtil.postByJson(URL,json,handler);
                    }

                }

                break;
            case R.id.quxiao_btn:
                finish();
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
               String resp = (String) bundle.get("response");
                Log.i("------------>",resp);
                try {
                    Map  s = JSON.parseObject(resp,Map.class);
                    String stateInfo = (String) s.get("stateInfo");
                    Toast.makeText(UserManagerAcitivity.this,stateInfo,Toast.LENGTH_SHORT).show();
                    int state = (int) s.get("state");
                    if (state == 200){
                        UserInfo.getUserInfo().setUser(null);
                        Intent intent = new Intent(UserManagerAcitivity.this,LoginActivity.class);
                        startActivity(intent);

                    }
                }catch (Exception e){
                    Toast.makeText(UserManagerAcitivity.this,"服务器错误",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
}
