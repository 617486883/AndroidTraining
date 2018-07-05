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
import com.software1605.androidtraining.entity.CoordItem;

import java.util.Map;

public class MyCoordItemActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String URL="http://wxscjy.free.ngrok.cc/coord/updateCoodr";
    private static final String URL_DELETE="http://wxscjy.free.ngrok.cc/coord/delectCoor";
    private ImageView back;
    private EditText userName,lo,lg,name;
    private Button chongzhi,update;
    private CoordItem coordItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coord_item);
        userName = findViewById(R.id.userName);
        lo = findViewById(R.id.longitude_item);
        lg = findViewById(R.id.context_item);
        name = findViewById(R.id.title);
        chongzhi = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        back = findViewById(R.id.back_admin);
        back.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        update.setOnClickListener(this);
        Intent intent = getIntent();
        coordItem = (CoordItem) intent.getSerializableExtra("coord");
        Log.i("------------->", "onCreate: coordItem"+coordItem);
        userName.setText(coordItem.getUserName());
        lo.setText(coordItem.getLongitude()+"");
        lg.setText(coordItem.getLatitude()+"");
        name.setText(coordItem.getName());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.delete:
                OkhttpUtil.getByType(URL_DELETE,"id",coordItem.getId()+"",handler);

                break;
            case R.id.update:
                if (userName.getText().toString().equals("") || lo.getText().toString().equals("")
                        || lg.getText().toString().equals("") || name.getText().toString().equals("")){
                    Toast.makeText(MyCoordItemActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
                }else {
                    coordItem.setLatitude(Double.parseDouble(lg.getText().toString()));
                    coordItem.setLongitude(Double.parseDouble(lo.getText().toString()));
                    coordItem.setName(name.getText().toString());
                    coordItem.setUserName(userName.getText().toString());
                    String req = JSON.toJSONString(coordItem);
                    OkhttpUtil.postByJson(URL,req,handler);
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
                Log.i("--------->", "handleMessage: +myItemCoord "+resp);
                try {
                    Map map = JSON.parseObject(resp,Map.class);

                    String stateInfo = (String) map.get("stateInfo");
                    Toast.makeText(MyCoordItemActivity.this, stateInfo,Toast.LENGTH_SHORT).show();
                    int state = (int) map.get("state");
                    if (state == 200){
                        Intent intent = new Intent(MyCoordItemActivity.this,MyCoordActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (Exception e){
                    Toast.makeText(MyCoordItemActivity.this,"服务器报错,连接服务器失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

}
