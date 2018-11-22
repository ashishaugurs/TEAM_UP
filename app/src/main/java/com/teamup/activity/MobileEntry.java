package com.teamup.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.teamup.R;
import com.teamup.model.Player;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.SmsUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MobileEntry extends BaseActivity {
    EditText inputMobile;
    Activity context;
    TextView headerText,contentDes;
    LinearLayout team_mobile;
    String TAG = MobileEntry.class.getName();

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
           setContentView(R.layout.mobile_verification);
           setBlankToolbar();
           context=this;
           findId();
           contentDes.setTypeface(CommonUtils.setFontTextNormal(context));
           headerText.setTypeface(CommonUtils.setFontTextHeader(context));
           inputMobile.setTypeface(CommonUtils.setFontTextNormal(context));
           inputMobile.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,inputMobile,R.mipmap.active_side,null,R.mipmap.side_mobile));
           headerText.setText("Verification");
           contentDes.setText("We'll text you a confirmation code to\nsecure your account.");
           inputMobile.requestFocus();
           inputMobile.addTextChangedListener(new TextWatcher() {
               @Override
               public void onTextChanged(CharSequence s, int start, int before, int count) {



               }

               @Override
               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                   // dataset change UI completion
               }

               @Override
               public void afterTextChanged(Editable s) {

               }
           });

           team_mobile.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   CommonUtils.hideSoftKeyboard(context);
               }
           });
           backclick();
       }catch (Exception e){

       }

       findViewById(R.id.confirm)
               .setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       if(inputMobile.getText().length()<10){
                        return;
                       }


                       sendOTP( inputMobile.getText().toString());


                   }
               });
    }



    public void sendOTP(final String phoneNumber){

        final ProgressDialog dialog = ProgressDialog.show(this,"","Sending OTP. Please wait...",true,false);

        Log.d(TAG, "sendOTP: ");

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Log.d(TAG, "onVerificationCompleted: "+phoneAuthCredential.getSmsCode());

                        Map<String,String> phone = new HashMap<>();
                        phone.put(phoneNumber, phoneAuthCredential.getSmsCode());

                        FirebaseUtils.getFirebaseRootRef()
                                .child("otp")
                                .setValue(phone)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Log.d(TAG, "onComplete: success "+task.isSuccessful());
                                        dialog.dismiss();
                                    }
                                })
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Intent intent = new Intent(MobileEntry.this,TeamOTP.class);
                                        //send player detail upfront
                                    dialog.dismiss();
                                    Player player = (Player) getIntent().getSerializableExtra(AppConstant.Player);
                                    player.setPhone(inputMobile.getText().toString());
                                         intent.putExtra(AppConstant.Player, player);

                                              startActivity(intent);
                                    }
                                });
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Log.d(TAG, "onVerificationFailed: "+e.getMessage());
                        e.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(context, "Failed to send OTP", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                        PhoneAuthCredential authCredential = PhoneAuthProvider.getCredential(s, "543245");

                        Log.d(TAG, "onCodeSent: " + authCredential.getSmsCode());
                        Log.d(TAG, "onCodeSent: token = "+s);
                        String code = s.replaceAll("[A-Za-z]", "");

                        //authCredential.

                        Log.d(TAG, "onCodeSent: code fetched = "+code);


                        super.onCodeSent(s, forceResendingToken);
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                    }
                });        // OnVerificationStateChangedCallbacks

    }

    private void findId() {
        inputMobile=findViewById(R.id.email_text);
        headerText=findViewById(R.id.textView);
        contentDes=findViewById(R.id.textView2);
        team_mobile=findViewById(R.id.team_mobile);
    }

 }
