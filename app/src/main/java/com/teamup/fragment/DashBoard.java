package com.teamup.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.activity.EventDetails;
import com.teamup.activity.LocalEventsNearYou;
import com.teamup.activity.SearchInner;
import com.teamup.adapter.DetailMenuAdap;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;

public class DashBoard extends Fragment implements OnItemClickListener{
    Activity context;
    View rootView;
    String type="";
    TextView upcoming_events,see_all,local_ads,see_ads,local_events,see_all_events;
    String TAG = DashBoard.class.getName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dashboard, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();
        getIds();
        try{
        valuesInit();
        valuesInitHorizontol();
        nearByEvemntsAdap();
        }catch (Exception e){
            e.printStackTrace();
           }

        listeners();
    }

    private void listeners() {

        see_ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                HomeTab homeTab = (HomeTab) getParentFragment();
                homeTab.viewPager.setCurrentItem(1);

            }
        });
        see_all_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LocalEventsNearYou.class));
            }
        });


    }

    private void getIds() {

        upcoming_events=rootView.findViewById(R.id.upcoming_events);
        see_all=rootView.findViewById(R.id.see_all);
        local_ads=rootView.findViewById(R.id.local_ads);
        see_ads=rootView.findViewById(R.id.see_ads);
        local_events=rootView.findViewById(R.id.local_events);
        see_all_events=rootView.findViewById(R.id.see_all_events);

        upcoming_events.setTypeface(CommonUtils.setFontTextNormal(context));
        local_ads.setTypeface(CommonUtils.setFontTextNormal(context));
        local_events.setTypeface(CommonUtils.setFontTextNormal(context));

        see_all_events.setTypeface(CommonUtils.setFontMedium(context));
        see_ads.setTypeface(CommonUtils.setFontMedium(context));
        see_all.setTypeface(CommonUtils.setFontMedium(context));



    }

    private void valuesInitHorizontol() {
        type="ads";
        RecyclerView common_rec=rootView.findViewById(R.id.ads_recycle);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        common_rec.setLayoutManager(addLocLayout);
        DetailMenuAdap adapter = new DetailMenuAdap(getActivity(),new ArrayList<String>(), type);
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void nearByEvemntsAdap() {
        type="nearby";
        RecyclerView common_rec=rootView.findViewById(R.id.nearbyevents);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        common_rec.setLayoutManager(addLocLayout);
        DetailMenuAdap adapter = new DetailMenuAdap(getActivity(),new ArrayList<String>(),type);
        common_rec.setAdapter(adapter);
        common_rec.addOnItemTouchListener(new DetailMenuAdap(common_rec,this));
        adapter.notifyDataSetChanged();
    }


    private void valuesInit() {
        type="dates";
        RecyclerView common_rec=rootView.findViewById(R.id.date_recycle);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(getActivity());
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        DetailMenuAdap adapter = new DetailMenuAdap(getActivity(),new ArrayList<String>(), type);
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(context, EventDetails.class));
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }


    /*register user*/

}
