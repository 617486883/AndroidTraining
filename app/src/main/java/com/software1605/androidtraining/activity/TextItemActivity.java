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
import com.software1605.androidtraining.entity.Text;

import java.util.Map;

public class TextItemActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String URL="http://wxscjy.free.ngrok.cc/text/updateText";
    private static final String URL_DELETE="http://wxscjy.free.ngrok.cc/text/delectText";
    private ImageView back;
    private EditText context,title;
    private TextView userName;
    private Button chongzhi,update;
    private Text text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_item2);
        userName = findViewById(R.id.userName);
        context = findViewById(R.id.context_item);
        title = findViewById(R.id.title);
        chongzhi = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        back = findViewById(R.id.back_admin);
        chongzhi.setOnClickListener(this);
        back.setOnClickListener(this);
        update.setOnClickListener(this);
        Intent intent = getIntent();
        text = (Text) intent.getSerializableExtra("text");
        Log.i("------------->", "onCreate: textItem"+text);
        userName.setText(text.getUserName());
        title.setText(text.getTitle());
        context.setText(text.getContent());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
               OkhttpUtil.getByType(URL_DELETE,"id",text.getId()+"",handler);
                break;
            case R.id.update:
                if (userName.getText().toString().equals("")
                        || context.getText().toString().equals("") || title.getText().toString().equals("")){
                    Toast.makeText(TextItemActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
                }else {
                    text.setContent(context.getText().toString());
                    text.setTitle(title.getText().toString());
                    String json = JSON.toJSONString(text);
                    OkhttpUtil.postByJson(URL,json,handler);

                }
                break;
            case R.id.back_admin:
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
                String resp = bundle.getString("response");
                try {
                    Map map = JSON.parseObject(resp,Map.class);
                    String stateInfo = (String) map.get("stateInfo");
                    Toast.makeText(TextItemActivity.this, stateInfo,Toast.LENGTH_SHORT).show();
                    int state = (int) map.get("state");
                    if (state == 200){
                        Intent intent = new Intent(TextItemActivity.this,TextActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.i("--------->", "handleMessage: +myItemCoord "+resp);
                }catch (Exception e){
                    Toast.makeText(TextItemActivity.this,"服务器报错,连接服务器失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

}
