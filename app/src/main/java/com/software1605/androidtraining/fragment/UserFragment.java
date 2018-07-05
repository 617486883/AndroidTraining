package com.software1605.androidtraining.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.activity.TextActivity;
import com.software1605.androidtraining.activity.LoginActivity;
import com.software1605.androidtraining.activity.MyCoordActivity;
import com.software1605.androidtraining.activity.SettingActivity;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.staticDate.UserInfo;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户
 */
public class UserFragment extends Fragment {
    String IMG_URL_HTTP="http://wxscjy.free.ngrok.cc/";
    Handler handler;
    User user;
    CircleImageView circleImageView;
    TextView userName;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view =  inflater.inflate(R.layout.fragment_user,container,false);
       //设置按钮
       ImageView setting = view.findViewById(R.id.setting_btn);
        //用户名
       userName = view.findViewById(R.id.username);
       //头像框
       circleImageView = view.findViewById(R.id.circleImageView);
       //获取到用户
       user = UserInfo.getUserInfo().getUser();
       //用户不为空，将用户数据显示到前端
        TextView mycood = view.findViewById(R.id.mycood);
        TextView mytext = view.findViewById(R.id.mytext);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用户为空跳转至登录界面
                if (user == null){
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
            }
        });
        mycood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null){
                    Toast.makeText(getActivity(),"您还未登录，请登录",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), MyCoordActivity.class);
                    startActivity(intent);
                }
            }
        });

        mytext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null){
                    Toast.makeText(getActivity(),"您还未登录，请登录",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(getActivity(), TextActivity.class);
                    startActivity(intent);
                }
            }
        });
    //    Log.i("user---->", "onCreate: "+user);
        //点击设置
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = UserInfo.getUserInfo().getUser();
                //用户为空跳转至登录界面
               if (user == null){
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);

                }else {
                   Intent intent = new Intent(getActivity(), SettingActivity.class);
                   startActivity(intent);
               }
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        user = UserInfo.getUserInfo().getUser();
        if (user!=null){
            Log.i("------------------>", "onHiddenChanged: "+user );
            userName.setText(user.getUserName());
            Glide.with(getContext()).load(IMG_URL_HTTP+user.getImgUrl()).into(circleImageView);
        }else {
            userName.setText("您还没有登录，请登录");
            circleImageView.setImageResource(R.drawable.loginuser);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            user = UserInfo.getUserInfo().getUser();
            if (user!=null){
                userName.setText(user.getUserName());
                Glide.with(getContext()).load(IMG_URL_HTTP+user.getImgUrl()).into(circleImageView);
            }else {
                userName.setText("您还没有登录，请登录");
                circleImageView.setImageResource(R.drawable.loginuser);
            }

        }
    }
}


