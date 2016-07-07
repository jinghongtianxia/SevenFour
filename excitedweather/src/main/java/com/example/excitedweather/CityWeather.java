package com.example.excitedweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/2.
 */

/**
 *  private String weather;
    private String city;
     private String pinyin;
     private String date;
     private String time;
     private String temp;
     private String l_tmp;
     private String h_tmp;
 *
 *
 */
public class CityWeather extends Activity {

    private View view;
    private static String[] typeDay ={"晴","多云","阴","阵雨","雷阵雨","雷阵雨伴有冰雹","雨夹雪","小雨","中雨","大雨","暴雨","大暴雨","特大暴雨",
            "阵雪","小雪","中雪","大雪","暴雪","雾","冻雨","沙尘暴","小到中雨","中到大雨","大到暴雨","暴雨到大暴雨","大暴雨到特大暴雨",
            "小到中雪","中到大雪","大到暴雪","浮尘","扬沙","强沙尘暴","霾","无"};
    private static String[] typeNight ={"晴","多云","阴","阵雨","雷阵雨","雷阵雨伴有冰雹","雨夹雪","小雨","中雨","大雨","暴雨","大暴雨","特大暴雨",
            "阵雪","小雪","中雪","大雪","暴雪","雾","冻雨","沙尘暴","小到中雨","中到大雨","大到暴雨","暴雨到大暴雨","大暴雨到特大暴雨",
            "小到中雪","中到大雪","大到暴雪","浮尘","扬沙","强沙尘暴","霾"};
    private Map<String,String> mapDay;
    private Map<String,String> mapNight;
    private ImageView picture;
    private String weather;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.show_main,null);
        setContentView(view);
        mapDay = new HashMap<String, String>();
        mapNight = new HashMap<String, String>();
//        View view = View.inflate(this,R.layout.show_main,null);
        LinearLayout linearLayout1 = (LinearLayout) view.findViewById(R.id.line1);
        TextView Weather = (TextView) linearLayout1.findViewById(R.id.Weather);
        Intent intent = getIntent();
        weather = intent.getStringExtra("weather");
        String pinyin = intent.getStringExtra("pinyin");
        time = intent.getStringExtra("time");
        String city = intent.getStringExtra("City");
        String temp = intent.getStringExtra("temp");
        String l_tmp = intent.getStringExtra("l_tmp");
        String h_tmp = intent.getStringExtra("h_tmp");
        TextView City_Name = (TextView)findViewById(R.id.City_Name);
        City_Name.setText(city);
        TextView PinYin = (TextView)findViewById(R.id.PinYin);
        PinYin.setText(pinyin);
        TextView City_Temp = (TextView)findViewById(R.id.City_Temp);
        City_Temp.setText(temp);
//        TextView Weather = (TextView)findViewById(R.id.Weather);
        Weather.setText(weather);
        TextView MinTemp = (TextView)findViewById(R.id.MinTemp);
        MinTemp.setText(l_tmp);
        TextView MaxTemp = (TextView)findViewById(R.id.MaxTemp);
        MaxTemp.setText(h_tmp);
        picture = (ImageView)findViewById(R.id.picture);
        MapResult();
    }

    public void MapResult(){
        try {
            String[] day = getAssets().list("day");
            for (int i=0;i<typeDay.length;i++){
                for (int j=-1;j<i;j++){
                    mapDay.put(typeDay[i],day[j+1]);
                }
            }
            String[] night = getAssets().list("night");
            for (int i=0;i<typeNight.length;i++){
                for (int j=-1;j<i;j++){
                    mapNight.put(typeNight[i],night[j+1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String[] judge = time.split("\\:");
            int dORn = Integer.valueOf(judge[0]);
            if (dORn == 18) {
                InputStream is = getAssets().open("night" + "/" + mapNight.get(weather));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                picture.setImageBitmap(bitmap);
            }else if (dORn != 18) {
                InputStream is = getAssets().open("day" + "/" + mapDay.get(weather));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                picture.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
