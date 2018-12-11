package com.teamup.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.teamup.R;
import com.teamup.model.Event;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.NDSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewEvent extends BaseActivity {
    NDSpinner eventType,recurring;
    EditText location,notes,opponentSpin;
    TextView dateTime,confirm,delete_event;
    AppCompatImageView eventIcon,eventArrow,dateTimeIcon,dateTimeArrow,
            recurrnigImg,recurringArrow,locationImg,opponentImg,opponentArrow, noteImg;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    CheckBox isMoneyCollected, isSendAttendance;
    String lat="", lng="";

    LinearLayout event_parent;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;

    String TAG = AddNewEvent.class.getName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_event);
        context=this;
//        setHeadValue("Create new event",context);
        //setBackText();

        setToolbar("Create new event");
        findIdSetTypeFace();
        locationFunction();
        functionDP();

        dateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dateTimeArrow.setImageResource(R.mipmap.up_arrow);
            }
        });
    }

    private void findIdSetTypeFace() {

        event_parent=findViewById(R.id.event_parent);

        isMoneyCollected = findViewById(R.id.isMoneyCollected);
        isSendAttendance = findViewById(R.id.isSendAttendance);

        eventType=findViewById(R.id.eventType);
        eventIcon=findViewById(R.id.eventIcon);
        eventArrow=findViewById(R.id.eventArrow);

        dateTimeIcon=findViewById(R.id.gen_img);
        dateTime=findViewById(R.id.sportsActivity);
        dateTimeArrow=findViewById(R.id.genArrow);


        recurrnigImg=findViewById(R.id.recurrnigImg);
        recurringArrow=findViewById(R.id.recurringArrow);
        recurring=findViewById(R.id.recurring);


        locationImg=findViewById(R.id.first_umg);

        opponentImg=findViewById(R.id.dob_img);
        opponentArrow=findViewById(R.id.opponent);
        opponentSpin=findViewById(R.id.opponentSpin);

        notes=findViewById(R.id.note);
        noteImg=findViewById(R.id.note_img);

        confirm=findViewById(R.id.confirm);
        delete_event=findViewById(R.id.delete_event);




        dateTime.setTypeface(CommonUtils.setFontTextNormal(context));
        notes.setTypeface(CommonUtils.setFontTextNormal(context));
        confirm.setTypeface(CommonUtils.setFontTextHeader(context));
        delete_event.setTypeface(CommonUtils.setFontTextHeader(context));
        confirm.setText("Save");


        setRecurring();
        setEventType();


        opponentSpin.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,opponentSpin,R.mipmap.opponent_color,opponentImg,R.mipmap.opponent));
        notes.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,notes,R.mipmap.notes_color,noteImg,R.mipmap.notes));



    }


    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateTime.setText(sdf.format(myCalendar.getTime()));
        dateTimeArrow.setImageResource(R.mipmap.below);
        dateTimeIcon.setImageResource(R.mipmap.clock_color);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void locationFunction() {
        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        ((View)findViewById(R.id.place_autocomplete_search_button)).setVisibility(View.GONE);
        location=((EditText)findViewById(R.id.place_autocomplete_search_input));
        location.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size_smallest));
        location.setTextColor(getResources().getColor(R.color.text_write));
        location.setHintTextColor(getResources().getColor(R.color.hint_color));
        location.setHint("Location");
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build();
        placeAutocompleteFragment.setFilter(autocompleteFilter);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationImg.setImageResource(R.mipmap.event_loc);
                LatLng latLng = place.getLatLng();
                lat = latLng.latitude+"";
                lng = latLng.longitude+"";
                Toast.makeText(getApplicationContext(),place.getName().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();
            }

        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkValidation()){
                    addEvent();
                }

            }
        });
    }


    private boolean checkValidation(){

        if(eventType.getSelectedItemPosition()==0)
        {
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select event",event_parent);
            return false;
        }else if(TextUtils.isEmpty(dateTime.getText().toString().trim())){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select date",event_parent);
            return false;
        }
        else if(recurring.getSelectedItemPosition()==0){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select reccuring event",event_parent);
            return false;
        }
        else if(TextUtils.isEmpty(location.getText().toString().trim())){

            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select location",event_parent);
            return false;
        }
        else if(TextUtils.isEmpty(opponentSpin.getText().toString().trim())){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please enter opponent",event_parent);
            return false;

        }else if(TextUtils.isEmpty(notes.getText().toString().trim())){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please type description",event_parent);
            return false;
        }
        else {
            return true;
        }
    }





    private void setEventType() {


        String[] plants = new String[]{
                "Event type",
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


                    tv.setTextColor(getResources().getColor(R.color.hint_color));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.text_write));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        eventType.setAdapter(spinnerArrayAdapter);

        eventType.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    eventArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        eventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
               TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));

                if(position > 0){
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    eventIcon.setImageResource(R.mipmap.event_type_color);

                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                eventArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }



        });



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

    /*recurring event   */




    private void setRecurring() {
        String[] plants = new String[]{
                "Recurring event?",
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
        recurring.setAdapter(spinnerArrayAdapter);

        recurring.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    recurringArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        recurring.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint


                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));

                if(position > 0){
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    recurrnigImg.setImageResource(R.mipmap.rec_color);

                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }







                recurringArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    /*oppnent*/



    private void addEvent(){

        String teamID = getIntent().getStringExtra(AppConstant.KEY_TEAM_ID);

        DatabaseReference reference = FirebaseUtils.getEventRef(teamID)
                .push();

        String eventID = reference.getKey();

        Event event = new Event(dateTime.getText().toString(),
                notes.getText().toString(),
                isMoneyCollected.isChecked(),
                isSendAttendance.isChecked(),
                lat,
                location.getText().toString(),
                lng,
                opponentSpin.getText().toString(),
                //recurring.getSelectedItem().toString(),
                true,
                eventType.getSelectedItem().toString(),
                FirebaseUtils.getUId(),
                true,
                "",
                false,
                teamID,
                eventID);
       

                reference.setValue(event)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: success");
                            Toast.makeText(context, "Event Added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


}
