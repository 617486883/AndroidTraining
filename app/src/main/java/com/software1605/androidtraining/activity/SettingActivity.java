package com.software1605.androidtraining.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.software1605.androidtraining.R;
import com.software1605.androidtraining.staticDate.UserInfo;

public class SettingActivity extends AppCompatActivity  implements View.OnClickListener{
    ImageView return_btn;
    TextView myuser,exit,updateuser;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        return_btn = findViewById(R.id.return_btn);
        myuser = findViewById(R.id.myuser);
        exit = findViewById(R.id.exit);
        updateuser = findViewById(R.id.updateuser);

        return_btn.setOnClickListener(this);
        myuser.setOnClickListener(this);
        exit.setOnClickListener(this);
        updateuser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.return_btn:
                finish();
                break;
            case R.id.myuser:
                break;
            case R.id.exit:
                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
               sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
               editor = sp.edit();
              editor.putString("name","");
              editor.commit();
                UserInfo.getUserInfo().setUser(null);

                startActivity(intent);
                finish();
                break;
            case R.id.updateuser:
                break;

        }
    }
}
