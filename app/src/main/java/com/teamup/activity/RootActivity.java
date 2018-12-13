package com.teamup.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.teamup.R;
import com.teamup.fragment.HomeTab;
import com.teamup.fragment.NavigationDrawerFragment;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

public class RootActivity extends BaseActivity implements NavigationDrawerFragment.FragmentDrawerListener{
    Toolbar mToolbar;
    DrawerLayout drawerLayout;
    NavigationDrawerFragment drawerFragment;
    public HomeTab carouselFragment;
    public static int i,slidingPos;
    AppCompatImageView imageNotification;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       try{
           context= RootActivity.this;
           setContentView(R.layout.activity_main);
           visibilityOffBack();
           drawerSetUp();
           imageNotification=changeRightIcon(R.drawable.notifications,context);
           slidingPos=0;


           FirebaseUtils.storeTokenToServer();

           if (savedInstanceState == null) {
               // withholding the previously created fragment from being created again
               // On orientation change, it will prevent fragment recreation
               // its necessary to reserving the fragment stack inside each tab
                  initScreen("0");

           } else {
               // restoring the previously created fragment
               // and getting the reference

               try {
                   carouselFragment = (HomeTab) getSupportFragmentManager().getFragments().get(0);
               }catch (Exception e){
                   e.printStackTrace();
               }
           }


           imageNotification.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(slidingPos==0) startActivity(new Intent(context,Notifications.class));
               }
           });


       }catch (Exception e){

       }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initScreen(String value) {
        // Creating the ViewPager container fragment once
        CommonUtils.savePreferencesString(context,"tab",value);
        carouselFragment = new HomeTab();
        final FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.setCustomAnimations(R.anim.slider_from_left, R.anim.slide_to_left);
        fragmentManager.replace(R.id.container, carouselFragment).commit();

    }

    /**
     * Only Activity has this special callback method
     * Fragment doesn't have any onBackPressed callback
     *
     * Logic:
     * Each time when the back button is pressed, this Activity will propagate the call to the
     * container Fragment and that Fragment will propagate the call to its each tab Fragment
     * those Fragments will propagate this method call to their child Fragments and
     * eventually all the propagated calls will get back to this initial method
     *
     * If the container Fragment or any of its Tab Fragments and/or Tab child Fragments couldn't
     * handle the onBackPressed propagated call then this Activity will handle the callback itself
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (drawerFragment.isOpen()) {
            drawerFragment.drawerClose();
        } else {
          //  exitFromApp(context);
        }
        return false;
    }




    public static void exitFromApp(final Activity context) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(R.string.exit_text)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        context.sendBroadcast(broadcastIntent);
                        context.finish();

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


    @Override
    public void onDrawerItemSelected(View view, int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                initScreen("0");
                break;
            case 1:
                startActivity(new Intent(context,MyTeam.class));
                break;
            case 2:
                startActivity(new Intent(context,TeamPay.class));
                break;
            case 3:
                startActivity(new Intent(context,NotificationControl.class));
                break;
            case 4:
                startActivity(new Intent(context,Settings.class));
                break;
            case 5:
                startActivity(new Intent(context,Help.class));
                break;
            case 6:
                CommonUtils.simpleSnackBar("Work in progress..",drawerLayout);
                break;
            case 7:
                break;
        }
    }


    public void setTitleHead(String data){
        mToolbar.setTitle(data);
    }


    protected void drawerSetUp() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("Dashboard");
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerFragment = (NavigationDrawerFragment)getFragmentManager().findFragmentById(R.id.fragment_layout_drawer);
        drawerFragment.setUp(R.id.fragment_layout_drawer, drawerLayout, mToolbar);
        drawerFragment.setDrawerListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
    //finger
}
