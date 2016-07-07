package com.example.excitedweather;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by Administrator on 2016/7/1.
 */
public class BaiduLocation implements BDLocationListener {
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        final String City = bdLocation.getCity();
        final String CityLength = City.substring(0,City.length()-1);
        final String Province = bdLocation.getProvince();
        final int Operators = bdLocation.getOperators();
        final String CityCode = bdLocation.getCityCode();
    }
}
