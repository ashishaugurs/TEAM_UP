package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamup.R;
import com.teamup.adapter.EventDetailsAdapter;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.NDSpinner;

import java.util.ArrayList;


public class FilterBy extends BaseActivity {

    TextView titleName,loc,soccer,anyage,gender,textPlayer,send_msg,invite,
            example_content,search_place,distance,initial_range,mid_range,last_range,listing_type;
    String type="event";
    NDSpinner describe;
    AppCompatImageView desArrow,appBack;



    private void valuesInit() {
        type="dates";
        RecyclerView common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        EventDetailsAdapter adapter = new EventDetailsAdapter(context,new ArrayList<String>(), type);
        common_rec.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

//      TextView apply= setRightText("Apply",context);
//      apply.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//
//          }
//      });

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_by);
        context=this;
        findIds();
      //  setHeadValue("Filter by",context);
        settingDesAdapter();
        Toolbar toolbar = setToolbar("Filter by");
        toolbar.setNavigationIcon(R.drawable.close);
    }


    private void findIds() {
        example_content=findViewById(R.id.example_content);
        search_place=findViewById(R.id.search_place);
        distance=findViewById(R.id.distance);
        initial_range=findViewById(R.id.initial_range);
        mid_range=findViewById(R.id.mid_range);
        last_range=findViewById(R.id.last_range);
        listing_type=findViewById(R.id.listing_type);

        example_content.setTypeface(CommonUtils.setFontMedium(context));
        search_place.setTypeface(CommonUtils.setSFProDisplayRegular(context));
        distance.setTypeface(CommonUtils.setFontMedium(context));
        initial_range.setTypeface(CommonUtils.setFontTextNormal(context));
        mid_range.setTypeface(CommonUtils.setFontTextNormal(context));
        last_range.setTypeface(CommonUtils.setFontTextNormal(context));

        listing_type.setTypeface(CommonUtils.setFontMedium(context));

    }


    private void settingDesAdapter() {

        desArrow=findViewById(R.id.dobArrow);
        describe=findViewById(R.id.gender);
        String[] plants = new String[]{
                "Players Needed?",
                "D1",
                "D2",
                "D3",
                "D4"
        };


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.simple_spinner_item,plants){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(CommonUtils.setFontTextNormal(context));
                if(position == 0){
                    // Set the hint text color gray
                    getResources().getColor(R.color.hint_color);
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.text_write));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        describe.setAdapter(spinnerArrayAdapter);

        describe.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    desArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        describe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text



                }
                desArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem submitItem = menu.add(Menu.NONE, 1, 1, "Submit");
        submitItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        submitItem.setIcon(R.drawable.check);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Snackbar.make(findViewById(R.id.toolbar), "Working on it", Snackbar.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
}