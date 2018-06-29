package com.software1605.androidtraining.adapotr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.software1605.androidtraining.R;
import com.software1605.androidtraining.entity.Coord;

import java.util.List;

public class TextAdaport extends ArrayAdapter<Coord> {

    private int resourcesId;
    public TextAdaport(@NonNull Context context, int textViewResourceId, List<Coord> objects) {
        super(context,textViewResourceId,objects);
        resourcesId = textViewResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Coord coord = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourcesId,null);
        ImageView imageView = view.findViewById(R.id.ivImage);
        TextView textView = view.findViewById(R.id.tvTittle);
        TextView contennt = view.findViewById(R.id.tvContent);
        imageView.setImageResource(coord.getId());
        textView.setText(coord.getName());
        contennt.setText(coord.getName());
        return view;
    }
}
