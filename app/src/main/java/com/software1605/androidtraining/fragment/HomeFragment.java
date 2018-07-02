package com.software1605.androidtraining.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.software1605.androidtraining.R;

/**
 * 主页
 */
public class HomeFragment extends Fragment implements LocationSource,AMapLocationListener{
    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    private LatLng mLocalLatlng;
    private boolean isFirst = true;
    MapView mapView;
    AMap aMap = null;
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            View view =  inflater.inflate(R.layout.fragment_home,container,false);
            mapView = view.findViewById(R.id.map);
            mapView.onCreate(savedInstanceState);
            //管理地图方法
            map();
            return view;
        }

        private void map(){

            if (aMap == null){
                aMap = mapView.getMap();
            }
            MyLocationStyle myLocationStyle;
            //初始化定位蓝点位置
            myLocationStyle = new MyLocationStyle();
            //连续定位时间间隔
            myLocationStyle.interval(2000);
              myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER) ;
            //设置定位蓝点
            aMap.setMyLocationStyle(myLocationStyle);
            //设置默认定位按钮
            aMap.getUiSettings().setMyLocationButtonEnabled(true);
            //启动定位蓝点
            aMap.setMyLocationEnabled(true);
            //注册监听
            aMap.setLocationSource(this);

        aMap.setMyLocationEnabled(true);

        myLocationStyle.showMyLocation(true);

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);


    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }


    //地图监听
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null){
            mlocationClient = new AMapLocationClient(getActivity().getApplicationContext());
            mLocationOption = new AMapLocationClientOption();
            mlocationClient.setLocationListener(this);
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();

        }

    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null&&amapLocation != null) {
            if (amapLocation != null
                    &&amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                if(isFirst) {
                    mLocalLatlng = new LatLng(amapLocation.getLatitude(),amapLocation.getLongitude());
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocalLatlng, 16));
                    isFirst = false;
                }
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }
}
