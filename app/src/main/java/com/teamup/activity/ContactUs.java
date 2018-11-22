package com.teamup.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;

import com.teamup.R;
import com.teamup.utils.CommonUtils;

public class ContactUs extends BaseActivity {
    Activity context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.contact_us);
       // setRightText("Send",context);
       // setHeadValue("Contact Us",context);
      //  setBackText();
        setToolbar("Contact Us");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem submit = menu.add("Submit");
        submit.setIcon(R.drawable.ic_send_24_px);
        submit.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            CommonUtils.simpleSnackBar("Work in progress",findViewById(item.getItemId()));

        return super.onOptionsItemSelected(item);
    }
}
