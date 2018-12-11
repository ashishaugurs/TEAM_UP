package com.teamup.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.teamup.R;
import com.teamup.adapter.EventDetailsAdapter;
import com.teamup.model.Member;
import com.teamup.model.Player;
import com.teamup.model.Team;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.NDSpinner;

import java.util.ArrayList;


public class CreateTeam extends BaseActivity {

    TextView sportActivity,saveBtn;
    EditText teamName,location;
    NDSpinner genderSelection,ageGroup;
    LinearLayout parentTeam;
    private PlaceAutocompleteFragment placeAutocompleteFragment;
    AppCompatImageView first_umg,dobArrow,bddayImg,genImg,genArrow,sportImg,teamImg;
    String type="event";


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

        if(!TextUtils.isEmpty(CommonUtils.getPreferencesString(context, AppConstant.LIST_VALUE))){
            sportActivity.setText(CommonUtils.getPreferencesString(context,AppConstant.LIST_VALUE));
            sportImg.setImageResource(R.mipmap.soccer_colorful);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_team);
        context=this;
        findIds();
/*        setBackText();
        setHeadValue("Create new team",context);*/
        setToolbar("Create new team");
        setGender();
        setAgeGroup();
        locationFunction();
    }

    private void findIds() {

        teamImg=findViewById(R.id.team_img);
        sportImg=findViewById(R.id.sportImg);
        genArrow=findViewById(R.id.genArrow);
        genImg=findViewById(R.id.genImg);
        dobArrow=findViewById(R.id.ageGImg);
        first_umg=findViewById(R.id.first_umg);
        parentTeam=findViewById(R.id.parentTeam);
        saveBtn=findViewById(R.id.confirm);
        teamName=findViewById(R.id.teamName);
        sportActivity=findViewById(R.id.sportsActivity);
        genderSelection=findViewById(R.id.gender);
        ageGroup=findViewById(R.id.ageGroup);

        bddayImg=findViewById(R.id.dob_img);


        teamName.setTypeface(CommonUtils.setFontTextNormal(context));
        sportActivity.setTypeface(CommonUtils.setFontTextNormal(context));

        saveBtn.setTypeface(CommonUtils.setFontTextHeader(context));
        saveBtn.setText("Save");


        teamName.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,teamName,R.mipmap.team_active,teamImg,R.mipmap.team_name));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()){
                    CommonUtils.hideSoftKeyboard(context);
                  //  CommonUtils.simpleSnackBar("work in progress",parentTeam);

                    Team team = new Team();
                    team.setGender(genderSelection.getSelectedItem().toString());
                    team.setName(teamName.getText().toString());
                    team.setSa(sportActivity.getText().toString());
                    team.setCreatedBy(FirebaseUtils.getUId());
                    team.setAgeGroup(ageGroup.getSelectedItem().toString());
                    team.setCreatedOn(CommonUtils.getDateByTimestamp(System.currentTimeMillis())+" " +
                            CommonUtils.getTimeByCurrentTimestamp(System.currentTimeMillis()));
                    team.setLocation(location.getText().toString());

                    DatabaseReference reference = FirebaseUtils.getTeamNodeRef().push();

                    final String teamID = reference.getKey();


                            reference.setValue(team)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                               
                                    if(task.isSuccessful()) {

                                        //let it commented for a while
                                       // addMeAsPlayer(teamID);
                                      }
                                }
                            });

                }


            }
        });
    }

    private void addMeAsPlayer(final String teamID){

        FirebaseUtils.getMyProfileReference()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Player player = dataSnapshot.getValue(Player.class);

                        Member member = new Member(teamID,
                                player.getPhone(),
                                player.getFirstname(),
                                player.getLastname(),
                                player.getGender(),
                                player.getDescribe(),
                                FirebaseUtils.getUId(),
                                true,
                                true
                                );

                        DatabaseReference reference = FirebaseUtils.getTeamMemberRef(teamID)
                                //this id will be used as team member id
                                .child(FirebaseUtils.getUId());
                        reference.setValue(member)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(context, "Team Successfully Added", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonUtils.savePreferencesString(context, AppConstant.LIST_VALUE,"");
    }

    private boolean checkValidation(){

        if(TextUtils.isEmpty(teamName.getText().toString().trim()))
        {
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please enter  team name",parentTeam);
            return false;

        }else if(TextUtils.isEmpty(sportActivity.getText().toString().trim())){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please choose sport",parentTeam);
            return false;
        }
        else if(genderSelection.getSelectedItemPosition()==0){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select gender.",parentTeam);
            return false;
        }
        else if(ageGroup.getSelectedItemPosition()==0){
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select age group",parentTeam);
            return false;
        }else if(TextUtils.isEmpty(location.getText().toString().trim())){

            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select location",parentTeam);
            return false;
        }
        else {
            return true;
        }
    }


    /* function copy paste*/


    private void locationFunction() {
        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        findViewById(R.id.place_autocomplete_search_button).setVisibility(View.GONE);
        location= findViewById(R.id.place_autocomplete_search_input);
        location.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.text_size_smallest));
        location.setTextColor(getResources().getColor(R.color.text_write));
        location.setHintTextColor(getResources().getColor(R.color.hint_color));

        location.setTypeface(CommonUtils.setFontTextNormal(context));
        location.setHint("Location");
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


        sportActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,SelectSport.class));
            }
        });
    }

/*age group*/


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
                return position != 0;
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
        ageGroup.setAdapter(spinnerArrayAdapter);

        ageGroup.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    dobArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        ageGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView changeTextColor= view.findViewById(android.R.id.text1);

                if(position > 0){
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    bddayImg.setImageResource(R.mipmap.bdday_color);


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


    protected void setGender(){
        String[] plants = new String[]{
                "Gender",
                "Male",
                "Female",
                "Transgender"
        };


        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.simple_spinner_item,plants){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(CommonUtils.setFontTextNormal(context));
                if(position == 0){
                    tv.setTextColor(getResources().getColor(R.color.hint_color));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.text_write));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        genderSelection.setAdapter(spinnerArrayAdapter);

        genderSelection.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    genArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });


        genderSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView changeTextColor= view.findViewById(android.R.id.text1);


                if(position > 0){
                    // Notify the selected item text

                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));

                    if(position==1)
                        genImg.setImageResource(R.mipmap.gender_male);
                    else if(position==2)
                        genImg.setImageResource(R.mipmap.gender_female);
                    else
                        genImg.setImageResource(R.mipmap.gender_colored);

                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                genArrow.setImageResource(R.mipmap.below);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genArrow.setImageResource(R.mipmap.below);
            }


        });
    }

}