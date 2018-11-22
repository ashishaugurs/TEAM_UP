package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;

public class AddNewListing extends BaseActivity {

    TextView playerTitle,playerContent,teamTitle,teamContent,teamupTitle,teamupContent;
    LinearLayout teamupParent,teamParent,playerParent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_listing);
        findId();
        setToolbar("Add new listing");
    }

    private void findId() {
        teamParent=findViewById(R.id.team_parent);
        playerParent=findViewById(R.id.player_parent);
        teamupParent=findViewById(R.id.teamup_parent);
        playerTitle=findViewById(R.id.playerTitle);
        playerContent=findViewById(R.id.playerContent);
        teamTitle=findViewById(R.id.team_title);
        teamContent=findViewById(R.id.team_content);
        teamupTitle=findViewById(R.id.teamup_title);
        teamupContent=findViewById(R.id.teamup_content);
        playerTitle.setTypeface(CommonUtils.setFontTextHeader(context));
        teamTitle.setTypeface(CommonUtils.setFontTextHeader(context));
        teamupTitle.setTypeface(CommonUtils.setFontTextHeader(context));
        playerContent.setTypeface(CommonUtils.setFontTextNormal(context));
        teamContent.setTypeface(CommonUtils.setFontTextNormal(context));
        teamupContent.setTypeface(CommonUtils.setFontTextNormal(context));



        teamParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             CommonUtils.savePreferencesString(context, AppConstant.TEAMTYPE,"team");
             startActivity(new Intent(context,IPlayer.class));
            }
        });

        playerParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.savePreferencesString(context, AppConstant.TEAMTYPE,"player");
                startActivity(new Intent(context,IPlayer.class));
            }
        });

        teamupParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtils.savePreferencesString(context, AppConstant.TEAMTYPE,"teamup");
                startActivity(new Intent(context,IPlayer.class));
            }
        });
    }
}
