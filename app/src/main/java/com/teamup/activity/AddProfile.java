package com.teamup.activity;

import android.Manifest;
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
import android.os.Handler;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pkmmte.view.CircularImageView;
import com.teamup.R;
import com.teamup.model.Player;
import com.teamup.utils.AppConstant;
import com.teamup.utils.CommonUtils;
import com.teamup.utils.FirebaseUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class AddProfile extends BaseActivity {

    public static final int MY_PERMISSIONS_REQUEST_WRITE_READ=1000;
    public static final int GALLERY_INTENT_CALLED = 2224;
    public static final int GALLERY_KITKAT_INTENT_CALLED = 500;
    public static final int CUSTOM_REQUEST_CODE = 390;
    File imgFile;
    String pathImage=null;
    LinearLayout detailsSpin;
    AppCompatImageView desImg;
    AppCompatImageView genImg;
    AppCompatImageView genderArrow;
    AppCompatImageView dobArrow;
    AppCompatImageView desArrow;
    AppCompatImageView dob_img;
    RelativeLayout accessCamera;
    CircularImageView userProfilePic;
    AppCompatImageView plus_add;
    TextView confirmBtn;
    TextView headingValue;
    TextView dob;
    com.teamup.utils.NDSpinner gender;
    com.teamup.utils.NDSpinner describe;
    LinearLayout datepick;
    Handler handler;
    Animation animScale;
    EditText email_text;
    Activity context;
    Calendar myCalendar;

    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try{

            setContentView(R.layout.profile_details);
            context=this;
            findID();
            functionDP();
            settingGenderAdapter();
            settingDesAdapter();

            //backclick();
            setBlankToolbar();
            listener();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void listener() {
        datepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCal();
            }
        });

        accessCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPermission();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navifateProfile();
            }
        });
    }

    private void findID() {

        detailsSpin=findViewById(R.id.detailsSpin);
        desImg=findViewById(R.id.des_img);
        genImg=findViewById(R.id.gen_img);
        genderArrow=findViewById(R.id.genderArrow);
        dobArrow=findViewById(R.id.dobArrow);
        desArrow=findViewById(R.id.desArrow);
        dob_img=findViewById(R.id.dob_img);
        accessCamera=findViewById(R.id.accessCamera);
        userProfilePic=findViewById(R.id.user_profile_pic);
        plus_add=findViewById(R.id.plus_add);
        confirmBtn=findViewById(R.id.confirm);
        headingValue=findViewById(R.id.textView);
        dob=findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        describe=findViewById(R.id.describe);
        datepick=findViewById(R.id.datePck);

    }

    private void settingGenderAdapter() {
        animScale = AnimationUtils.loadAnimation(context, R.anim.anim_bounce);
        handler = new Handler();
       // confirmBtn.setTypeface(CommonUtils.setFontTextHeader(context));
        headingValue.setTypeface(CommonUtils.setFontTextHeader(context));
        dob.setTypeface(CommonUtils.setFontTextNormal(context));
        headingValue.setText("Add Profile Details");

        detailsSpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dobArrow.setImageResource(R.mipmap.below);
                desArrow.setImageResource(R.mipmap.below);
                dobArrow.setImageResource(R.mipmap.below);
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
                if(position == 0)
                {
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
                    genderArrow.setImageResource(R.mipmap.up_arrow);
                }
                return false;
            }

        });


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint

                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));

                if(position > 0){
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    genImg.setImageResource(R.mipmap.gender_colored);

                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                genderArrow.setImageResource(R.mipmap.below);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                genderArrow.setImageResource(R.mipmap.below);
            }


        });
    }

    private void settingDesAdapter() {

        String[] plants = new String[]{
                "What best describes you?",
                "California sycamore",
                "Mountain mahogany",
                "Butterfly weed",
                "Carrot weed"
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
                TextView changeTextColor=((TextView)(view.findViewById(android.R.id.text1)));
                if(position > 0){
                    // Notify the selected item text
                    changeTextColor.setTextColor(getResources().getColor(R.color.text_write));
                    desImg.setImageResource(R.mipmap.soccer_colorful);


                }else{
                    changeTextColor.setTextColor(getResources().getColor(R.color.hint_color));
                }
                desArrow.setImageResource(R.mipmap.below);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

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
            ShowDialogForChoose();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_READ:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ShowDialogForChoose();
                } else {
                    //  CommonUtils.simpleSnackBar("Can't access files", (FrameLayout) layoutView.findViewById(R.id.main_frame));

                }
                break;
        }
    }

    private void updateLabel() throws ParseException {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String setDateByCalendar=sdf.format(myCalendar.getTime());
        Date setByUser=sdf.parse(setDateByCalendar);

        String currentDate= sdf.format(new Date());
        Date currentDae=sdf.parse(currentDate);

        if(setByUser.equals(currentDae)){

            dob.setText(sdf.format(myCalendar.getTime()));
            dobArrow.setImageResource(R.mipmap.below);

        }else if(setByUser.before(currentDae)){

            dob.setText(sdf.format(myCalendar.getTime()));
            dobArrow.setImageResource(R.mipmap.below);

        }else if(setByUser.after(currentDae)){


            dob.setText("");
            dobArrow.setImageResource(R.mipmap.below);

        }


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
                try {
                    updateLabel();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        };
    }


    void openCal(){
        new DatePickerDialog(context, R.style.DialogTheme, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        dobArrow.setImageResource(R.mipmap.up_arrow);
    }
/*
    @OnClick(R.id.open_gen)
    void openGen(View data){
        genImg.setImageResource(R.mipmap.up_arrow);
    }

    @OnClick(R.id.open_des)
    void openDes(View data){

        desArrow.setImageResource(R.mipmap.up_arrow);
    }*/


    @Override
    protected void onResume() {
        super.onResume();
    //    visibilityGONEICon(context);
    }

    void navifateProfile(){

        progressDialog = ProgressDialog.show(this, "", "Please wait...");
        progressDialog.setCancelable(false);

           if(checkValidation()) {

               Log.d(TAG, "navifateProfile: image Path = "+pathImage);
               if(pathImage == null)
                   insertDetails();
               else
                   uploadImageThenInsert();


            //   startActivity(new Intent(context, InviteFriends.class));
//        CommonUtils.simpleSnackBar("Work in progress",detailsSpin);
           }
    }


    String profileUrl = "";

    String TAG = AddProfile.class.getName();
    ProgressDialog progressDialog;
    private void insertDetails(){

        Player player = (Player)getIntent().getSerializableExtra(AppConstant.Player);


        player.setImageUrl(profileUrl);
        player.setGender(gender.getSelectedItem().toString());
        player.setDescribe(describe.getSelectedItem().toString());
        player.setDob(dob.getText().toString());


        FirebaseUtils.getMyProfileReference()
                .setValue(player)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(context, InviteFriends.class));
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


        final StorageReference ref = FirebaseStorage.getInstance()
                .getReference()
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

    // TODO: 17-11-2018 fix get download url; 

    void showPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionsAsk();
        }else{
            ShowDialogForChoose();
        }
    }

    //camera code
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
                    Bitmap bitmap = dataVal.getExtras().getParcelable("data");
                    FileOutputStream out = null;
                    try {

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

        Log.d(TAG, "onActivityResult: image PAth = "+pathImage);

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



    /*validation*/

    private boolean checkValidation(){

        if(gender.getSelectedItemPosition()==0)
        {
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select your gender",detailsSpin);
            return false;
        }else if(TextUtils.isEmpty(dob.getText().toString().trim())){

            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select your dob",detailsSpin);
            return false;
        }
        else if(describe.getSelectedItemPosition()==0)
        {
            CommonUtils.hideSoftKeyboard(context);
            CommonUtils.simpleSnackBar("Please select description",detailsSpin);
            return false;
        }
        else {
            return true;
        }
    }



}
