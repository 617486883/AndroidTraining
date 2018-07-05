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

import java.util.List;
import java.util.Map;

public class AdminCoordActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String URL="http://wxscjy.free.ngrok.cc/coord/updateCoodr";
    private static final String URL_DELETE="http://wxscjy.free.ngrok.cc/coord/delectCoor";
    private static final String URL_INSERT="http://wxscjy.free.ngrok.cc/coord/insertCoord";
    private static final String URL_SELECT = "http://wxscjy.free.ngrok.cc/coord/findByName";
    private ImageView back;
    private EditText userName,lo,lg,name;
    private Button select,update,insert,delete;
    private CoordItem coordItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_coord);
        userName = findViewById(R.id.userName);
        lo = findViewById(R.id.longitude_item);
        lg = findViewById(R.id.context_item);
        name = findViewById(R.id.title);
        back = findViewById(R.id.back_admin);
        select = findViewById(R.id.select);
        update = findViewById(R.id.update);
        insert = findViewById(R.id.add);
        delete = findViewById(R.id.delete);

        back.setOnClickListener(this);
        select.setOnClickListener(this);
        update.setOnClickListener(this);
        insert.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select:
                if (name.getText().toString().equals("")){
                    Toast.makeText(AdminCoordActivity.this,"请输入正确的标题",Toast.LENGTH_SHORT).show();
                }else {
                    OkhttpUtil.getByType(URL_SELECT,"name",name.getText().toString(),handler);
                }
                break;


            case R.id.update:
                if (userName.getText().toString().equals("") || lo.getText().toString().equals("")
                        || lg.getText().toString().equals("") || name.getText().toString().equals("")){
                    Toast.makeText(AdminCoordActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
                }else {
                    if (coordItem!=null){
                        coordItem.setId(coordItem.getId());
                        coordItem.setLatitude(Double.parseDouble(lg.getText().toString()));
                        coordItem.setLongitude(Double.parseDouble(lo.getText().toString()));
                        coordItem.setName(name.getText().toString());
                        coordItem.setUserName(userName.getText().toString());
                        String req = JSON.toJSONString(coordItem);
                        OkhttpUtil.postByJson(URL,req,handler1);
                    }else {
                        Toast.makeText(AdminCoordActivity.this,"请先查询",Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.delete:
                if (userName.getText().toString().equals("") || lo.getText().toString().equals("")
                        || lg.getText().toString().equals("") || name.getText().toString().equals("")){
                    Toast.makeText(AdminCoordActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
                }else {
                    if (coordItem == null){
                        Toast.makeText(AdminCoordActivity.this,"请先查询",Toast.LENGTH_SHORT).show();
                    }else {
                        OkhttpUtil.getByType(URL_DELETE,"id",coordItem.getId()+"",handler1);
                    }
                }
                break;
            case R.id.add:
                if (userName.getText().toString().equals("") || lo.getText().toString().equals("")
                        || lg.getText().toString().equals("") || name.getText().toString().equals("")){
                    Toast.makeText(AdminCoordActivity.this,"请输入正确的格式",Toast.LENGTH_SHORT).show();
                }else {
                    coordItem.setLatitude(Double.parseDouble(lg.getText().toString()));
                    coordItem.setLongitude(Double.parseDouble(lo.getText().toString()));
                    coordItem.setName(name.getText().toString());
                    coordItem.setUserName(userName.getText().toString());
                    String req = JSON.toJSONString(coordItem);
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
                    List<CoordItem> list = JSON.parseArray(bundle.getString("response"),CoordItem.class);
                    coordItem = list.get(0);
                    name.setText(coordItem.getName());
                    lg.setText(coordItem.getLatitude()+"");
                    lo.setText(coordItem.getLongitude()+"");
                    userName.setText(coordItem.getUserName());
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
                try {
                    Map map = JSON.parseObject(resp,Map.class);
                    String stateInfo = (String) map.get("stateInfo");
                    Toast.makeText(AdminCoordActivity.this, stateInfo,Toast.LENGTH_SHORT).show();
                    int state = (int) map.get("state");
                    if (state == 200){
                        Intent intent = new Intent(AdminCoordActivity.this,AdminTableActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Log.i("--------->", "handleMessage: +myItemCoord "+resp);
                }catch (Exception e){
                    Toast.makeText(AdminCoordActivity.this,"服务器报错,连接服务器失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

}
