package com.teamup.activity;

/**
 * Created by ByungHwa on 8/19/2014.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.teamup.R;
import com.teamup.data.Pager;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class Slider extends BaseActivity {

    private static LoopingViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView btnText,bottomText;
    Activity context;
    private int imageLogo[]=new int[]{R.mipmap.logo_team,R.mipmap.map,R.mipmap.team,R.mipmap.calendar,R.mipmap.mobile};
    private int textDes[]=new int[]{R.string.recreational,R.string.tutorial_2,R.string.tutorial_3,R.string.tutorial_4,R.string.tutorial_5};
    private int headerText[]=new int[]{R.string.teamup,R.string.belocal,R.string.mgmt,R.string.near,R.string.teampayy};

    ArrayList<ModelViewPager> viewPagerData;
    Handler handlerRef;
    Animation animScale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_first);
        try{

            context=this;
            handlerRef=new Handler();
            animScale = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);
            getViews();
            init();

        }catch (Exception e){

        }

    }

    private void getViews() {
        btnText=findViewById(R.id.fb_text);
        bottomText=findViewById(R.id.existing);
        btnText.setTypeface(CommonUtils.setFontTextHeader(context));
        bottomText.setTypeface(CommonUtils.setFontMedium(context));

        CommonUtils.setRobotoMedium(context, btnText, bottomText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPager.resumeAutoScroll();
    }

    @Override
    protected void onPause() {
        mPager.pauseAutoScroll();
        super.onPause();
    }


    private void init() {
        viewPagerData=new ArrayList<>();


        for(int i=0;i<imageLogo.length;i++){
            ModelViewPager pager=new ModelViewPager();
            pager.setDescription(textDes[i]);
            pager.setImage(imageLogo[i]);
            pager.setHeader(headerText[i]);
            viewPagerData.add(pager);
        }


        Pager mAdapter=new Pager(context, viewPagerData,false);
         mPager = (LoopingViewPager) findViewById(R.id.pager);
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        mPager.setAdapter(mAdapter);
        indicator.setViewPager(mPager);
        mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        NUM_PAGES = imageLogo.length;


       /* final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                int incrementedValue=currentPage++;
                mPager.setCurrentItem(incrementedValue, true);
                //changeBullets(R.mipmap.grey_do,incrementedValue);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);*/



        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnText.setAnimation(animScale);
                btnText.startAnimation(animScale);
                handlerRef.postDelayed(new Runnable() {
                    public void run() {

                        startActivity(new Intent(context,Intermediate.class));

                    }

                }, AppConstant.BOUNCING_EFFECT_TIME);


            }
        });

        bottomText.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              startActivity(new Intent(context,MainActivity.class));

                                          }
                                      }
        );

    }
}
