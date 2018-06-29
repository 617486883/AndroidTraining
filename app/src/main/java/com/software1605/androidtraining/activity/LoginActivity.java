package com.software1605.androidtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.dao.UserDao;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.factory.UserFactory;

public class LoginActivity extends AppCompatActivity {

    Handler handler;
    EditText nameEditText,passwordEditText;
    String name ;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameEditText= findViewById(R.id.name);
        passwordEditText = findViewById(R.id.password);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123){
                    Bundle bundle = msg.getData();
                    String response = bundle.getString("response");
                    Log.i("list--->",response.toString());
                    User user = JSON.parseObject(response, User.class);
                    if (user.getState() == 200){
                        //成功返回信息
                        Toast.makeText(LoginActivity.this, user.getStateInfo(),Toast.LENGTH_SHORT).show();
                        Log.i("user---->", "handleMessage: "+user);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, user.getStateInfo(),Toast.LENGTH_SHORT).show();
                        nameEditText.setText("");
                        passwordEditText.setText("");
                    }
                }
            }
        };

    }

    public void login(View view){
        name = nameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        UserDao userDao = UserFactory.getUserInterf();
        userDao.checkLogin(name,password,handler);
    }
}
