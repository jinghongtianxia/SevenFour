package com.example.excitedweather;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Manual,auto
 */
public class MainActivity extends Activity implements View.OnClickListener,BDLocationListener{

    private Button Auto;
    private Button Manual;
    private LocationClient mLocationClient;
    private String City;
    private String content;
    private String city;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Auto = (Button)findViewById(R.id.Auto);
        Manual = (Button)findViewById(R.id.Manual);
        Auto.setOnClickListener(this);
        Manual.setOnClickListener(this);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(this);
        LocationClientOption mLocationClientOption = new LocationClientOption();
        mLocationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClientOption.setIsNeedLocationDescribe(true);
        mLocationClientOption.setIsNeedAddress(true);
        mLocationClient.setLocOption(mLocationClientOption);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Auto:

                StartLocation();
                break;
            case R.id.Manual:
                final View view = View.inflate(this,R.layout.edit,null);
                final AlertDialog alertDialog = new AlertDialog.Builder( this).setView( view).setPositiveButton( "搜索", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText Input = (EditText)view.findViewById(R.id.Input);
                        content = Input.getText().toString();
                        Pattern p1 = Pattern.compile("[a-zA-Z]+");
                        Matcher m1 = p1.matcher(content);
                        Pattern p2 = Pattern.compile("[\u4e00-\u9fa5]+");
                        Matcher m2 = p2.matcher(content);
                        //拼音匹配
                        final Dialog dialog1 = ProgressDialog.StartDialog (MainActivity.this, "搜索中");
                        dialog1.show ();
                        if (m1.matches()){
                            final String PinyinUrl = "http://apis.baidu.com/apistore/weatherservice/weather";
                            final String PinyinArg = "citypinyin="+""+content;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject ( Json.request ( PinyinUrl, PinyinArg ) );
//                                    jsonObject.opt("retData");
                                        String content = jsonObject.getString ( "retData" );
                                        JSONObject jsonObject1 = new JSONObject ( content );
                                        String city = jsonObject1.getString ( "city" );
                                        String pinyin = jsonObject1.getString ( "pinyin" );
                                        String time = jsonObject1.getString ( "time" );
                                        String weather = jsonObject1.getString ( "weather" );
                                        String temp = jsonObject1.getString ( "temp" );
                                        String l_tmp = jsonObject1.getString ( "l_tmp" );
                                        String h_tmp = jsonObject1.getString ( "h_tmp" );
                                        Intent intent = new Intent ( MainActivity.this, CityWeather.class );
                                        intent.putExtra ( "City", city + "市" );
                                        intent.putExtra ( "temp", temp + "°" );
                                        intent.putExtra ( "pinyin", pinyin );
                                        intent.putExtra ( "time", time );
                                        intent.putExtra ( "l_tmp", l_tmp + "°" );
                                        intent.putExtra ( "h_tmp", h_tmp + "°" );
                                        intent.putExtra ( "weather", weather );
                                        startActivity ( intent );
                                    }
                                    catch (JSONException e) {
                                        e.printStackTrace ();
                                        runOnUiThread ( new Runnable () {
                                            @Override
                                            public void run() {
                                                Toast.makeText (MainActivity.this,"输入有误，请重新搜索",Toast.LENGTH_SHORT).show ();
                                            }
                                        } );
                                    }
                                    dialog1.dismiss ();
                                }
                            }).start();
                            //中文匹配
                        }if (m2.matches()){
                            try {
                                String Iso = URLEncoder.encode(content,"UTF-8");
                                final String ZhongWenArg = "cityname=" + "" + Iso;
                                final String ZhongWenUrl = "http://apis.baidu.com/apistore/weatherservice/cityname";
                                new Thread(new Runnable() {
                                    private String weather;
                                    private String pinyin;
                                    private String time;
                                    private String temp;
                                    private String l_tmp;
                                    private String h_tmp;
                                    @Override
                                    public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(Json.request(ZhongWenUrl, ZhongWenArg));
//                                            String content = jsonObject.getString("retData");
//                                            jsonObject.opt("retData");
                                                String content = jsonObject.getString("retData");
                                                JSONObject jsonObject1 = new JSONObject(content);
                                                city = jsonObject1.getString("city");
                                                pinyin = jsonObject1.getString("pinyin");
                                                time = jsonObject1.getString("time");
                                                weather = jsonObject1.getString("weather");
                                                temp = jsonObject1.getString("temp");
                                                l_tmp = jsonObject1.getString("l_tmp");
                                                h_tmp = jsonObject1.getString("h_tmp");
                                                Intent intent = new Intent(MainActivity.this,CityWeather.class);
                                                intent.putExtra("City", city +"市");
                                                intent.putExtra("temp",temp+"°");
                                                intent.putExtra("pinyin",pinyin);
                                                intent.putExtra("time",time);
                                                intent.putExtra("l_tmp",l_tmp+"°");
                                                intent.putExtra("h_tmp",h_tmp+"°");
                                                intent.putExtra("weather",weather);
                                                startActivity(intent);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                runOnUiThread ( new Runnable () {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText (MainActivity.this,"输入有误，请重新搜索",Toast.LENGTH_SHORT).show ();

                                                    }
                                                } );
                                            }
                                        dialog1.dismiss ();
                                    }
                                }).start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else if (!(m1.matches ())&&(!(m2.matches ()))){
                            Toast.makeText (MainActivity.this,"输入有误，请重新搜索",Toast.LENGTH_SHORT).show ();
                            dialog1.dismiss ();
                        }
                        };
                }).setNegativeButton("取消",null).show();
                break;
        }
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        if (bdLocation.getCity().length() == 0){
            StartLocation();
        }else if (bdLocation.getCity().length() != 0){
            City = bdLocation.getCity();
            String Remove = City.substring(0,City.length()-1);
            try {
                String Iso = URLEncoder.encode(Remove,"UTF-8");
                final String httpUrl = "http://apis.baidu.com/apistore/weatherservice/cityname";
                final String httpArg = "cityname=" +""+Iso;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(Json.request(httpUrl,httpArg));
                            jsonObject.opt("retData");
                            String content = jsonObject.getString("retData");
                            JSONObject jsonObject1 = new JSONObject(content);
                            String temp = jsonObject1.getString("temp");
                            String pinyin = jsonObject1.getString("pinyin");
                            String time = jsonObject1.getString("time");
                            String weather = jsonObject1.getString("weather");
                            String l_tmp = jsonObject1.getString("l_tmp");
                            String h_tmp = jsonObject1.getString("h_tmp");
                            Intent intent = new Intent(MainActivity.this,CityWeather.class);
                            intent.putExtra("City",City);
                            intent.putExtra("temp",temp+"°");
                            intent.putExtra("pinyin",pinyin);
                            intent.putExtra("time",time);
                            intent.putExtra("l_tmp",l_tmp+"°");
                            intent.putExtra("h_tmp",h_tmp+"°");
                            intent.putExtra("weather",weather);
                            startActivity(intent);
                            mLocationClient.stop();
                            dialog.dismiss ();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void StartLocation(){
        this.dialog = ProgressDialog.StartDialog (this, "定位中");
        this.dialog.show ();
        mLocationClient.start();
    }

}
