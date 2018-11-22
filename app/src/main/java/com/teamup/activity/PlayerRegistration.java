package com.teamup.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teamup.R;
import com.teamup.model.Player;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;

public class PlayerRegistration extends BaseActivity {

    EditText firstName,lastName,emailAddress,password;
    Activity context;
    TextView headingValue,confirm;
    AppCompatImageView firstImg,lastImg,mailImg,passImg;
    Handler handler;
    Animation animScale;
    FirebaseAuth authRegistration;
    ProgressDialog dialogProgress;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_profile);
        context=this;


        try{
            findInjection();
            setBlankToolbar();
        }catch (Exception e){
        }
    }

    private void findInjection() {

        authRegistration=FirebaseAuth.getInstance();
        animScale = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);
        handler = new Handler();
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        emailAddress=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirm=findViewById(R.id.confirm);
        headingValue=findViewById(R.id.textView);

        try {
            emailAddress.setText(getIntent().getStringExtra(AppConstant.EMAIL) );
            firstName.setText(getIntent().getStringExtra(AppConstant.NAME).split(" ")[0] );
            lastName.setText(getIntent().getStringExtra(AppConstant.NAME).split(" ")[1] );
        }
        catch (Exception e){
            Log.e("Reg", "findInjection: "+e.getMessage() );
        }

        firstImg=findViewById(R.id.first_umg);
        lastImg=findViewById(R.id.last_name_img);
        mailImg=findViewById(R.id.profile_mail_img);
        passImg=findViewById(R.id.password_img);


        firstName.setTypeface(CommonUtils.setFontTextNormal(context));
        lastName.setTypeface(CommonUtils.setFontTextNormal(context));
        emailAddress.setTypeface(CommonUtils.setFontTextNormal(context));
        password.setTypeface(CommonUtils.setFontTextNormal(context));
    //    confirm.setTypeface(CommonUtils.setFontTextHeader(context));
        headingValue.setTypeface(CommonUtils.setFontTextHeader(context));
        headingValue.setText("Sign up with email");

        firstName.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,firstName,R.mipmap.person_colored,firstImg,R.mipmap.first_name));
        lastName.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,lastName,R.mipmap.person_colored,lastImg,R.mipmap.first_name));
        emailAddress.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,emailAddress,R.mipmap.mail_colored, mailImg,R.mipmap.profile_mail));
        password.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,password,R.mipmap.password_colored,passImg,R.mipmap.password));


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirm.setAnimation(animScale);
                confirm.startAnimation(animScale);

                handler.postDelayed(new Runnable() {
                    public void run() {

                        CommonUtils.hideSoftKeyboard(context);

                        if(checkValidation()){
                            dialogProgress=ProgressDialog.show(context,"Submitting","");
                            createUser();

//                            startActivity(new Intent(context,MobileEntry.class));
                        }


                    }

                }, AppConstant.BOUNCING_EFFECT_TIME);


            }
        });
    }

    private void createUser() {
        authRegistration.createUserWithEmailAndPassword(emailAddress.getText().toString().trim(),
                password.getText().toString().trim())
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Toast.makeText(context, "createUserWithEmail:onComplete:" +
                                task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(context, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {

                            Player player = new Player();
                            player.setFirstname(firstName.getText().toString());
                            player.setLastname(lastName.getText().toString());
                            player.setEmail(emailAddress.getText().toString());

                            Intent intent = new Intent(context, MobileEntry.class);
                            intent.putExtra(AppConstant.Player, player);
                            startActivity(intent);

                            finish();
                        }
                        dialogProgress.cancel();
                    }
                });
    }


    private boolean checkValidation(){

        if(TextUtils.isEmpty(firstName.getText().toString().trim())){
            firstName.setError("Please enter first name");
            firstName.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(lastName.getText().toString().trim()))
        {
            lastName.setError("Please enter last name");
            lastName.requestFocus();
            return false;
        }
        else if (emailAddress.getText().toString().trim().length() == 0) {
            emailAddress.setError("Please enter Email id");
            emailAddress.requestFocus();
            return false;
        } else if (!CommonUtils.isValidEmail(emailAddress.getText().toString().trim())) {
            emailAddress.setError("Please enter valid Email id");
            emailAddress.requestFocus();
            return false;
        }
        else if (password.getText().toString().trim().length() == 0) {
            password.setError("Please enter password");
            password.requestFocus();
            return false;
        }
        else if (password.getText().toString().trim().length() < 5) {
            password.setError("Password should contain at least 6 symbols.");
            password.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }
}
