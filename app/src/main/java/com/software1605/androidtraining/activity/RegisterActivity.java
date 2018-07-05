package com.software1605.androidtraining.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册类
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String URL = "http://wxscjy.free.ngrok.cc/user/register";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private EditText passwordEd, nameEdit,userNameEdit;
    private Button register;
    private TextView login;
    Handler handler;
    String imgurl = null;
    File file ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        passwordEd = findViewById(R.id.password);
        nameEdit = findViewById(R.id.name);
        userNameEdit = findViewById(R.id.userName);
        register = findViewById(R.id.register);
        login = findViewById(R.id.btn_login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
                //点击注册
            case R.id.register:
                String name = nameEdit.getText().toString();
                String password = passwordEd.getText().toString();
                String userName = userNameEdit.getText().toString();
                //校验是否为空
                if (name.equals("") || password.equals("") || userName.equals("")){
                    Toast.makeText(RegisterActivity.this,"清输入正确的格式，不得有空",Toast.LENGTH_SHORT).show();

                }else {
                        Map map = new HashMap();
                        map.put("name",name);
                        map.put("password",password);
                        map.put("userName",userName);
                        String json = JSON.toJSONString(map);
                        handlerManager();
                        OkhttpUtil.postByJson(URL,json,handler);
                    }
                break;
                //点击已有账号，清登录
            case R.id.btn_login:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }



    @SuppressLint("HandlerLeak")
    public void handlerManager(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x123){
                    Bundle bundle = msg.getData();
                   String s = (String) bundle.get("response");
                   try {
                       Map map = JSON.parseObject(s,Map.class);
                       String stateInfo = (String) map.get("stateInfo");
                       Toast.makeText(RegisterActivity.this, stateInfo,Toast.LENGTH_SHORT).show();
                       int state = (int) map.get("state");
                       if (state == 200){
                           Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                           startActivity(intent);
                           finish();
                       }
                   }catch (Exception e){
                        Toast.makeText(RegisterActivity.this,"连接不到服务器",Toast.LENGTH_SHORT).show();
                   }

                }
            }
        };

    }


}
