package com.teamup.adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import com.teamup.fragment.DashBoard;
import com.teamup.fragment.MyListing;
import com.teamup.fragment.Search;
import com.teamup.fragment.TeamChat;
import com.teamup.fragment.TeamEvent;



/**
 * Created by shahabuddin on 6/6/14.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    Activity context;
    /**
     * Create pager adapter
     *  @param resources
     * @param fm
     * @param context
     */
    public ViewPagerAdapter(final Resources resources, FragmentManager fm, Activity context) {
        super(fm);
        this.resources = resources;
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
         Fragment result = null;
        switch (position) {
            case 0:
                    result = new DashBoard();
                break;
            case 1:
                    result = new TeamEvent();
                break;
            case 2:
                    result = new Search();
                break;

            case 3:
                    result = new MyListing();
                break;

            default:
                    result = new TeamChat();
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        return null;
    }

    /**
     * On each Fragment instantiation we are saving the reference of that Fragment in a Map
     * It will help us to retrieve the Fragment by position
     *
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    /**
     * Remove the saved reference from our Map on the Fragment destroy
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }


    /**
     * Get the Fragment by position
     *
     * @param position tab position of the fragment
     * @return
     */
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }



}
