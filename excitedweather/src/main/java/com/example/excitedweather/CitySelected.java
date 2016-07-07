package com.example.excitedweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/2.
 */
public class CitySelected extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_selected);
        TextView City_Selected = (TextView)findViewById(R.id.City_Selected);
        Intent intent = getIntent();
        String City = intent.getStringExtra("City");
        City_Selected.setText(City);
    }
}
