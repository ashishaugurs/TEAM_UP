package com.teamup.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.teamup.R;
import com.teamup.model.Player;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

import java.util.Arrays;


public class MainActivity extends BaseActivity {

    ProgressDialog progressDialog;
    FirebaseAuth auth;
    Activity context;
    TextView loginText,fbText,orText,loginBtn,forgotText,signUpText;
    EditText emailText,passwordText;
    LinearLayout loginFb;
    Handler handler;
    Animation animScale;
    LinearLayout parent;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();

        try{
            setContentView(R.layout.login_teamup);
            context=this;
            auth=FirebaseAuth.getInstance();
            animScale = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);
            handler = new Handler();
            loginText=findViewById(R.id.login_text);
            parent=findViewById(R.id.linearParent);
            fbText=findViewById(R.id.fb_text);
            orText=findViewById(R.id.or_text);
            emailText=findViewById(R.id.email_text);
            passwordText=findViewById(R.id.password_text);
            loginBtn=findViewById(R.id.login_in);
            forgotText=findViewById(R.id.forgot_text);
            signUpText=findViewById(R.id.signup_text);
            loginFb=findViewById(R.id.loginFb);


            loginText.setTypeface(CommonUtils.setFontTextHeader(context));
            fbText.setTypeface(CommonUtils.setFontTextHeader(context));
            orText.setTypeface(CommonUtils.setFontTextHeader(context));
            emailText.setTypeface(CommonUtils.setFontTextNormal(context));
            passwordText.setTypeface(CommonUtils.setFontTextNormal(context));
            loginBtn.setTypeface(CommonUtils.setFontTextHeader(context));
            forgotText.setTypeface(CommonUtils.setFontMedium(context));
            signUpText.setTypeface(CommonUtils.setFontTextNormal(context));


            SpannableString ss = new SpannableString(getResources().getString(R.string.donothave));
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(context, Intermediate.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                }
            };
            ss.setSpan(clickableSpan, 22, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            signUpText.setHighlightColor(Color.TRANSPARENT);
            signUpText.setText(ss);
            signUpText.setMovementMethod(LinkMovementMethod.getInstance());

            emailText.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,emailText,R.mipmap.mail_colored,null,R.mipmap.mail));
            passwordText.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,passwordText,R.mipmap.password_colored,null,R.mipmap.password));



            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    loginBtn.setAnimation(animScale);
                    loginBtn.startAnimation(animScale);

                    handler.postDelayed(new Runnable() {
                        public void run() {

                            CommonUtils.hideSoftKeyboard(context);

                            if(checkValidation()){
                                progressDialog=ProgressDialog.show(context,"Submitting data","Please wait..");
                                loginUser();
                            }

                        }

                    }, AppConstant.BOUNCING_EFFECT_TIME);

                }
            });


            loginFb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginFb.setAnimation(animScale);
                    loginFb.startAnimation(animScale);
                    handler.postDelayed(new Runnable() {
                        public void run() {

                            callbackManager = loginWithFacebook(MainActivity.this, true, parent);
                            CommonUtils.hideSoftKeyboard(context);
                            CommonUtils.showToast(context,"Work in progress");

                        }

                    }, AppConstant.BOUNCING_EFFECT_TIME);
                }
            });
            forgotText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context,Forgot.class));

                }
            });

            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CommonUtils.hideSoftKeyboard(context);

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(emailText.getText().toString().trim(), passwordText.getText().toString().trim())
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressDialog.cancel();
                        if (!task.isSuccessful()) {
                            // there was an error
                            CommonUtils.showToast(context,"Auth Failed");
                        } else {
                            CommonUtils.savePreferencesString(context,AppConstant.EMAIL,emailText.getText().toString().trim());
                            CommonUtils.savePreferencesString(context,AppConstant.PASSWORD,passwordText.getText().toString().trim());
                            CommonUtils.savePreferencesBoolean(context,AppConstant.IS_USER_LOGIN,true);
                            Intent intent = new Intent(context, RootActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


    private boolean checkValidation(){

        //
        if (emailText.getText().toString().trim().length() == 0) {
            emailText.setError("Please enter Email id");
            emailText.requestFocus();
            return false;
        } else if (!CommonUtils.isValidEmail(emailText.getText().toString().trim())) {
            emailText.setError("Please enter valid Email id");
            emailText.requestFocus();
            return false;
        }
        else if (passwordText.getText().toString().trim().length() == 0) {
            passwordText.setError("Please enter password");
            passwordText.requestFocus();
            return false;
        }
        else if (passwordText.getText().toString().trim().length() < 5) {
            passwordText.setError("Password should contain at least 6 symbols.");
            passwordText.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }


    public static CallbackManager loginWithFacebook(final Context context,
                                                    final Boolean isLogin,@Nullable final View snackView){

         final String TAG = "FacebookLogin = "+isLogin;
         CallbackManager callbackManager = CallbackManager.Factory.create();

        final ProgressDialog progressDialog = ProgressDialog.show(context, "","Please wait...", true, false);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {

                String userId = loginResult.getAccessToken().getUserId();
//                new GraphRequest(loginResult.getAccessToken(), "/"+userId+"/picture", null, HttpMethod.GET,
//                        new GraphRequest.Callback() {
//                            @Override
//                            public void onCompleted(GraphResponse response) {
//                                Log.d(TAG, "onCompleted: "+response.getRawResponse());
//                            }
//                        })
//                        .executeAsync();

                handleFacebookAccessToken(context,loginResult.getAccessToken(),isLogin, progressDialog, snackView);

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
                Toast.makeText(context, "Login Cancelled", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: facebook = "+error.getMessage());
                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

        LoginManager.getInstance().logInWithReadPermissions((Activity) context,
                Arrays.asList("email","public_profile"));

        return callbackManager;

    }

    static String TAG = "Login";
    private static void handleFacebookAccessToken(final Context context, AccessToken accessToken, final Boolean isLogin,
                                                  final ProgressDialog progressDialog, @Nullable final View snackView){


        FirebaseAuth.getInstance()
                .signInWithCredential(FacebookAuthProvider.getCredential(accessToken.getToken()))
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: facebook login ");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d("facebook", "onComplete: success = "+task.isSuccessful());
                        if(task.isSuccessful()){
                            if(task.getResult()!=null) {
                                CommonUtils.savePreferencesString(context, AppConstant.EMAIL, task.getResult().getUser().getEmail());
                                CommonUtils.savePreferencesBoolean(context, AppConstant.IS_USER_LOGIN, true);
                            }
                            Intent intent;

                            if(!task.getResult().getAdditionalUserInfo().isNewUser()){

                                intent = new Intent(context, RootActivity.class);
                                intent.putExtra(AppConstant.LOGIN_TYPE,"facebook");
                                context.startActivity(intent);
                                ((Activity)context).finish();
                                progressDialog.dismiss();

                            }else{


                                Player player = new Player();
                                player.setFirstname(task.getResult().getUser().getDisplayName());
                                player.setImage_url(String.valueOf(task.getResult().getUser().getPhotoUrl()));
                                player.setEmail(task.getResult().getUser().getEmail());

                                FirebaseUtils.getMyProfileReference()
                                        .setValue(player)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                progressDialog.dismiss();
                                                if(task.isSuccessful()){
                                                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                    context.startActivity(new Intent(context, RootActivity.class));
                                                }

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                            }
                                        });

                            }

                        }
                        else{
                            Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show();
                            Log.d("login", "onComplete: "+task.getException().getMessage());
                        }
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.e("Login", "onFailure: "+e.getMessage() );
                    }
                })
        ;


    }

}
