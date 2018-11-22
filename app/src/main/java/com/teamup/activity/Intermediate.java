package com.teamup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.teamup.R;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;

public class Intermediate extends BaseActivity {

    TextView loginText,createAccount,loginIn,fbText,forgotText,agreementData,terms;
    Activity context;
    LinearLayout signUp,facebook,parent;
    Handler handler;
    Animation animScale;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intermediate);
        context=this;
        handler=new Handler();
        animScale = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);

        signUp=findViewById(R.id.signUp);
        facebook=findViewById(R.id.facebook);
        parent=findViewById(R.id.parent);
        loginText=findViewById(R.id.login_text);
        fbText=findViewById(R.id.fb_text);
        createAccount=findViewById(R.id.create_account);
        loginIn=findViewById(R.id.sign_up);
        forgotText=findViewById(R.id.forgot_text);
        agreementData=findViewById(R.id.agreement);
        terms=findViewById(R.id.terms);
        forgotText=findViewById(R.id.forgot_text);

        loginText.setTypeface(CommonUtils.setFontTextNormal(context));
        fbText.setTypeface(CommonUtils.setFontTextHeader(context));
        createAccount.setTypeface(CommonUtils.setFontTextHeader(context));
        loginIn.setTypeface(CommonUtils.setFontTextHeader(context));
        forgotText.setTypeface(CommonUtils.setFontMedium(context));
        agreementData.setTypeface(CommonUtils.setFontMedium(context));
        terms.setTypeface(CommonUtils.setFontMedium(context));

        forgotText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MainActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp.setAnimation(animScale);
                signUp.startAnimation(animScale);

                handler.postDelayed(new Runnable() {
                    public void run() {

                            CommonUtils.hideSoftKeyboard(context);
                            startActivity(new Intent(context,PlayerRegistration.class));

                    }

                }, AppConstant.BOUNCING_EFFECT_TIME);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                facebook.setAnimation(animScale);
                facebook.startAnimation(animScale);

                handler.postDelayed(new Runnable() {
                    public void run() {

                        CommonUtils.hideSoftKeyboard(context);

                        //    CommonUtils.simpleSnackBar("Work in progress",parent);
                        callbackManager = MainActivity.loginWithFacebook(Intermediate.this, false, findViewById(R.id.parent));


                    }

                }, AppConstant.BOUNCING_EFFECT_TIME);

            }
        });

//        getResources().getString(R.string.agreement)
/*
       SpannableString ss = new SpannableString(getResources().getString(R.string.agreement));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                CommonUtils.simpleSnackBar("Work in progress",parent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        agreementData.setHighlightColor(Color.TRANSPARENT);
        agreementData.setText(ss);
        agreementData.setMovementMethod(LinkMovementMethod.getInstance());*/


    }


    CallbackManager callbackManager;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

}
