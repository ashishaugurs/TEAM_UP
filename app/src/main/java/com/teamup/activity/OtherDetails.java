package com.teamup.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.teamup.R;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

public class  OtherDetails extends BaseActivity {

    TextView accountName,locName,sendMsg,makeAdmin,removeNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accoundt_details);
        context=this;
      //  setBackText();
      //  setHeadValue("Account details",context);
        setToolbar("Account details");
        findIds();
    }

    private void findIds() {
        accountName=findViewById(R.id.earl_paddilla);
        locName=findViewById(R.id.loc);
        sendMsg=findViewById(R.id.soccer);
        makeAdmin=findViewById(R.id.anyage);
        removeNumber=findViewById(R.id.gender);


        accountName.setTypeface(CommonUtils.setFontTextHeader(context));
        locName.setTypeface(CommonUtils.setFontTextNormal(context));
        sendMsg.setTypeface(CommonUtils.setFontTextHeader(context));
        makeAdmin.setTypeface(CommonUtils.setFontTextHeader(context));
        removeNumber.setTypeface(CommonUtils.setFontTextHeader(context));


        makeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String ref = getIntent().getStringExtra(FirebaseUtils.KEY_PASSED_REFERENCE);

                new AlertDialog.Builder(context)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase.getInstance()
                        .getReference(ref)
                        .child("admin")
                        .setValue(true)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Toast.makeText(context, "Added as admin", Toast.LENGTH_SHORT).show();
                            }
                            });

                            }
                        })
                        .setNegativeButton("No", null)
                        .setMessage("Make this member as admin?")
                        .show();
            }
        });

        removeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ref = getIntent().getStringExtra(FirebaseUtils.KEY_PASSED_REFERENCE);

                new AlertDialog.Builder(context)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance()
                                        .getReference(ref)
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    Toast.makeText(context, "Player has been removed from the team", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            }
                                        });

                            }
                        })
                        .setNegativeButton("No", null)
                        .setMessage("Remember this member from the team?")
                        .show();
            }
        });
    }
}
