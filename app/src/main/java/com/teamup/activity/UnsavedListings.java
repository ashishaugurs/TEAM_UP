package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teamup.R;
import com.teamup.adapter.DetailMenuAdap;
import com.teamup.adapter.EventDetailsAdapter;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;

public class UnsavedListings extends BaseActivity implements OnItemClickListener{

    RecyclerView common_rec;
    AppCompatImageView rightIcon;

    @Override
    protected void onResume() {
        super.onResume();
        CommonUtils.write("icon ---------->"+R.mipmap.plus_icon);
/*        rightIcon= changeRightIcon(R.mipmap.icon_plus,context);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AddNewListing.class));
            }
        });*/
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_team_recycle);
        context=this;
        valuesInit();
    //    setHeadValue("My listings",context);
//        setBackText();
        setToolbar("My listings");

        findViewById(R.id.add_new_listing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AddNewListing.class));
            }
        });

    }



    private void valuesInit() {

        common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        EventDetailsAdapter adapter = new EventDetailsAdapter(context,new ArrayList<String>(),"unsaved");
        common_rec.setAdapter(adapter);
        common_rec.addOnItemTouchListener(new DetailMenuAdap(common_rec,this));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, int position) {

       // startActivity(new Intent(context,AddNewListing.class));

    }

    @Override
    public void onLongItemClick(View view, int position) {

    }
}
