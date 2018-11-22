package com.teamup.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;
import com.teamup.R;

public class SmsUtils {

  static String TAG = SmsUtils.class.getName();



    public static void sendOTP(Context context, String phoneNumber){


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                (Activity) context,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted: ");
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.d(TAG, "onVerificationFailed: "+e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        Log.d(TAG, "onCodeSent: ");

                        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential("", "");

                        //authCredential.
                        super.onCodeSent(s, forceResendingToken);
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                    }
                });        // OnVerificationStateChangedCallbacks

    }

    public static String getCountryCode(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.getSimCountryIso();

        String locale = null;
        if (telephonyManager.getNetworkCountryIso() != null
                && !telephonyManager.getNetworkCountryIso().toString().equals(""))
        {
            locale = telephonyManager.getNetworkCountryIso().toString();
        }


        String[] codes = context.getResources().getStringArray(R.array.CountryCodes);

        String code = "";

        for(String item : codes){
            //  Log.d(TAG, "sendOTP: "+item);
            if(item.toLowerCase().contains(locale)){
                code = item.substring(0,item.indexOf(","));
                break;
            }
        }

        return code;
    }
}
