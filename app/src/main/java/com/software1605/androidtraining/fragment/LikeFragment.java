package com.software1605.androidtraining.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.software1605.androidtraining.R;
import com.software1605.androidtraining.Uitl.OkhttpUtil;
import com.software1605.androidtraining.adapotr.TextAdaport;
import com.software1605.androidtraining.entity.Text;

import java.util.List;

/**
 * 留言
 */
public class LikeFragment extends Fragment {
    private static final String URL_FIND_BY_TITLE="http://wxscjy.free.ngrok.cc/text/findTextByTextTitle";
    private static final String URL_FIND_ALL = "http://wxscjy.free.ngrok.cc/text/findAll";
    private ListView listView;
    private List<Text> list;
    SearchView searchView;


    //获取数据
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                Bundle bundle = msg.getData();
                String resp = (String) bundle.get("response");
                try {
                    list = JSON.parseArray(resp,Text.class);
                    TextAdaport cooreAdaport = new TextAdaport(getActivity(),R.layout.listview_item,list);
                    listView.setAdapter(cooreAdaport);
                }catch (Exception e){
                    Toast.makeText(getActivity(),"网络错误",Toast.LENGTH_SHORT).show();
                }

                Log.i("--------------->", "handleMessage:  LikeFragment"+list);
            }

        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_like,container,false);
        if (list == null){
            OkhttpUtil.postByJson(URL_FIND_ALL,"",handler);
        }
        listView = view.findViewById(R.id.listView);
        searchView=view.findViewById(R.id.searchview);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s.equals("")){
                    Toast.makeText(getActivity(),"请输入标题",Toast.LENGTH_SHORT).show();
                }else {
                    OkhttpUtil.getByType(URL_FIND_BY_TITLE,"title",s,handler);
                    if(list.size() == 0){
                        Toast.makeText(getActivity(),"没有该条消息",Toast.LENGTH_SHORT).show();
                    }
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            OkhttpUtil.postByJson(URL_FIND_ALL,"",handler);
        }
    }
}

