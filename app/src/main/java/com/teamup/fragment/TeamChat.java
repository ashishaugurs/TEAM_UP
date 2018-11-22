package com.teamup.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.activity.SingleChat;
import com.teamup.adapter.MessagAdapter;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;

import java.util.ArrayList;

public class TeamChat extends Fragment implements OnItemClickListener {

    TextView teamTalk,msgsText;
    Activity context;
    View rootView;
    String type="";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.inbox_msg, container, false);
        return rootView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();
        try{
            type="p_chat";
            final TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);

            TabLayout.Tab messageTab = tabLayout.getTabAt(0);
            TabLayout.Tab teamTab = tabLayout.getTabAt(1);


            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if(tabLayout.getSelectedTabPosition() == 0){
                        type="p_chat";
                        valuesInit();
                    }
                    else{

                        type="group_chat";
                        valuesInit();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });




            valuesInit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void valuesInit() {

        RecyclerView common_rec=rootView.findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(getActivity());
        common_rec.setLayoutManager(addLocLayout);
        MessagAdapter adapter = new MessagAdapter(getActivity(),new ArrayList<String>(), type);
        common_rec.setAdapter(adapter);
        common_rec.addOnItemTouchListener(new MessagAdapter(common_rec,this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {

        startActivity(new Intent(context, SingleChat.class));

    }

    @Override
    public void onLongItemClick(View view, int position) {

    }
}
