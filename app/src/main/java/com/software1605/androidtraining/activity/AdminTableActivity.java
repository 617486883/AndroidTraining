package com.software1605.androidtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.software1605.androidtraining.R;

public class AdminTableActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private TextView liuyan,dibiao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_table);
        back = findViewById(R.id.back_btn);
        liuyan = findViewById(R.id.liuyan);
        dibiao = findViewById(R.id.dibiao);

        back.setOnClickListener(this);
        liuyan.setOnClickListener(this);
        dibiao.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_btn:
               finish();
                break;
            case R.id.liuyan:
                Intent intent = new Intent(AdminTableActivity.this,AdminTextActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.dibiao:
                Intent intent1 = new Intent(AdminTableActivity.this,AdminCoordActivity.class);
                startActivity(intent1);
                finish();
                break;


        }
    }
}
