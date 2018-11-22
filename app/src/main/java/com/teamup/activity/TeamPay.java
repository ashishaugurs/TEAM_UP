package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.utils.CommonUtils;


public class TeamPay extends BaseActivity {

    TextView text_title,text_content,card_number,date_text,card_date,card_digit,confirm;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teampay);
        context=this;
        getIds();
//        setHeadValue("TeamPay",context);
//        setBackText();
        setToolbar("TeamPay");
    }



    private void getIds() {

        text_title=findViewById(R.id.text_title);
        text_content=findViewById(R.id.text_content);
        card_number=findViewById(R.id.card_number);
        date_text=findViewById(R.id.date_text);
        card_date=findViewById(R.id.card_date);
        card_digit=findViewById(R.id.card_digit);
        confirm=findViewById(R.id.confirm);

        text_title.setTypeface(CommonUtils.setFontTextHeader(context));
        text_content.setTypeface(CommonUtils.setFontTextNormal(context));
        card_number.setTypeface(CommonUtils.setFontTextNormal(context));
        date_text.setTypeface(CommonUtils.setFontTextNormal(context));
        card_date.setTypeface(CommonUtils.setFontTextNormal(context));
        card_digit.setTypeface(CommonUtils.setFontTextNormal(context));
        confirm.setTypeface(CommonUtils.setFontTextHeader(context));


    }


}