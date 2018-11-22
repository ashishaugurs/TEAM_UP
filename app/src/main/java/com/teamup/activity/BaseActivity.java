package com.teamup.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.utils.CommonUtils;

public class BaseActivity extends AppCompatActivity {
    Activity context;
    AppCompatImageView backPress;
    LinearLayout back_copy;
    TextView backText;

    protected TextView setBackText(){
        backPress=findViewById(R.id.back_press);
        backPress.setVisibility(View.GONE);
        backText=findViewById(R.id.backText);
        back_copy=findViewById(R.id.back_copy);
        backText.setTypeface(CommonUtils.setSfPro(context));
        back_copy.setVisibility(View.VISIBLE);
        back_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return backText;
    }

    protected TextView setRightText(String text,Activity context){
        TextView tvHeader=context.findViewById(R.id.rightText);
        tvHeader.setTypeface(CommonUtils.setSfProBold(context));
        tvHeader.setVisibility(View.VISIBLE);
        tvHeader.setText(text);
        return tvHeader;
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
    }

    public void setHeadValue(String text,Activity context){
        try {
            TextView tvHeader=context.findViewById(R.id.tvHeader);
            tvHeader.setTypeface(CommonUtils.setFontTextHeader(context));
            tvHeader.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AppCompatImageView changeRightIcon(int imageName, Activity context){
         AppCompatImageView imageView=context.findViewById(R.id.notification);
         imageView.setVisibility(View.VISIBLE);
         imageView.setImageResource(imageName);
         return imageView;
    }

    public void visibilityGONEICon(Activity context){
        AppCompatImageView imageView=context.findViewById(R.id.notification);
        imageView.setVisibility(View.INVISIBLE);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }


    protected void backclick(){
        back_copy=findViewById(R.id.back_copy);
        back_copy.setVisibility(View.GONE);
        backPress =findViewById(R.id.back_press);
        backPress.setVisibility(View.VISIBLE);
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(getSupportActionBar()!=null)
            setBlankToolbar();
    }


    protected void setBlankToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getSupportActionBar()!=null)
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        else
            toolbar.setTitle("");


    }


    protected Toolbar setToolbar(String title){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(getSupportActionBar()!=null)
          getSupportActionBar().setTitle(title);
        else
            toolbar.setTitle(title);

           toolbar.setTitleTextColor(Color.WHITE);

           return toolbar;
    }
    protected void visibilityOffBack(){
        backPress =findViewById(R.id.back_press);
        backPress.setVisibility(View.INVISIBLE);
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slider_from_left, R.anim.slide_to_right);
    }

}
