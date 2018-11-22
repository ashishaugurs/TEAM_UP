package com.teamup.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.teamup.R;
import com.teamup.model.Player;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

public class TeamOTP extends BaseActivity {
    EditText zero,one,two,three,four,five;
    Activity context;
    TextView headerText,contentDes,resendCode,confirm;
    Handler handler;
    Animation animScale;
    String TAG = TeamOTP.class.getName();
    String OTP;

    @Override
    protected void onResume() {
        super.onResume();


        registerReceiver(SmsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_otp_verification);
        try{
            setBlankToolbar();
            context=this;
            handler=new Handler();
            animScale = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);
            headerText=findViewById(R.id.textView);
            contentDes=findViewById(R.id.textView2);

            zero=findViewById(R.id.zero);
            one=findViewById(R.id.one);
            two=findViewById(R.id.two);
            three=findViewById(R.id.three);
            four=findViewById(R.id.four);
            five=findViewById(R.id.five);

           // fillOTPFields("123456");

            resendCode=findViewById(R.id.resendCode);
            confirm=findViewById(R.id.confirm);
            contentDes.setTypeface(CommonUtils.setFontTextNormal(context));
            headerText.setTypeface(CommonUtils.setFontTextHeader(context));
            confirm.setTypeface(CommonUtils.setFontTextHeader(context));
            resendCode.setTypeface(CommonUtils.setFontMedium(context));
            headerText.setText("Verify Mobile Number");
            contentDes.setText("We sent you a code\n" + "to verify your mobile number");
            textWatcher();

            final Player player = (Player)getIntent().getSerializableExtra(AppConstant.Player);

            FirebaseUtils.getFirebaseRootRef()
                    .child("otp")
                    .child(player.getPhone())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String otp = dataSnapshot.getValue(String.class);
                            OTP = otp;
                            Log.d(TAG, "onDataChange: "+OTP);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    CommonUtils.hideSoftKeyboard(context);
                    confirm.setAnimation(animScale);
                    confirm.startAnimation(animScale);




                    handler.postDelayed(new Runnable() {
                        public void run() {
                            CommonUtils.hideSoftKeyboard(context);
                                 if(!TextUtils.isEmpty(zero.getText().toString().trim())&&
                                    !TextUtils.isEmpty(one.getText().toString().trim())&&
                                    !TextUtils.isEmpty(two.getText().toString().trim())&&
                                    !TextUtils.isEmpty(three.getText().toString().trim())){


                                     String inputOtp = zero.getText().toString()+
                                             one.getText().toString() +
                                             two.getText().toString() +
                                             three.getText().toString() +
                                             four.getText().toString() +
                                             five.getText().toString();

                                     if(!inputOtp.equals(OTP)){
                                         Snackbar.make(view,"Incorrect OTP",Snackbar.LENGTH_LONG)
                                                 .show();
                                         return;
                                     }

                                     Intent intent = new Intent(context,AddProfile.class);
                                     //send player detail upfront
                                     intent.putExtra(AppConstant.Player, (Player)getIntent().getSerializableExtra(AppConstant.Player));

                                     startActivity(intent);


                            }else{
                              CommonUtils.simpleSnackBar("Please enter correct otp",(LinearLayout) findViewById(R.id.parent));
                              }
                        }
                    }, AppConstant.BOUNCING_EFFECT_TIME);
                }
            });


            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonUtils.simpleSnackBar("Work in progress", (LinearLayout) findViewById(R.id.parent));
                }
            });

            backclick();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void textWatcher() {

        zero.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(zero.getText().toString().length()==1)     //size as per your requirement
                {
                    one.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        one.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(one.getText().toString().length()==1)     //size as per your requirement
                {
                    two.requestFocus();
                }
                else{
                    zero.requestFocus();
                }
                Log.d(TAG, "onTextChanged: ");
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d(TAG, "afterTextChanged: ");
            }

        });
        two.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(two.getText().toString().length()==1)     //size as per your requirement
                {
                    three.requestFocus();
                }else{
                    one.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        three.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(three.getText().toString().length()==1)     //size as per your requirement
                {
                    four.requestFocus();
                }
                else{
                    two.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        four.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(four.getText().toString().length()==1)     //size as per your requirement
                {
                    five.requestFocus();
                }
                else{
                    three.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        five.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(five.getText().toString().length()==1)     //size as per your requirement
                {
                   // CommonUtils.hideSoftKeyboard(context);
                }else{
                    four.requestFocus();
                }

            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub
            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

    }



    public void fillOTPFields(String otp){
        if(!otp.isEmpty()){
            zero.setText(otp.charAt(0)+"" );
            zero.setSelection(1);

            one.setText(otp.charAt(1)+"" );
            one.setSelection(1);

            two.setText(otp.charAt(2)+"" );
            two.setSelection(1);

            three.setText(otp.charAt(3)+"" );
            three.setSelection(1);

            four.setText(otp.charAt(4) +"");
            four.setSelection(1);

            five.setText(otp.charAt(5) +"");
            five.setSelection(1);

        }

    }

    public void getOTP(String otp, String sender){

      //  Toast.makeText(context, otp +" from "+sender, Toast.LENGTH_SHORT).show();
        fillOTPFields(otp);
    }


    public BroadcastReceiver SmsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: from activity");

            Object[] smsObjects = (Object[]) intent.getExtras().get("pdus");
            SmsMessage[] smsMessages = new SmsMessage[smsObjects.length];

            for (int i = 0; i < smsObjects.length; i++) {
                smsMessages[i] = SmsMessage.createFromPdu((byte[]) smsObjects[i]);
                String from = smsMessages[i].getOriginatingAddress();
                String body = smsMessages[i].getMessageBody();
                String code = body.substring(0, body.indexOf(" "));

                Log.d(TAG, "onReceive: " + body);
                Log.d(TAG, "onReceive: code = " + code);
                Log.d(TAG, "onReceive: from = " + from);

                getOTP(code, from);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(SmsReceiver);
    }
}
