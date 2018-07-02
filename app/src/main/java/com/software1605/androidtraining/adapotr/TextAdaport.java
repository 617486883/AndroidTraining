package com.software1605.androidtraining.adapotr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.entity.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TextAdaport extends ArrayAdapter<Text> {
    String IMG_URL_HTTP="http://wxscjy.free.ngrok.cc/";
    private CircleImageView imageView;
    private TextView title;
    private TextView contennt;
    private int resourcesId;


    public TextAdaport(@NonNull Context context, int textViewResourceId, List<Text> objects) {
        super(context,textViewResourceId,objects);
        resourcesId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Text text = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourcesId,null);
        imageView = view.findViewById(R.id.ivImage);
        title = view.findViewById(R.id.tvTittle);
        contennt = view.findViewById(R.id.tvContent);
        //从网络获取远程图片数据
        Glide.with(getContext()).load(IMG_URL_HTTP+text.getImgUrl()).into(imageView);
        title.setText(text.getTitle());
        contennt.setText(text.getContent());
        return view;
    }
}
