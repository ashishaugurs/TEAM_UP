package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.adapter.EventDetailsAdapter;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;

public class SearchInner extends BaseActivity implements OnItemClickListener{
    RecyclerView common_rec;
    TextView emptyView;
    ArrayList<String> refer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.search_inner_layout);
//        setBackText();
//        setHeadValue("Search",context);
//        setRightText("Filter",context);

        setToolbar("Search");

        valuesInit();
      //  listener();
    }


    private void listener() {

        emptyView=findViewById(R.id.empty_view);
        findViewById(R.id.rightText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // if(!CommonUtils.getPreferencesString(context,AppConstant.LIST_POSITION).equalsIgnoreCase("0"))
                   startActivity(new Intent(context,FilterBy.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void valuesInit() {

        common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        EventDetailsAdapter adapter = new EventDetailsAdapter(context,refer,"unsaved");
        common_rec.addOnItemTouchListener(new EventDetailsAdapter(common_rec,this));
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(View view, int position) {

        CommonUtils.savePreferencesString(context,AppConstant.NAV_TYPE,"search");

        if(CommonUtils.getPreferencesString(context, AppConstant.LIST_POSITION).
                equalsIgnoreCase("0")){
            startActivity(new Intent(context,TeamDetailsMap.class));

        }else if(CommonUtils.getPreferencesString(context, AppConstant.LIST_POSITION).
                equalsIgnoreCase("1")){
            startActivity(new Intent(context,PlayerDetail.class));
        }else{
            startActivity(new Intent(context,PlayerDetail.class));
        }


    }

    @Override
    public void onLongItemClick(View view, int position) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem filterItem = menu.add(Menu.NONE, 1, 1, "Submit");
        filterItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        filterItem.setIcon(R.drawable.filter);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        startActivity(new Intent(context,FilterBy.class));
        return super.onOptionsItemSelected(item);
    }
}
