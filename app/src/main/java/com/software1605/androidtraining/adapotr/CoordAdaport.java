package com.software1605.androidtraining.adapotr;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.software1605.androidtraining.R;
import com.software1605.androidtraining.entity.CoordItem;

import java.util.List;

public class CoordAdaport extends ArrayAdapter<CoordItem> {
    private int resourcesId;
    public CoordAdaport(@NonNull Context context, int resource, @NonNull List<CoordItem> objects) {
        super(context, resource, objects);
        resourcesId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CoordItem coordItem = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourcesId,null);
        TextView name = view.findViewById(R.id.name_item);
        name.setText(coordItem.getName());
        return view;
    }
}
