package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teamup.R;
import com.teamup.adapter.EventDetailsAdapter;

import java.util.ArrayList;

public class LocationSelection extends BaseActivity {
    String type="location";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_selection);
        context=this;
        setHeadValue("Select Location",context);
        valuesInit();
        setBackText();
        // working
    }

    private void valuesInit() {
        type="location";
        RecyclerView common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        EventDetailsAdapter adapter = new EventDetailsAdapter(context,new ArrayList<String>(), type);
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
