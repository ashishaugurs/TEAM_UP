package com.teamup.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.teamup.R;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;

public class Forgot extends BaseActivity {
    EditText email_text;
    Activity context;
    TextView headingValue,contentValue,confirmBtn;
    AppCompatImageView firstImg,lastImg,mailImg,passImg;
    Handler handler;
    Animation animScale;
    LinearLayout parent;
    FirebaseAuth authReset;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
       try{
           context=this;
           authReset=FirebaseAuth.getInstance();
           animScale = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);
           handler=new Handler();
           headingValue=findViewById(R.id.textView);
           contentValue=findViewById(R.id.textView2);
           email_text=findViewById(R.id.email_text);
           confirmBtn=findViewById(R.id.confirm);
           parent=findViewById(R.id.parent);
           headingValue.setTypeface(CommonUtils.setFontTextHeader(context));
           contentValue.setTypeface(CommonUtils.setFontTextNormal(context));
           email_text.setTypeface(CommonUtils.setFontTextNormal(context));
           confirmBtn.setTypeface(CommonUtils.setFontTextHeader(context));

           headingValue.setText(getResources().getString(R.string.forgt_pwd));
           contentValue.setText(getResources().getString(R.string.forgot_content));
           email_text.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,email_text,R.mipmap.mail_colored,firstImg,R.mipmap.profile_mail));

           confirmBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   confirmBtn.setAnimation(animScale);
                   confirmBtn.startAnimation(animScale);

                   handler.postDelayed(new Runnable() {
                       public void run() {

                           if(checkValidation()){
                               CommonUtils.hideSoftKeyboard(context);
                             //  CommonUtils.simpleSnackBar("Work in progress",parent);
                               progressDialog=ProgressDialog.show(context,"Submitting data","Please wait..");
                               sentEmail();

                           }

                       }

                   }, AppConstant.BOUNCING_EFFECT_TIME);

               }
           });

           parent.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   CommonUtils.hideSoftKeyboard(context);
               }
           });

         //  backclick();
           setBlankToolbar();

       }catch(Exception e){

       }
    }

    private void sentEmail() {


        authReset.sendPasswordResetEmail(email_text.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                             CommonUtils.simpleSnackBar("We have sent you instructions to reset your password!",parent);
                        } else {
                            CommonUtils.simpleSnackBar("Failed to send reset email!",parent);
                        }

                        progressDialog.cancel();
                    }
                });

    }


    @Override
    protected void onResume() {
        super.onResume();
//        visibilityGONEICon(context);
    }

    private boolean checkValidation(){
        //
        if (email_text.getText().toString().trim().length() == 0) {
            email_text.setError("Please enter Email id");
            email_text.requestFocus();
            return false;
        } else if (!CommonUtils.isValidEmail(email_text.getText().toString().trim())) {
            email_text.setError("Please enter valid Email id");
            email_text.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }


}
