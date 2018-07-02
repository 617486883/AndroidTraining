package com.software1605.androidtraining.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 注册类
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String URL = "http://wxscjy.free.ngrok.cc/user/register";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    private CircleImageView image_choose;
    private EditText passwordEd, nameEdit,userNameEdit;
    private Button register;
    private TextView login;
    Handler handler;
    String imgurl = null;
    File file ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        image_choose = findViewById(R.id.circleImageView);
        passwordEd = findViewById(R.id.password);
        nameEdit = findViewById(R.id.name);
        userNameEdit = findViewById(R.id.userName);
        register = findViewById(R.id.register);
        login = findViewById(R.id.btn_login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
        image_choose.setOnClickListener(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //获取本地相片
            case R.id.circleImageView:
                checkPermission();
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent, 0x1);
                break;
                //点击注册
            case R.id.register:
                String name = nameEdit.getText().toString();
                String password = passwordEd.getText().toString();
                String userName = userNameEdit.getText().toString();
                //校验是否为空
                if (name.equals("") || password.equals("") || userName.equals("")){
                    Toast.makeText(RegisterActivity.this,"清输入正确的格式，不得有空",Toast.LENGTH_SHORT).show();

                }else {
                    if (file == null){
                        Toast.makeText(RegisterActivity.this,"请上传头像",Toast.LENGTH_SHORT).show();
                    }else {

                        Map map = new HashMap();
                        map.put("name",name);
                        map.put("password",password);
                        map.put("userName",userName);
                        String json = JSON.toJSONString(map);
                        handlerManager();
                        OkhttpUtil.postFile(URL,"user",json,file,handler);
                    }


                }

                break;
                //点击已有账号，清登录
            case R.id.btn_login:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x1 && resultCode == RESULT_OK) {
            if (data != null) {
                imgurl = Uri.decode(data.getData().getEncodedPath());
                Log.i("imgurl---------------->",imgurl );
                image_choose.setImageURI(data.getData());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("HandlerLeak")
    public void handlerManager(){
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x001){
                    Bundle bundle = msg.getData();
                   String s = (String) bundle.get("response");
                   Toast.makeText(RegisterActivity.this,s,Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     *
     */

    //保存操作
    private File saveBitmap(){
        Bitmap photo = ((BitmapDrawable)image_choose.getDrawable()).getBitmap();
        File tmDir=new File(Environment.getExternalStorageDirectory()+"/avaterr");
        if(tmDir.exists()){
            tmDir.mkdir();
        }
        File img=new File(tmDir.getAbsolutePath()+"avater.png");

        try {
            FileOutputStream fos = new FileOutputStream(img);
            photo.compress(Bitmap.CompressFormat.PNG,85,fos);
            fos.flush();
            fos.close();
            return img;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //权限要求下存储图片
    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);

        }
    }

}
