package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import com.teamup.R;
import com.teamup.adapter.CustomAdapter;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.OnItemClickListener;
import java.util.ArrayList;

public class SelectSport extends BaseActivity implements OnItemClickListener {
    ArrayList<String> reference=new ArrayList<String>();
    String type;
    RecyclerView common_rec;
    AutoCompleteTextView search_place;
    CustomAdapter adapter;
    SearchView searchView;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_sport);
        context=this;
      //  setHeadValue("Select Sport/Activity",context);
        search_place=findViewById(R.id.search_place);
        search_place.setTypeface(CommonUtils.setSfPro(context));
        valuesInit();
       // setBackText();
        setToolbar("Select Sport");

        toolbar = findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);


        search_place.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
    }



    private void valuesInit() {

        reference.add("Cricket");
        reference.add("Volley Ball");
        reference.add("Hockey");
        reference.add("Running");
        reference.add("BasketBall");
        reference.add("Polo");
        reference.add("Carom");
        reference.add("Trekking");
        reference.add("Zipline");
        reference.add("Repling");


        type="sport";
        common_rec=findViewById(R.id.common_rec);
        LinearLayoutManager addLocLayout = new LinearLayoutManager(context);
        common_rec.setLayoutManager(addLocLayout);
        common_rec.setNestedScrollingEnabled(false);
        adapter = new CustomAdapter(reference, context);
        common_rec.setAdapter(adapter);
        common_rec.addOnItemTouchListener(new CustomAdapter(common_rec,this));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {


    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void filter(String text) {
        //new array list that will hold the filtered data


        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : reference) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
            adapter.filterList(filterdNames);
            adapter = new CustomAdapter(filterdNames, context);
            common_rec.setAdapter(adapter);
        }
    }


    String TAG = SelectSport.class.getName();
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.sports_menu, menu);

        MenuItem item = menu.findItem(R.id.app_bar_search);
        searchView = (SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {

                
                Log.d(TAG, "onMenuItemActionExpand: ");

                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {


                Log.d(TAG, "onMenuItemActionCollapse: ");

                return false;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return false;
            }
        });

        searchView.setIconifiedByDefault(true);




        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
