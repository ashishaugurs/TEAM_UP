package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.utils.CommonUtils;

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


        removeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,Attendance.class));
            }
        });
    }
}
