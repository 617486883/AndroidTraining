package com.software1605.androidtraining.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 登陆页面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String URL = "http://wxscjy.free.ngrok.cc/user/login";
    Handler handler;
    EditText nameEditText,passwordEditText;
    String name ;
    String password;
    TextView register,passwordbtn;
    Button login;
    private Context mContext = this;
    private SharedPreferences sp;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameEditText= findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        register = findViewById(R.id.register_btn);
        passwordbtn = findViewById(R.id.passwordbtn);
        login = findViewById(R.id.login_btn);

        register.setOnClickListener(this);
        passwordbtn.setOnClickListener(this);
        login.setOnClickListener(this);

        handlerManage();
        //拿到本地文件
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sp.edit();
    }



    //处理handler
    @SuppressLint("HandlerLeak")
    public void handlerManage(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //联网失败
                if (msg.what == 0x124){
                    Bundle bundle = msg.getData();
                    String response = bundle.getString("response");
                    Toast.makeText(LoginActivity.this, response,Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 0x123){
                    Bundle bundle = msg.getData();
                    String response = bundle.getString("response");
                    Log.i("list--->",response.toString());
                    User user = null;
                    try {
                      user = JSON.parseObject(response, User.class);
                        if (user.getState() == 200){
                            //成功返回信息
                            Toast.makeText(LoginActivity.this, user.getStateInfo(),Toast.LENGTH_SHORT).show();
                            Log.i("user---->", "handleMessage: "+user);
                            //存入缓存文件
                            editor.putString("name",user.getName());
                            editor.putString("password",user.getPassword());
                            editor.commit();
                            //存入数据
                            UserInfo.getUserInfo().setUser(user);
                            //跳转
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, user.getStateInfo(),Toast.LENGTH_SHORT).show();
                            nameEditText.setText("");
                            passwordEditText.setText("");
                        }
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "发现错误,无法登录，请检查网络问题！",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        };

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //点击注册
            case R.id.register_btn:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
                //点击登录
            case R.id.login_btn:
                name = nameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                Log.i("aaaa", "onClick: "+name);
                if (name.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this,"请输入正确的用户名和密码",Toast.LENGTH_SHORT).show();
                }else {
                    Map<String,String> map = new HashMap<>();
                    map.put("name",name);
                    map.put("password",password);
                    String json = JSON.toJSONString(map);
                    OkhttpUtil.postByJson(URL,json,handler);
                }
                break;
                //点击忘记密码
            case R.id.passwordbtn:
              Intent intent1 = new Intent(LoginActivity.this,UpdateUserPasswordActivity.class);
              startActivity(intent1);
              finish();
                break;
        }
    }
}
