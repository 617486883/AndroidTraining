package com.software1605.androidtraining.activity;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.BottomBar;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.fragment.CooreFragment;
import com.software1605.androidtraining.fragment.HomeFragment;
import com.software1605.androidtraining.fragment.LikeFragment;
import com.software1605.androidtraining.fragment.UserFragment;
import com.software1605.androidtraining.staticDate.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

   public static  User user;
   Handler handler;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //给与定位权限
        startLocation();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建4个Fragment
        createFragment();
        getuser();

    }

    @Override
    protected void onStart() {
        super.onStart();

        getuser();
    }


    //拿到用户数据
    private void getuser(){

        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        if (sp.getString("name",null).equals("")){
            //传递数据，从登录拿到用户
            user = UserInfo.getUserInfo().getUser();
        }else {
            //从本地文件拿到账号密码，联网拿到数据
            String name = sp.getString("name",null);
            String password = sp.getString("password",null);
            Log.i("------------------>", "onCreate: "+name);
            if (!name.equals("")){
                Map map = new HashMap();
                map.put("name",name);
                map.put("password",password);
                handlerManage();
                String json = JSON.toJSONString(map);
                OkhttpUtil.postByJson(LoginActivity.URL,json,handler);
            }else {
                user = UserInfo.getUserInfo().getUser();
            }

        }

    }

//设置Fragment
    public void createFragment(){
        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
                .addItem(HomeFragment.class,
                        "首页",
                        R.drawable.home,
                        R.drawable.home_select)
                .addItem(CooreFragment.class,
                        "地标",
                        R.drawable.coord,
                        R.drawable.coord_select)
                .addItem(LikeFragment.class,
                        "留言",
                        R.drawable.like,
                        R.drawable.like_select)
                .addItem(UserFragment.class,
                        "我的",
                        R.drawable.user,
                        R.drawable.user_select).build();

    }

    //通过自动登录拿到用户的线程
    public void handlerManage(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x123){
                    Bundle bundle = msg.getData();
                    String response = bundle.getString("response");
                    user = JSON.parseObject(response, User.class);
                  //重新存入
                    UserInfo.getUserInfo().setUser(user);
                    Log.i("list--->",response.toString());
                }
            }
        };

    }

    //给与权限
    private void startLocation() {
        int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            Log.d("TTTT", "弹出提示");
            return;
        }
    }


}
