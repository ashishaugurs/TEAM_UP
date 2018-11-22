package com.teamup.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.activity.MainActivity;
import com.teamup.activity.UserProfile;
import com.teamup.adapter.NavigationDrawerAdapter;
import com.teamup.model.NavDrawerItem;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends android.app.Fragment {

    private static String nameUser="name";
    private static String TAG = NavigationDrawerFragment.class.getSimpleName();
    private static String[] loggedInTitle ;
    private static int[] img = new int[]{R.mipmap.home_black, R.mipmap.team_nav, R.mipmap.credit_card,R.mipmap.notification,R.mipmap.settings, R.mipmap.information};
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView recyclerView;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private FragmentDrawerListener drawerListener;
    private Activity mcontext;

    CircularImageView profileCircularImageView;
    TextView fullName;

    public NavigationDrawerFragment() {

    }

    protected void overridePendingTransitionEnter() {
        CommonUtils.write("entry inside overpending");
        mcontext.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        mcontext.overridePendingTransition(R.anim.slider_from_left, R.anim.slide_to_right);
    }

    public List<NavDrawerItem> getLoginData(String nameUser) {

        List<NavDrawerItem> data = new ArrayList<>();
        // preparing navigation drawer items
        for (int i = 0; i < loggedInTitle.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(loggedInTitle[i]);
            navItem.setPicItem(img[i]);
            data.add(navItem);
        }
        return data;
    }


    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        mcontext=getActivity();
        loggedInTitle = new String[]{getValue(R.string.dashboard),getValue(R.string.myteam),getValue(R.string.team_pay),getValue(R.string.notifications),getValue(R.string.settings),getValue(R.string.help)};
        final View layout = inflater.inflate(R.layout.fragment_layout_drawer, container, false);

        TextView titleName=layout.findViewById(R.id.title_fb);
        TextView subTitle=layout.findViewById(R.id.fb_des);
        titleName.setTypeface(CommonUtils.setFontMedium(mcontext));

        profileCircularImageView = layout.findViewById(R.id.profile_imageView);

        fullName = titleName;
        FirebaseUtils.setMyFullName(fullName);
        FirebaseUtils.setMyProfileImage(profileCircularImageView);

        subTitle.setTypeface(CommonUtils.setFontTextNormal(mcontext));
        LinearLayout userProfile=layout.findViewById(R.id.food_logo);
        LinearLayout bottomLayout=layout.findViewById(R.id.bottomLayout);

        recyclerView = (RecyclerView) layout.findViewById(R.id.common_rec);
        recyclerView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        adapter = new NavigationDrawerAdapter(getActivity(), getLoginData(nameUser));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                drawerListener.onDrawerItemSelected(view, position);
                mDrawerLayout.closeDrawer(containerView);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        bottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(containerView);
//                CommonUtils.simpleSnackBar("Work in progress", containerView);
                logoutFromApp(mcontext);
            }
        });

        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(containerView);
                overridePendingTransitionEnter();
                startActivity(new Intent(mcontext, UserProfile.class));
            }
        });





        return layout;
    }

    public String getValue(int name){

        return getResources().getString(name);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    public static void logoutFromApp(final Activity context) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
        builder.setMessage(R.string.exit_text)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CommonUtils.clearAllData(context);
                        Intent i = new Intent(context, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);
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


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
           containerView = getActivity().findViewById(fragmentId);
           mDrawerLayout = drawerLayout;
           mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
                CommonUtils.hideSoftKeyboard(getActivity());
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                CommonUtils.hideSoftKeyboard(getActivity());
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }

        };


        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void drawerClose() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(containerView);
        }
    }

    public boolean isOpen() {
        return mDrawerLayout.isDrawerOpen(containerView);
    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


    }

}