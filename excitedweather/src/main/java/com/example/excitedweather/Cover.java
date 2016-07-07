package com.example.excitedweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/5.
 */
public class Cover extends Activity {

    private static ImageView cover;

    private final long SPLASH_LENGTH = 2000;

    private Handler handler = new Handler ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coverfirst);
        handler.postDelayed ( new Runnable () {
            @Override
            public void run() {
                Intent intent = new Intent ( Cover.this, MainActivity.class );
                startActivity(intent);
                finish ();
            }
        },SPLASH_LENGTH );
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        try {
//            InputStream is = getAssets().open("day/32.png");
//            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            cover.setImageBitmap(bitmap);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        SystemClock.sleep(2000);
//        Intent intent = new Intent(Cover.this, MainActivity.class);
//        startActivity(intent);
//    }
}
