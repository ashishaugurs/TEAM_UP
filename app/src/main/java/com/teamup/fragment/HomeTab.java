package com.teamup.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.teamup.R;
import com.teamup.activity.MainActivity;
import com.teamup.activity.RootActivity;
import com.teamup.adapter.ViewPagerAdapter;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.NoSwipeableViewPager;
import java.util.List;


public class HomeTab extends Fragment{

    List<Fragment> mFragmentList;
    NoSwipeableViewPager viewPager;
    ViewPagerAdapter adapter;
    TabLayout tabLayout;
    View layoutView;
    TextView wt_can;
    AutoCompleteTextView search_place;
    Activity context;
    Button submit;
    private FrameLayout flContainer;
    Toolbar mToolbar;
    DrawerLayout drawerLayout;
    NavigationDrawerFragment drawerFragment;
    RootActivity parent;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(R.layout.activity_icon_tabs, container, false);
        return layoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();
        try{
            parent=new RootActivity();
            viewPager = layoutView.findViewById(R.id.viewpager);
            setupViewPager();
            tabLayout = layoutView.findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(viewPager);
            listernerTab(tabLayout,parent);
            setupTabIcons();
            ((RootActivity)context).setTitleHead("Dashboard");
            parent.changeRightIcon(R.drawable.notifications,context);
            RootActivity.slidingPos=0;

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(getTabView(i));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab_icon, null);
        TextView tv = v.findViewById(R.id.text_data);
        tv.setTypeface(CommonUtils.setSfPro(context));
        if(position==0){

            tv.setVisibility(View.VISIBLE);
            tv.setTextColor(ContextCompat.getColor(context,R.color.blue));

        }else{

            tv.setVisibility(View.GONE);
            tv.setTextColor(ContextCompat.getColor(context,R.color.text_write));

        }

        tv.setText(title[position]);

        ImageView img = v.findViewById(R.id.icon_tab);

        if(position==0)
            img.setImageResource(tabsSelected[position]);
        else
            img.setImageResource(tabIcons[position]);

        return v;
    }

    public static String middle(String str)
    {
        int position;
        int length;
        if (str.length() % 2 == 0)
        {
            position = str.length() / 2 - 1;
            length = 2;
        }
        else
        {
            position = str.length() / 2;
            length = 1;
        }
        return str.substring(position, position + length);
    }


    @Override
    public void onResume() {
        super.onResume();

        if(AppConstant.flag){
            viewPager.setCurrentItem(0);
        }


    }


    @Override
    public void onDetach() {
        super.onDetach();
        AppConstant.flag=false;
    }

    private void listernerTab(TabLayout tabLayout, final RootActivity parentActivity) {


        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);

                        View customView = tab.getCustomView();
                        ImageView icon = customView.findViewById(R.id.icon_tab);
                        TextView text = customView.findViewById(R.id.text_data);
                        icon.setImageResource(tabsSelected[tab.getPosition()]);
                        text.setVisibility(View.VISIBLE);
                        text.setTextColor(ContextCompat.getColor(context,R.color.blue));
                        text.setText(title[tab.getPosition()]);
                        RootActivity.slidingPos=tab.getPosition();

                        if(tab.getPosition()==0){
                            ((RootActivity)context).setTitleHead("Dashboard");
                            parentActivity.changeRightIcon(R.drawable.notifications,context);
                        }else if(tab.getPosition()==1){
                            ((RootActivity)context).setTitleHead("Events");
                            parentActivity.visibilityGONEICon(context);
                        }else if(tab.getPosition()==2){
                            ((RootActivity)context).setTitleHead("Search listing");
                            parentActivity.visibilityGONEICon(context);
                        }else if(tab.getPosition()==3){
                            ((RootActivity)context).setTitleHead("My Listings");
                            parentActivity.visibilityGONEICon(context);
                        }else if(tab.getPosition()==4){
                            ((RootActivity)context).setTitleHead("My Team");
                             parentActivity.visibilityGONEICon(context);
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        View customView = tab.getCustomView();
                        ImageView icon = customView.findViewById(R.id.icon_tab);
                        TextView text = customView.findViewById(R.id.text_data);
                        icon.setImageResource(tabIcons[tab.getPosition()]);
                        text.setTextColor(ContextCompat.getColor(context,R.color.text_write));
                        text.setText(title[tab.getPosition()]);
                        text.setVisibility(View.GONE);

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                        View customView = tab.getCustomView();
                        ImageView icon = customView.findViewById(R.id.icon_tab);
                        TextView text = customView.findViewById(R.id.text_data);
                        icon.setImageResource(tabsSelected[tab.getPosition()]);
                        text.setVisibility(View.VISIBLE);
                        text.setTextColor(ContextCompat.getColor(context,R.color.blue));
                        text.setText(title[tab.getPosition()]);

                    }
                }
        );

    }


    int[] tabIcons;
    int[] tabsSelected;
    String [] title;
    private void setupTabIcons() {

        title=new String[]{"Home","Events","Search","Mylisting","Chat"};

        tabIcons = new int[]{
                R.mipmap.home_grey,
                R.mipmap.events,
                R.mipmap.search,
                R.mipmap.listing,
                R.mipmap.chat
        };

        tabsSelected = new int[]{
                R.mipmap.home,
                R.mipmap.events_color,
                R.mipmap.search_color,
                R.mipmap.listing_color,
                R.mipmap.chat_color};


        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }






    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getResources(), getChildFragmentManager(),context);
        viewPager.setAdapter(adapter);
    }

    public void navigateViewPagerToPosition(int position){
        viewPager.setCurrentItem(position);
    }

    protected  void logout() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage("Do you want to logout from app?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        CommonUtils.clearAllData(context);
                        Intent homeIntent = new Intent(getActivity(), MainActivity.class);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        getActivity().finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();
    }


}
