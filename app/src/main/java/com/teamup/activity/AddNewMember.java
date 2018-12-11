package com.teamup.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.teamup.R;
import com.teamup.model.Member;
import com.teamup.model.Player;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.NDSpinner;

public class AddNewMember extends BaseActivity {

    AppCompatImageView grp_name;
    EditText firstName;
    EditText lastName;
    EditText callAdd;
    NDSpinner gender,group;
    TextView confirm;
    AppCompatImageView genArrow;
    AppCompatImageView gen_img;
    AppCompatImageView team_img;
    AppCompatImageView lastimg;
    AppCompatImageView first_umg;
    String TAG = AddNewMember.class.getName();
    String uid = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_member);
        context=this;

        setToolbar("Add new member");
        findId();
        settingGenderAdapter();
        setGroup();
        firstName.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,firstName,R.mipmap.person_colored,team_img,R.mipmap.first_name));
        lastName.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,lastName,R.mipmap.person_colored,lastimg,R.mipmap.first_name));
        callAdd.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,callAdd,R.mipmap.phone_active,first_umg,R.mipmap.contact_grey));

        setDeactivated();
    }

    private void findId() {
        grp_name=findViewById(R.id.grp_name);
        team_img=findViewById(R.id.team_img);
        gen_img=findViewById(R.id.gen_img);
        genArrow=findViewById(R.id.genArrow);

        lastimg=findViewById(R.id.lastimg);
        first_umg=findViewById(R.id.first_umg);


        firstName=findViewById(R.id.teamName);
        lastName=findViewById(R.id.lastName);
        gender=findViewById(R.id.gender);
        group=findViewById(R.id.group);
        callAdd=findViewById(R.id.call);
        confirm=findViewById(R.id.confirm);

        firstName.setTypeface(CommonUtils.setFontTextNormal(context));
        lastName.setTypeface(CommonUtils.setFontTextNormal(context));
        callAdd.setTypeface(CommonUtils.setFontTextNormal(context));
        confirm.setTypeface(CommonUtils.setFontTextHeader(context));

        callAdd.addTextChangedListener(textWatcher);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addMember();


            }
        });
    }


    private void addMember(){

        String teamID = getIntent().getStringExtra(AppConstant.KEY_TEAM_ID);

        DatabaseReference reference = FirebaseUtils.getTeamMemberRef(teamID)
                //this id will be used as team member id
                .child(uid);
        

        Member member = new Member(teamID,
                callAdd.getText().toString(),
                firstName.getText().toString(),
                lastName.getText().toString(),
                gender.getSelectedItem().toString(),
                group.getSelectedItem().toString(),
                uid,
                false,
                false);


        if(CommonUtils.isAnyEditTextEmpty(firstName, lastName, callAdd)) {
            return;
        }

        if(gender.getSelectedItem()==null || group.getSelectedItem()==null) {
            Toast.makeText(context, "All Fields required", Toast.LENGTH_SHORT).show();
            return;
        }
        

        
        reference.setValue(member)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   
                        if(task.isSuccessful()) {
                            Toast.makeText(context, "Member added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        
                    }
                });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        //    Log.d(TAG, "beforeTextChanged: "+charSequence.toString());
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
          //  Log.d(TAG, "afterTextChanged: "+editable.toString());


            setDeactivated();



            if(editable.toString().isEmpty()|| editable.toString().length()!=10)
                return;


            String number = editable.toString();


            FirebaseUtils.getFirebaseRootRef()
                    .child(FirebaseUtils.NODE_PLAYER)
                   .orderByChild(FirebaseUtils.KEY_PHONE)
                    .equalTo(number)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           // setDeactivated(true);
                            Log.d(TAG, "onDataChange: count -> "+dataSnapshot.getChildrenCount());

                            Log.d(TAG, "onDataChange: "+dataSnapshot.getValue());
                            Log.d(TAG, "onDataChange: ref = "+dataSnapshot.getRef());
                            if(!dataSnapshot.exists()) {
                                Toast.makeText(context, "No player found", Toast.LENGTH_SHORT).show();
                                return;
                            }



                            for(DataSnapshot post : dataSnapshot.getChildren()){
                                Player player = post.getValue(Player.class);
                                firstName.setText(player.getFirstname() );
                                lastName.setText(player.getLastname() );

                                uid = post.getKey();
                                
                                int t = 0;
                                for(String item: genders) {
                                    if (item.equalsIgnoreCase(player.getGender()))
                                        gender.setSelection(t,true);
                                    t++;
                                }
                                 t = 0;
                                for(String item: groups) {
                                    if (item.equalsIgnoreCase(player.getDescribe()))
                                        group.setSelection(t,true);
                                    t++;
                                }

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
        }
    };




    Boolean isFieldsCurrentlyActive = false;
    private void setDeactivated(){
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        gender.setEnabled(false);
        //group.setEnabled(isActivated);

        emptyFields(firstName, lastName);
        gender.setSelection(-1);
        // group.setSelection(-1);

        isFieldsCurrentlyActive = false;

    }

    private void emptyFields(EditText... editText){
        for(EditText editText1 : editText)
            editText1.setText("" );
    }

    /*Gender Adapter*/

    String[] genders = new String[]{
            "Gender",
            "Male",
            "Female",
            "Transgender"
};


    private void settingGenderAdapter() {



        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.simple_spinner_item,genders){
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
                    tv.setTextColor(getResources().getColor(R.color.hint_color));
                }
                else {
                    tv.setTextColor(getResources().getColor(R.color.text_write));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        gender.setAdapter(spinnerArrayAdapter);

        gender.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    genArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String selectedItemText = (String) parent.getItemAtPosition(position);
                    // If user change the default selection
                    // First item is disable and it is used for hint
                    TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));
                    if(position > 0){
                        // Notify the selected item text

                        changeTextColor.setTextColor(getResources().getColor(R.color.text_write));

                        if(position==1)
                            gen_img.setImageResource(R.mipmap.gender_male);
                        else if(position==2)
                            gen_img.setImageResource(R.mipmap.gender_female);
                        else
                            gen_img.setImageResource(R.mipmap.gender_colored);

                    }else{
                        changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                genArrow.setImageResource(R.mipmap.below);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genArrow.setImageResource(R.mipmap.below);
            }


        });
    }


    /*description adapter*/


    String[] groups = new String[]{
            "Group",
            "California sycamore",
            "Mountain mahogany",
            "Butterfly weed",
            "Carrot weed"
    };

    private void setGroup() {



        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.simple_spinner_item, groups) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                tv.setTypeface(CommonUtils.setFontTextNormal(context));
                if (position == 0) {
                    // Set the hint text color gray
                    getResources().getColor(R.color.hint_color);
                } else {
                    tv.setTextColor(getResources().getColor(R.color.text_write));
                }
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_item);
        group.setAdapter(spinnerArrayAdapter);

        group.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    wydbArrow.setImageResource(R.mipmap.up_arrow);
                    CommonUtils.hideSoftKeyboard(context);
                }
                return false;
            }

        });

       group.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CommonUtils.hideSoftKeyboard(context);

                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));
                if (position > 0) {
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    grp_name.setImageResource(R.mipmap.team_active);

                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
//                wydbArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }




}
