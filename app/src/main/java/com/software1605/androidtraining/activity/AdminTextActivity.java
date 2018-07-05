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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.entity.Text;

import java.util.List;
import java.util.Map;

public class AdminTextActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String URL="http://wxscjy.free.ngrok.cc/text/updateText";
    private static final String URL_DELETE="http://wxscjy.free.ngrok.cc/text/delectText";
    private static final String URL_INSERT="http://wxscjy.free.ngrok.cc/text/insertText";
    private static final String URL_SELECT = "http://wxscjy.free.ngrok.cc/text/findTextByTextTitle";
    private ImageView back;
    private EditText userName,title,content;
    private Button select,add,update,delete;
    private Text text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_text);
        userName = findViewById(R.id.userName);
        title = findViewById(R.id.title);
        content = findViewById(R.id.context_item);

        select = findViewById(R.id.select);
        add = findViewById(R.id.add);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        back = findViewById(R.id.back_admin);

        back.setOnClickListener(this);
        select.setOnClickListener(this);
        add.setOnClickListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.select:
        if (title.getText().toString().equals("")){
            Toast.makeText(AdminTextActivity.this,"请输入正确的标题",Toast.LENGTH_SHORT).show();
        }else {
            OkhttpUtil.getByType(URL_SELECT,"title",title.getText().toString(),handler);
        }
        break;


        case R.id.update:
        if (userName.getText().toString().equals("") || title.getText().toString().equals("")
                || content.getText().toString().equals("") ){
            Toast.makeText(AdminTextActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
        }else {
            if (text!=null){
                text.setId(text.getId());
                text.setUserName(userName.getText().toString());
                text.setContent(content.getText().toString());
                text.setTitle(title.getText().toString());
                String req = JSON.toJSONString(text);
                OkhttpUtil.postByJson(URL,req,handler1);
            }else {
                Toast.makeText(AdminTextActivity.this,"请先查询",Toast.LENGTH_SHORT).show();
            }

        }
        break;
        case R.id.delete:
        if (userName.getText().toString().equals("") || content.getText().toString().equals("")
                || title.getText().toString().equals("")){
            Toast.makeText(AdminTextActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
        }else {
            if (text == null){
                Toast.makeText(AdminTextActivity.this,"请先查询",Toast.LENGTH_SHORT).show();
            }else {
                OkhttpUtil.getByType(URL_DELETE,"id",text.getId()+"",handler1);
            }
        }
        break;
        case R.id.add:
        if (userName.getText().toString().equals("") || title.getText().toString().equals("")
                || content.getText().toString().equals("") ){
            Toast.makeText(AdminTextActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
        }else {
            text = new Text();
            text.setUserName(userName.getText().toString());
            text.setContent(content.getText().toString());
            text.setTitle(title.getText().toString());
            String req = JSON.toJSONString(text);
            OkhttpUtil.postByJson(URL_INSERT,req,handler1);
        }

        break;
        case R.id.back_admin:
            finish();
        break;
    }
    }

    //处理查询
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
                try {
                    List<Text> list = JSON.parseArray(bundle.getString("response"),Text.class);
                    text = list.get(0);
                    userName.setText(text.getUserName());
                    title.setText(text.getTitle());
                    content.setText(text.getContent());
                }catch (Exception e){
                    Toast.makeText(AdminTextActivity.this,"连接服务器失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


    @SuppressLint("HandlerLeak")
    Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
                String resp = bundle.getString("response");
                Log.i("--------->", "handleMessage: +myItemCoord "+resp);
                try {
                    Map map = JSON.parseObject(resp,Map.class);
                    String stateInfo = (String) map.get("stateInfo");
                    Toast.makeText(AdminTextActivity.this, stateInfo,Toast.LENGTH_SHORT).show();
                    int state = (int) map.get("state");
                    if (state == 200){
                        Intent intent = new Intent(AdminTextActivity.this,AdminTableActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (Exception e){
                    Toast.makeText(AdminTextActivity.this,"服务器报错,连接服务器失败",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
}
