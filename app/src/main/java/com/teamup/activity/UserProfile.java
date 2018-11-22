package com.teamup.activity;

import  android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;
import com.teamup.R;
import com.teamup.model.Player;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;
import com.teamup.utils.NDSpinner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class UserProfile extends BaseActivity {

    CircularImageView userProfilePic;
    public static final int MY_PERMISSIONS_REQUEST_WRITE_READ=1000;
    public static final int GALLERY_INTENT_CALLED = 2224;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 500;
    public static final int CUSTOM_REQUEST_CODE = 390;
    File imgFile;
    String pathImage=null;
    EditText firstName,lastName,phoneNumber;
    TextView dob,changePicture;
    AppCompatImageView first_umg,last_name_img,gen_img,dob_img,wydb_img,call_img,genArrow,dobArrow,wydbArrow;
    NDSpinner gender, wydb_text;
    LinearLayout detailsSpin,datePck;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Player player=new Player();
    private String TAG = UserProfile.class.getName();


    String[] genders = new String[]{
            "Gender",
            "Male",
            "Female",
            "Transgender"
    };

    String[] des = new String[]{
            "What best describes you?",
            "California sycamore",
            "Mountain mahogany",
            "Butterfly weed",
            "Carrot weed"
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_user);
        context=this;
        findIds();
        setToolbar("Account Details");
        settingTypeFace();
        listener();
        functionDP();
        settingGenderAdapter();
        settingDesAdapter();

        loadUserDetails();

    }
    ProgressDialog progressDialog;
    private void loadUserDetails(){

        progressDialog = ProgressDialog.show(this, "", "Please wait...");
        progressDialog.setCancelable(false);

        FirebaseUtils.getMyProfileReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        progressDialog.dismiss();
                        if(!dataSnapshot.exists()) {
                            Toast.makeText(context, "failed to load data...", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d(TAG, "onDataChange: "+dataSnapshot.getValue());

                        try {
                            Player player = dataSnapshot.getValue(Player.class);
                            firstName.setText(player.getFirstname());
                            lastName.setText(player.getLastname());
                            dob.setText(player.getDob());

                            Log.d(TAG, "onDataChange: url ="+player.getImageUrl());


                            int t;
                            t = 0;
                            for(String item:genders)
                            {

                                if(item.equals(player.getGender())){
                                    gender.setSelection(t);
                                    break;
                                }

                                t++;

                            }
                            Log.d(TAG, "onDataChange: after setting gender spinner");

                            t = 0;
                            for(String item:des)
                            {

                                if(item.equals(player.getDescribe())){
                                    wydb_text.setSelection(t);
                                    break;
                                }

                                t++;
                            }

                            Log.d(TAG, "onDataChange: after setting describe spinner");

                            if(!player.getImageUrl().isEmpty())
                                Picasso.get()
                                        .load(player.getImageUrl())
                                        .placeholder(R.mipmap.place_holder)
                                        .into(userProfilePic);

                            UserProfile.this.player = player;
                            phoneNumber.setText(player.getPhone());

                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    String profileUrl = "";

    private void insertDetails(){

        progressDialog.show();

        player.setFirstname(firstName.getText().toString());
        player.setLastname(lastName.getText().toString());

        if(pathImage != null)
        player.setImageUrl(profileUrl);


        player.setGender(gender.getSelectedItem().toString());
        player.setDescribe(wydb_text.getSelectedItem().toString());
        player.setDob(dob.getText().toString());
        player.setPhone(phoneNumber.getText().toString());


        FirebaseUtils.getMyProfileReference()
                .setValue(player)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            pathImage = null;
                            profileUrl = null;
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                    }
                });



    }

    private void uploadImageThenInsert(){

        progressDialog.show();

        final StorageReference ref = FirebaseStorage.getInstance().getReference()
                .child(FirebaseUtils.getUId());

        ref.putFile(Uri.fromFile(new File(pathImage)))
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onProgress: "+taskSnapshot.getBytesTransferred());
                    }
                })
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d(TAG, "onSuccess: url = "+uri.toString());
                        profileUrl = uri.toString();
                        //   progressDialog.dismiss();
                        insertDetails();
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        profileUrl = "";
                        insertDetails();
                        //    progressDialog.dismiss();
                        Toast.makeText(context, "Failed to upload : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void listener() {

        firstName.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,firstName,R.mipmap.person_colored,first_umg,R.mipmap.first_name));
        lastName.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,lastName,R.mipmap.person_colored,last_name_img,R.mipmap.first_name));
        phoneNumber.addTextChangedListener(new CommonUtils.CustomTextWatcher(context,phoneNumber,R.mipmap.phone_active, call_img,R.mipmap.contact_grey));

       /* findViewById(R.id.rightText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()){

                    CommonUtils.hideSoftKeyboard(context);
                    CommonUtils.simpleSnackBar("Work in progress",detailsSpin);


                }
            }
        });*/
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


        if(pathImage == null)
            insertDetails();
        else
            uploadImageThenInsert();

        return super.onOptionsItemSelected(item);
    }

    private boolean checkValidation(){

        if(TextUtils.isEmpty(firstName.getText().toString().trim()))
        {
            firstName.setError("Please enter first name.");
            firstName.requestFocus();
            return false;
        }else if(TextUtils.isEmpty(lastName.getText().toString().trim())){
            lastName.setError("Please enter last name.");
            lastName.requestFocus();
            return false;
        }else {
            return true;
        }
    }

    private void findIds()
    {
        detailsSpin=findViewById(R.id.detailsSpin);
        userProfilePic=findViewById(R.id.userProfilePic);
        datePck=findViewById(R.id.datePck);
        changePicture=findViewById(R.id.changePicture);
        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        phoneNumber=findViewById(R.id.call);

        gender=findViewById(R.id.gender);
        dob=findViewById(R.id.dob);
        wydb_text=findViewById(R.id.wydb_text);


        first_umg=findViewById(R.id.first_umg);
        last_name_img=findViewById(R.id.last_name_img);
        gen_img=findViewById(R.id.gen_img);
        dob_img=findViewById(R.id.dob_img);
        wydb_img=findViewById(R.id.wydb_img);
        call_img=findViewById(R.id.call_img);

        genArrow=findViewById(R.id.genArrow);
        wydbArrow=findViewById(R.id.wydbArrow);
        dobArrow=findViewById(R.id.dobArrow);


    }


    private void settingTypeFace() {
        firstName.setTypeface(CommonUtils.setFontTextNormal(context));
        lastName.setTypeface(CommonUtils.setFontTextNormal(context));
        phoneNumber.setTypeface(CommonUtils.setFontTextNormal(context));
        dob.setTypeface(CommonUtils.setFontTextNormal(context));
        changePicture.setTypeface(CommonUtils.setFontMedium(context));
    }

    // adapters

    private void settingGenderAdapter() {

        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    permissionsAsk();
                }else{
                    permissionsAsk();
                }

            }
        });


        datePck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                dobArrow.setImageResource(R.mipmap.up_arrow);
            }
        });


        detailsSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dobArrow.setImageResource(R.mipmap.below);
                wydbArrow.setImageResource(R.mipmap.below);
                dobArrow.setImageResource(R.mipmap.below);
                CommonUtils.hideSoftKeyboard(context);
            }
        });

        dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(dob.getText().length()!=0){
                    dob_img.setImageResource(R.mipmap.bdday_color);
                } else{
                    dob_img.setImageResource(R.mipmap.birthday);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });



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
                genArrow.setImageResource(R.mipmap.below);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genArrow.setImageResource(R.mipmap.below);
            }


        });
    }

    private void settingDesAdapter() {




        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.simple_spinner_item, des) {
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
        wydb_text.setAdapter(spinnerArrayAdapter);

        wydb_text.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    wydbArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });

        wydb_text.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));
                if (position > 0) {
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    wydb_img.setImageResource(R.mipmap.soccer_colorful);

                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                wydbArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //camera permission

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void permissionsAsk() {
        String[] PERMISSIONS = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        if(!hasPermissions(context, PERMISSIONS))
        {
            requestPermissions(PERMISSIONS, MY_PERMISSIONS_REQUEST_WRITE_READ);
        }else{
            try{
                ShowDialogForChoose();
            }catch (Exception e){

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_READ:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try{
                        ShowDialogForChoose();
                    }catch (Exception e){

                    }
                } else {
                    //  CommonUtils.simpleSnackBar("Can't access files", (FrameLayout) layoutView.findViewById(R.id.main_frame));
                }
                break;
        }
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(myCalendar.getTime()));
        dobArrow.setImageResource(R.mipmap.below);

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

// camera code

    protected void ShowDialogForChoose() {

        ArrayAdapter<String> adapt = new ArrayAdapter<String>(context,
                android.R.layout.simple_list_item_1, new String[]{
                "Open Camera", "Choose from Gallery"}) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view
                        .findViewById(android.R.id.text1);
                textView.setTypeface(CommonUtils.setFontTextNormal(context));
                textView.setTextColor(ContextCompat.getColor(context,R.color.text_write));
                return view;
            }
        };

        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setAdapter(
                        new ArrayAdapter<String>(context,
                                android.R.layout.simple_list_item_1,
                                new String[]{"Open Camera",
                                        "From Photo Gallery", "Cancel"}) {
                            public View getView(int position, View convertView,
                                                ViewGroup parent) {
                                View view = super.getView(position,
                                        convertView, parent);
                                TextView textView = (TextView) view
                                        .findViewById(android.R.id.text1);
                                textView.setTextColor(Color.BLACK);
                                return view;
                            }
                        }, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (which == 0) {
                                    Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intentPicture, CUSTOM_REQUEST_CODE);
                                } else if (which == 1) {
                                    if (Build.VERSION.SDK_INT < 19) {
                                        Intent intent = new Intent();
                                        intent.setType("image/jpeg");
                                        intent.setAction(Intent.ACTION_GET_CONTENT);
                                        startActivityForResult(Intent.createChooser(intent, "Select Image"),GALLERY_INTENT_CALLED);
                                    } else {
                                        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                                        intent.setType("image/jpeg");
                                        startActivityForResult(intent,GALLERY_KITKAT_INTENT_CALLED);
                                    }
                                } else {
                                    dialog.dismiss();
                                }
                            }
                        });
        builder.create().show();
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Intent dataVal = data;
            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // profilepic_addproduct.setImageBitmap(photo);

            if (requestCode == CUSTOM_REQUEST_CODE) {
                if (data!= null && data.getData()!=null ) {
                    pathImage = getPath(context, data.getData());
                    pathImage = compressImage(pathImage);

                    imgFile = new File(pathImage);

                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                                .getAbsolutePath());
                        userProfilePic.setImageBitmap(myBitmap);
                    }

                } else {
                    FileOutputStream out = null;
                    try {

                        Bitmap bitmap = dataVal.getExtras().getParcelable("data");


                        String imagename = "img" + requestCode + ".jpg";
                        out = new FileOutputStream(new File(
                                Environment.getExternalStorageDirectory() + "/"
                                        + imagename));
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

                        pathImage = Environment.getExternalStorageDirectory() + "/"
                                + imagename;
                        pathImage = compressImage(pathImage);

                        imgFile = new File(pathImage);

                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                                    .getAbsolutePath());
                            userProfilePic.setImageBitmap(myBitmap);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            } else if (data != null && requestCode == GALLERY_INTENT_CALLED
                    || requestCode == GALLERY_KITKAT_INTENT_CALLED) {
                if (resultCode != Activity.RESULT_OK)
                    return;
                if (null == data)
                    return;
                Uri originalUri = null;
                if (requestCode == GALLERY_INTENT_CALLED) {
                    originalUri = data.getData();
                } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {
                    originalUri = data.getData();
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    // Check for the freshest data.
                    context.getContentResolver().takePersistableUriPermission(originalUri, takeFlags);
                }
                pathImage = getPath(context, originalUri);
                // path=compressImage(path + "");

//                Utils.write("check path===" + path);
                try {
                    pathImage = compressImage(pathImage);
                    imgFile = new File(pathImage);

                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
                                .getAbsolutePath());
                        userProfilePic.setImageBitmap(myBitmap);
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String compressImage(String imageUri) {

        String filePath1 = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // by setting this field as true, the actual bitmap pixels are not
        // loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath1, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as
        // 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        // width and height values are set maintaining the aspect ratio of the
        // image

        System.out.println("...............1  " + "actualHeight:"
                + actualHeight + "actualWidth:" + actualWidth + "maxHeight:"
                + maxHeight + "maxWidth:" + maxWidth + "imgRatio:" + imgRatio
                + "maxRatio:" + maxRatio);

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                System.out.println("a.................");
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                System.out.println("a.................");
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                System.out.println("c.................");
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }
        System.out.println("...............2  " + "actualHeight:"
                + actualHeight + "actualWidth:" + actualWidth + "maxHeight:"
                + maxHeight + "maxWidth:" + maxWidth + "imgRatio:" + imgRatio
                + "maxRatio:" + maxRatio);
        // setting inSampleSize value allows to load a scaled down version of
        // the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth,
                actualHeight);

        // inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        // this options allow android to claim the bitmap memory if it runs low
        // on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            // load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath1, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
                    Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
                middleY - bmp.getHeight() / 2, new Paint(
                        Paint.FILTER_BITMAP_FLAG));

        // check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath1);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

            // write the compressed bitmap at the destination specified by
            // filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("...............3  " + "actualHeight:"
                + actualHeight + "actualWidth:" + actualWidth + "maxHeight:"
                + maxHeight + "maxWidth:" + maxWidth + "imgRatio:" + imgRatio
                + "maxRatio:" + maxRatio);
        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpg");
        System.out.println("uriSting-----------------" + uriSting);
        return uriSting;
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null,
                null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }



    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }


}