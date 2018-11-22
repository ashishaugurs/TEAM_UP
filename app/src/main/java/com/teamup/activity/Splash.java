package com.teamup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.teamup.R;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.SmsUtils;

public class Splash extends AppCompatActivity {

    FirebaseAuth auth;
    private long SPLASH_TIME_OUT=3000;
    Activity context;
    private Handler handler = new Handler();

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            boolean isUserLogin = CommonUtils.getPreferencesBoolean(context, AppConstant.IS_USER_LOGIN);

            if (auth.getCurrentUser() != null) {
                startActivity(new Intent(context,RootActivity.class));
            }else{
                startActivity(new Intent(context,Slider.class));
            }

            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;


        CommonUtils.printKeyHash(this);

        if(!isGooglePlayServicesAvailable(context)){
            CommonUtils.showToast(context,"Google play services required");
            finish();
        }else{
         auth = FirebaseAuth.getInstance();
         setContentView(R.layout.splash);
         handler.postDelayed(runnable,SPLASH_TIME_OUT);
       }
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }
}
