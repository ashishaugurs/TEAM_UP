package com.teamup.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;

import com.teamup.R;
import com.teamup.activity.ModelViewPager;
import com.teamup.utils.CommonUtils;

import java.util.ArrayList;


public class Pager extends LoopingPagerAdapter<ModelViewPager> {

    public Pager(Context context, ArrayList<ModelViewPager> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.view_flipper_content, container, false);
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        TextView logoHeader=convertView.findViewById(R.id.textView);
        TextView desContent=convertView.findViewById(R.id.textView2);
        ImageView logoTeam=convertView.findViewById(R.id.imageView);
        logoHeader.setTypeface(CommonUtils.setFontTextHeader(context));
        desContent.setTypeface(CommonUtils.setFontTextNormal(context));
        logoHeader.setText(itemList.get(listPosition).getHeader());
        desContent.setText(itemList.get(listPosition).getDescription());
        logoTeam.setImageResource(itemList.get(listPosition).getImage());



        CommonUtils.setRobotoRegular(context, desContent);
        logoHeader.setTypeface(CommonUtils.setFontTextHeader(context));
    }
}