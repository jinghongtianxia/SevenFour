package com.example.excitedweather;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/7/7.
 */
public class ProgressDialog{

    private static Dialog dialog;

    public static Dialog StartDialog(Context context, String msg){
        View view = View.inflate (context,R.layout.progress,null);
        ImageView im = (ImageView)view.findViewById (R.id.progress);
        im.setImageResource (R.drawable.progress);
        AnimationDrawable animation = (AnimationDrawable)im.getDrawable ();
        animation.start ();
        dialog = new Dialog (context, R.style.My_Dialog);
        dialog.setTitle (msg);
        dialog.setContentView (view);
        return dialog;
    }

    public static void StopDialog(){
        dialog.dismiss ();
    }


}
