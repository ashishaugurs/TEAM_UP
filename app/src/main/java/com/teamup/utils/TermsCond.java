package com.teamup.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.activity.BaseActivity;

public class TermsCond extends BaseActivity {
    TextView termsData;
    Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.terms);
        termsData=findViewById(R.id.termsData);
        termsData.setTypeface(CommonUtils.setFontTextNormal(context));
      //  setHeadValue("Terms and Conditions",context);
       // setBackText();
        setToolbar("Terms and Conditions");
    }
}
