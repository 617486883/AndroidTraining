package com.software1605.androidtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.BottomBar;
import com.software1605.androidtraining.entity.User;
import com.software1605.androidtraining.fragment.CooreFragment;
import com.software1605.androidtraining.fragment.HomeFragment;
import com.software1605.androidtraining.fragment.LikeFragment;
import com.software1605.androidtraining.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {

   private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        Intent intent = getIntent();
        if (intent!=null){
            user = (User) intent.getSerializableExtra("user");
        }
    }

    public User getUser() {

        return user;
    }
}
