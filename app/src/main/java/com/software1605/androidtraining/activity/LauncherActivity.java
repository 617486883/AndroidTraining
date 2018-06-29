package com.software1605.androidtraining.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.software1605.androidtraining.R;

public class LauncherActivity extends Activity {
    Handler handler = new Handler();
    private boolean isStarActivity = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //关闭标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launcher);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                starMainActivity();
            }
        },2000);
    }
    private void starMainActivity() {
        if (!isStarActivity){
            Intent intent = new Intent(LauncherActivity.this,MainActivity.class);
            startActivity(intent);
            isStarActivity = true;
            //关闭当前页
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
