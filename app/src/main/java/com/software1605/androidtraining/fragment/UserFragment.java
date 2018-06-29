package com.software1605.androidtraining.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.software1605.androidtraining.R;
import com.software1605.androidtraining.activity.LoginActivity;
import com.software1605.androidtraining.activity.MainActivity;
import com.software1605.androidtraining.entity.User;

public class UserFragment extends Fragment {
    User user;
    //登录状态
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_user,container,false);
        ImageView setting = view.findViewById(R.id.setting_btn);
        TextView textView = view.findViewById(R.id.username);
        MainActivity mainActivity = (MainActivity) getActivity();
       user = mainActivity.getUser();
       if (user != null){
            textView.setText(user.getUserName());
       }
    //    Log.i("user---->", "onCreate: "+user);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user == null){
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
        return view;
    }


}
