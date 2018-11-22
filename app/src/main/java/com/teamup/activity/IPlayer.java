package com.teamup.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.teamup.R;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IPlayer extends BaseActivity {
    EditText textRef;
    LinearLayout parentPost;
    EditText noteData;
    com.teamup.utils.NDSpinner ageGroupe,teamTypeSpinner,listingSpinner;
    TextView sportsActivity,dateCal;
    LinearLayout listingParent,locationClick,sportSelection,date_event;
    TextView previewAd,postAd;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    AppCompatImageView dobArrow,bdday_img,teamTypeImg,teamTypeDpDwn,gen_img,first_umg,note_img,listing_icon,list_dpdwn,dateImg,dateArrow;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.i_am_player);
        findID();
       // setBackText();


        locationFunction();
        setAgeGroup();
        setTeamType();
        setListingType();
        functionDP();
    }

    private void locationFunction() {
        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ((View)findViewById(R.id.place_autocomplete_search_button)).setVisibility(View.GONE);
        textRef=((EditText)findViewById(R.id.place_autocomplete_search_input));
        textRef.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size_smallest));
        textRef.setTextColor(getResources().getColor(R.color.text_write));
        textRef.setHintTextColor(getResources().getColor(R.color.hint_color));
        textRef.setHint("Location");

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        placeAutocompleteFragment.setFilter(autocompleteFilter);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                first_umg.setImageResource(R.mipmap.event_loc);
                Toast.makeText(getApplicationContext(),place.getName().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();
            }

        });
        sportSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,SelectSport.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(CommonUtils.getPreferencesString(context, AppConstant.TEAMTYPE).equalsIgnoreCase("team")){
//             listingParent.setVisibility(View.VISIBLE);
          //   date_event.setVisibility(View.VISIBLE);
//             setHeadValue("Team",context);
        }else if(CommonUtils.getPreferencesString(context, AppConstant.TEAMTYPE).equalsIgnoreCase("teamup")){
//             setHeadValue("TeamUp",context);
        }else{
//             setHeadValue("I'm player",context);
        }

        if(CommonUtils.getPreferencesString(context,AppConstant.TEAMTYPE).equalsIgnoreCase("team")){
            setToolbar("Team");
            listingParent.setVisibility(View.VISIBLE);
            date_event.setVisibility(View.VISIBLE);
        }
        else if(CommonUtils.getPreferencesString(context, AppConstant.TEAMTYPE).equalsIgnoreCase("teamup"))
            setToolbar("TeamUp");
        else
            setToolbar("I'm a player");


        if(!TextUtils.isEmpty(CommonUtils.getPreferencesString(context,AppConstant.LIST_VALUE))){

            sportsActivity.setText(CommonUtils.getPreferencesString(context,AppConstant.LIST_VALUE));
            gen_img.setImageResource(R.mipmap.soccer_colorful);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        listingParent.setVisibility(View.GONE);
        date_event.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.savePreferencesString(context, AppConstant.LIST_VALUE,"");
    }

    private void findID() {

        date_event=findViewById(R.id.date_event);
        dateImg=findViewById(R.id.dateImg);
        dateCal=findViewById(R.id.dateCal);
        dateArrow=findViewById(R.id.dateArrow);


        list_dpdwn=findViewById(R.id.list_dpdwn);
        listing_icon=findViewById(R.id.listing_icon);
        listingSpinner=findViewById(R.id.listing_spinner);
        parentPost=findViewById(R.id.parentPost);
        noteData=findViewById(R.id.note);
        note_img=findViewById(R.id.note_img);
        first_umg=findViewById(R.id.first_umg);
        gen_img=findViewById(R.id.gen_img);
        teamTypeImg=findViewById(R.id.dob_img);
        teamTypeSpinner=findViewById(R.id.ageGroup);
        teamTypeDpDwn=findViewById(R.id.ageGImg);
        bdday_img=findViewById(R.id.bdday_img);
        dobArrow=findViewById(R.id.dobArrow);
        ageGroupe=findViewById(R.id.gender);
        sportsActivity=findViewById(R.id.sportsActivity);
        sportSelection= findViewById(R.id.genPck);
        postAd=findViewById(R.id.postAd);
        previewAd=findViewById(R.id.previewAd);
        listingParent=findViewById(R.id.listing_type);
        locationClick=findViewById(R.id.locationClick);

        postAd.setTypeface(CommonUtils.setFontTextHeader(context));
        previewAd.setTypeface(CommonUtils.setFontTextHeader(context));

        sportsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,SelectSport.class));
            }
        });

        postAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkValidation()){
                    CommonUtils.simpleSnackBar("Work in progress",parentPost);
                }


            }
        });

        previewAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CommonUtils.getPreferencesString(context, AppConstant.TEAMTYPE).equalsIgnoreCase("team")){
                    startActivity(new Intent(context,PlayerDetail_ExistingListing.class));
                }else if(CommonUtils.getPreferencesString(context, AppConstant.TEAMTYPE).equalsIgnoreCase("teamup")){
                    startActivity(new Intent(context,MyExistingListing.class));
                }else {
                    startActivity(new Intent(context,MyExistingListing.class));
                }

            }
        });


        dateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dobArrow.setImageResource(R.mipmap.up_arrow);
            }
        });

        dateCal.setTypeface(CommonUtils.setFontTextNormal(context));
        noteData.setTypeface(CommonUtils.setFontTextNormal(context));
        noteData.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,noteData,R.mipmap.notes_color,note_img,R.mipmap.notes));
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateCal.setText(sdf.format(myCalendar.getTime()));
        dateArrow.setImageResource(R.mipmap.below);

    }

    private void functionDP() {
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
    }



    private void setAgeGroup() {
        String[] plants = new String[]{
                "Age group",
                "15-20",
                "21-25",
                "26-30",
                "31-35"
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
        ageGroupe.setAdapter(spinnerArrayAdapter);

        ageGroupe.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    dobArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        ageGroupe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));

                if(position > 0){
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    bdday_img.setImageResource(R.mipmap.bdday_color);


                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                dobArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });
    }

    /*team type*/

    private void setTeamType() {

        String[] plants = new String[]{
                "Team type",
                "Team 1",
                "Team 2",
                "Team 3",
                "Team 4"
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
        teamTypeSpinner.setAdapter(spinnerArrayAdapter);

        teamTypeSpinner.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    teamTypeDpDwn.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        teamTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint/
                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));

                if(position > 0){
                    // Notify the selected item text
                    teamTypeImg.setImageResource(R.mipmap.team_active);
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));

                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                teamTypeDpDwn.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private boolean checkValidation(){

        if(TextUtils.isEmpty(sportsActivity.getText().toString().trim()))
        {
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select sport",parentPost);
            return false;
        }else if(ageGroupe.getSelectedItemPosition()==0){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select age group",parentPost);
            return false;
        }else if(teamTypeSpinner.getSelectedItemPosition()==0){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select team type",parentPost);
            return false;
        }else if(TextUtils.isEmpty(textRef.getText().toString().trim())){

            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select location",parentPost);
            return false;
        }else if(TextUtils.isEmpty(noteData.getText().toString().trim())){

            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please type description",parentPost);
            return false;
        }
        else {
            return true;
        }
    }



    /*Listing Type*/

    private void setListingType() {
        String[] plants = new String[]{
                "Listing Type",
                "T",
                "E",
                "A",
                "M",
                "U",
                "P"
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
        listingSpinner.setAdapter(spinnerArrayAdapter);

        listingSpinner.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    list_dpdwn.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        listingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));

                if(position > 0){
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    listing_icon.setImageResource(R.mipmap.team_active);


                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                list_dpdwn.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });
    }


}

