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
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.Map;

public class TextItemAddActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String URL="http://wxscjy.free.ngrok.cc/text/insertText";
    private ImageView back;
    private EditText context,title;
    private TextView userName;
    private Button update;
    private Text text;
    private User user = UserInfo.getUserInfo().getUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_item_add);
        userName = findViewById(R.id.userName);
        context = findViewById(R.id.context_item);
        title = findViewById(R.id.title);
        update = findViewById(R.id.delete);
        back = findViewById(R.id.back_admin);
        back.setOnClickListener(this);
        update.setOnClickListener(this);
        userName.setText(user.getUserName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_admin:
                Intent intent = new Intent(TextItemAddActivity.this,TextActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.delete:
                if (title.getText().toString().equals("") || context.getText().toString().equals("") )
                {
                    Toast.makeText(TextItemAddActivity.this,"请填写正确的格式",Toast.LENGTH_SHORT).show();
                }else {
                   Text text = new Text();
                   text.setTitle(title.getText().toString());
                   text.setContent(context.getText().toString());
                   text.setUserName(user.getUserName());
                    String json = JSON.toJSONString(text);
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
                    Toast.makeText(TextItemAddActivity.this, stateInfo,Toast.LENGTH_SHORT).show();
                    int state = (int) map.get("state");
                    if (state == 200){
                        Intent intent = new Intent(TextItemAddActivity.this,TextActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.i("--------->", "handleMessage: +myItemCoord "+resp);
                }catch (Exception e){
                    Toast.makeText(TextItemAddActivity.this,"服务器报错,连接服务器失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

}
