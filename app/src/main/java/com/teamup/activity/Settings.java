package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.teamup.R;
import com.teamup.adapter.DetailMenuAdap;
import com.teamup.model.Player;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.NDSpinner;

import java.util.ArrayList;


public class Settings extends BaseActivity {

    RecyclerView common_rec;
    TextView changePassword,changeEmail,openAfter,deactivateAccoount;
    EditText oldPassword, newPassword,confirmPassword,newMail;
    NDSpinner openContent;
    AppCompatImageView oldImage,newImage,confirmImage,emailImage,contentImage,genArrow;

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.write("icon ---------->"+R.mipmap.plus_icon);
      //  setRightText("Save",context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);
        context=this;
        getIds();
      //  setHeadValue("Settings",context);
      //  setBackText();
        setToolbar("Settings");
        listener();

    }

    private void listener() {

        oldPassword.addTextChangedListener(new CommonUtils.CustomTextWatcher(context, oldPassword, R.mipmap.password_colored, oldImage, R.mipmap.password));
        newPassword.addTextChangedListener(new CommonUtils.CustomTextWatcher(context, newPassword, R.mipmap.password_colored, newImage, R.mipmap.password));
        confirmPassword.addTextChangedListener(new CommonUtils.CustomTextWatcher(context, confirmPassword, R.mipmap.password_colored, confirmImage, R.mipmap.password));
        newMail.addTextChangedListener(new CommonUtils.CustomTextWatcher(context, newMail, R.mipmap.mail_colored, emailImage, R.mipmap.mail));
        openLaunchScreen();



    }



    private boolean isValidPassword(){
        if (newPassword.getText().toString().trim().length() == 0) {
            newPassword.setError("Please enter new password");
            newPassword.requestFocus();
            return false;
        }
        else if (newPassword.getText().toString().trim().length() < 5) {
            newPassword.setError("New password should contain at least 6 symbols.");
            newPassword.requestFocus();
            return false;
        }
        else if (confirmPassword.getText().toString().trim().length() == 0) {
            confirmPassword.setError("Please enter confirm password");
            confirmPassword.requestFocus();
            return false;
        }
        else if (confirmPassword.getText().toString().trim().length() < 5) {
            confirmPassword.setError("Confirm password should contain at least 6 symbols.");
            confirmPassword.requestFocus();
            return false;
        }
        else if(!confirmPassword.getText().toString().equalsIgnoreCase(newPassword.getText().toString().trim())){
            confirmPassword.setError("Confirm & New password mismatched.");
            confirmPassword.requestFocus();
            return false;
        }

        return true;

    }

    private boolean isValidEmail(){


            if (newMail.getText().toString().trim().length() == 0) {
             newMail.setError("Please enter Email id");
             newMail.requestFocus();
            return false;
        } else if (!CommonUtils.isValidEmail(newMail.getText().toString().trim())) {
             newMail.setError("Please enter valid Email id");
             newMail.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }


    private void getIds() {

        changePassword=findViewById(R.id.changePassword);
        changeEmail=findViewById(R.id.changeEmail);
        openAfter=findViewById(R.id.open_after);
        deactivateAccoount=findViewById(R.id.deactivate);

        oldPassword=findViewById(R.id.old_pwd);
        newPassword=findViewById(R.id.new_password);
        confirmPassword=findViewById(R.id.confirm_password);
        newMail=findViewById(R.id.new_mail);
        openContent=findViewById(R.id.open_screen);

        oldImage=findViewById(R.id.first_umg);
        newImage=findViewById(R.id.new_password_img);
        confirmImage=findViewById(R.id.confirm_pwd_img);
        emailImage=findViewById(R.id.email_new);
        contentImage=findViewById(R.id.gen_img);
        genArrow=findViewById(R.id.genArrow);


        changePassword.setTypeface(CommonUtils.setFontTextNormal(context));
        changeEmail.setTypeface(CommonUtils.setFontTextNormal(context));
        openAfter.setTypeface(CommonUtils.setFontTextNormal(context));
        oldPassword.setTypeface(CommonUtils.setFontTextNormal(context));
        newPassword.setTypeface(CommonUtils.setFontTextNormal(context));
        confirmPassword.setTypeface(CommonUtils.setFontTextNormal(context));
        newMail.setTypeface(CommonUtils.setFontTextNormal(context));
        deactivateAccoount.setTypeface(CommonUtils.setFontTextHeader(context));




    }


    private void valuesInit() {
        common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        DetailMenuAdap adapter = new DetailMenuAdap(context,new ArrayList<String>(),"myteam");
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    private void openLaunchScreen() {

        String[] plants = new String[]{
                "Dashboard",
                "Events",
                "SearchInner",
                "My Listing",
                "Chat"
        };


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.simple_spinner_item, plants) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return true;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(CommonUtils.setFontTextNormal(context));
                if (position == 0) {
                    // Set the hint text color gray
                    getResources().getColor(R.color.hint_color);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.text_write));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        openContent.setAdapter(spinnerArrayAdapter);

        openContent.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    genArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        openContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                // Notify the selected item text

                    if(position==0)
                        contentImage.setImageResource(R.mipmap.home);
                    else if(position==1)
                        contentImage.setImageResource(R.mipmap.events_color);
                    else if(position==2)
                        contentImage.setImageResource(R.mipmap.search_color);
                    else if(position==3)
                        contentImage.setImageResource(R.mipmap.listing_color);
                    else if(position==4)
                        contentImage.setImageResource(R.mipmap.chat_color);


                genArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        openContent.setSelection(0);

    }




    public void onPasswordUpdateClick(View view) {

        if(!isValidPassword()) {
            return;
        }

        FirebaseUtils.getUser()
                .updatePassword(newPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            new AlertDialog.Builder(Settings.this)
                                    .setMessage("Password updated Successfully")
                                    .setPositiveButton("Ok", null)
                                    .show();
                        }
                        else{
                            new AlertDialog.Builder(Settings.this)
                                    .setMessage(task.getException().getMessage())
                                    .setPositiveButton("Ok", null)
                                    .show();
                        }

                    }
                });


    }

    public void onEmailUpdateClick(View view) {


        if(!isValidEmail()) {
            return;
        }


        FirebaseUtils.getUser()
                .updateEmail(newMail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {

                            FirebaseUtils.getMyProfileReference()
                                    .child(AppConstant.EMAIL)
                                    .setValue(newMail.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            new AlertDialog.Builder(Settings.this)
                                                    .setMessage("Email updated Successfully")
                                                    .setPositiveButton("Ok", null)
                                                    .show();
                                        }
                                    });

                        }
                        else
                            new AlertDialog.Builder(Settings.this)
                                    .setMessage(task.getException().getMessage())
                            .setPositiveButton("Ok", null)
                            .show();
                    }
                });

    }

    public void onLaunchSettingUpdateClick(View view) {

        if(true)
            return;

        FirebaseUtils.getMyProfileReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Player player = dataSnapshot.getValue(Player.class);
                        player.setDeviceId("XXXXXXX-"+System.currentTimeMillis());
                        dataSnapshot.getRef().setValue(player);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}