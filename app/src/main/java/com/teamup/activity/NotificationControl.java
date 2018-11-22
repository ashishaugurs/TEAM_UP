package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Switch;
import android.widget.TextView;
import com.teamup.R;
import com.teamup.utils.CommonUtils;


public class NotificationControl extends BaseActivity {

    Switch pushNotification,textNotification,emailNotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_setting);
        context=this;
        findId();
      //  setBackText();
        setToolbar("Notifications");

    //    visibilityGONEICon(context);
//        setHeadValue("Notifications",context);
        settingTypeFace();
    }

    private void findId() {
        pushNotification=findViewById(R.id.push_switch);
        textNotification=findViewById(R.id.text_switch);
        emailNotification=findViewById(R.id.email_switch);
    }

    private void settingTypeFace() {
        pushNotification.setTypeface(CommonUtils.setFontTextNormal(context));
        textNotification.setTypeface(CommonUtils.setFontTextNormal(context));
        emailNotification.setTypeface(CommonUtils.setFontTextNormal(context));
    }
}