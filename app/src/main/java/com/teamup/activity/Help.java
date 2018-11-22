package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.teamup.R;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.TermsCond;

public class Help extends BaseActivity {
    TextView contactUs;
    TextView faqData;
    TextView privacyPolicy;
    TextView termsCondition;
    LinearLayout contact_us_data,faq_data,privacy_policy_data,terms_data,linear_parent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_screen);
        context=this;
        findId();
       // setBackText();
       // visibilityGONEICon(context);
      //  setHeadValue("Help",context);
        setToolbar("Help");
        settingTypeFace();
    }

    private void findId() {
        contactUs=findViewById(R.id.contact_us);
        faqData=findViewById(R.id.faq);
        privacyPolicy=findViewById(R.id.privacy);
        termsCondition=findViewById(R.id.terms);
    }

    private void settingTypeFace() {
        contact_us_data=findViewById(R.id.contact_us_data);
        faq_data=findViewById(R.id.faq_data);
        privacy_policy_data=findViewById(R.id.privacy_policy_data);
        terms_data=findViewById(R.id.terms_data);
        linear_parent=findViewById(R.id.linear_parent);

        contactUs.setTypeface(CommonUtils.setFontTextNormal(context));
        faqData.setTypeface(CommonUtils.setFontTextNormal(context));
        privacyPolicy.setTypeface(CommonUtils.setFontTextNormal(context));
        termsCondition.setTypeface(CommonUtils.setFontTextNormal(context));


        contact_us_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ContactUs.class));
            }
        });
        faq_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.simpleSnackBar("Work in progress",linear_parent);
            }
        });
        privacy_policy_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.simpleSnackBar("Work in progress",linear_parent);
            }
        });
        terms_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, TermsCond.class));
            }
        });

    }
}
