package com.software1605.androidtraining.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.adapotr.CoordAdaport;
import com.software1605.androidtraining.entity.CoordItem;

import java.util.List;

/**
 * 地标
 */
public class CooreFragment extends Fragment {
    private static final String URL = "http://wxscjy.free.ngrok.cc/coord/findByName";
    private TextView userName,longitude,latitude,name;
    SearchView searchView;
    ListView listView;
    List<CoordItem> list;
    CoordAdaport coordAdaport;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
                try {
                    list = JSON.parseArray(bundle.getString("response"),CoordItem.class);
                    coordAdaport = new CoordAdaport(getContext(),R.layout.coor_listview_item,list);
                    listView.setAdapter(coordAdaport);
                }catch (Exception e){
                    Toast.makeText(getActivity(),"服务器连接失败",Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coore,container,false);
        userName = view.findViewById(R.id.userName);
        longitude = view.findViewById(R.id.longitude_item);
        latitude = view.findViewById(R.id.context_item);
        name = view.findViewById(R.id.title);
        searchView = view.findViewById(R.id.coor_searchview);
        listView = view.findViewById(R.id.coore_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CoordItem coordItem = list.get(i);
                userName.setText(coordItem.getUserName());
                longitude.setText(coordItem.getLongitude()+"");
                latitude.setText(coordItem.getLatitude()+"");
                name.setText(coordItem.getName());
                coordAdaport.clear();
            }

        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                OkhttpUtil.getByType(URL,"name",query,handler);
                coordAdaport.clear();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.equals("")){
                    if (coordAdaport == null){
                        Toast.makeText(getActivity(),"服务器连接失败",Toast.LENGTH_SHORT).show();
                    }else {
                        coordAdaport.clear();
                    }
                }else {
                    OkhttpUtil.getByType(URL,"name",newText,handler);
                }

                return true;
            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            if (coordAdaport!=null){
                coordAdaport.clear();
            }
        }
    }
}
