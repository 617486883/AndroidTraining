package com.software1605.androidtraining.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.software1605.androidtraining.R;
import com.software1605.androidtraining.adapotr.TextAdaport;
import com.software1605.androidtraining.entity.Coord;

import java.util.ArrayList;
import java.util.List;

public class CooreFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coore,container,false);
        List<Coord> list = new ArrayList<>();
        list.add(new Coord(R.drawable.ic_launcher_background,0,0,"这是测试1",0,"aaa"));
        list.add(new Coord(R.drawable.ic_launcher_background,0,0,"这是测试2",0,"aaa"));
        list.add(new Coord(R.drawable.ic_launcher_background,0,0,"这是测试3",0,"aaa"));
        list.add(new Coord(R.drawable.ic_launcher_background,0,0,"这是测试1",0,"aaa"));
        list.add(new Coord(R.drawable.ic_launcher_background,0,0,"这是测试2",0,"aaa"));
        list.add(new Coord(R.drawable.ic_launcher_background,0,0,"这是测试3",0,"aaa"));
        ListView listView = view.findViewById(R.id.listView);
        TextAdaport cooreAdaport = new TextAdaport(getActivity(),R.layout.listview_item,list);
        listView.setAdapter(cooreAdaport);

        return view;
    }
}
