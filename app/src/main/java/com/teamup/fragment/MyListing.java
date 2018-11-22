package com.teamup.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamup.R;
import com.teamup.activity.UnsavedListings;
import com.teamup.adapter.MyListingAdapter;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;

public class MyListing extends Fragment implements OnItemClickListener{

    Activity context;
    View rootView;
    String type="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_mylisting, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();
       try{

           valuesInit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void valuesInit() {
        type="mylisting";
        RecyclerView common_rec=rootView.findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(getActivity());
        common_rec.setLayoutManager(addLocLayout);
        MyListingAdapter adapter = new MyListingAdapter(getActivity(),new ArrayList<String>(), type);
        common_rec.setAdapter(adapter);
        common_rec.addOnItemTouchListener(new MyListingAdapter(common_rec,this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

        CommonUtils.savePreferencesString(context, AppConstant.NAV_TYPE,"listing");

        startActivity(new Intent(context, UnsavedListings.class));
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }
}
