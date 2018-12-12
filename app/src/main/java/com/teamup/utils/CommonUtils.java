package com.teamup.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamup.R;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.content.Context.LOCATION_SERVICE;

public class CommonUtils {

    public static byte[] byteArrayImage;
    public static boolean mIsInForegroundMode;
    static String TAG = CommonUtils.class.getName();


    public static TextView tvHeader;
    public static boolean isGpsEnable(Activity context){

        LocationManager service = (LocationManager)context.getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return  enabled;

    }



    public static void write(String message) {
        try {
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String encodeURL(String urlStr) {

        URL url = null;
        try {
            // System.out.println("image url=="+urlStr);
            if (urlStr != null) {
                if (urlStr.length() > 4) {
                    if (urlStr.startsWith("http") || urlStr.contains("http://")) {
                    } else
                        urlStr = "http://" + urlStr;
                    url = new URL(urlStr);
                    URI uri = new URI(url.getProtocol(), url.getUserInfo(),
                            url.getHost(), url.getPort(), url.getPath(),
                            url.getQuery(), url.getRef());
                    url = uri.toURL();
                    return url.toString().replaceAll("&amp;", "&");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlStr;
    }

    public static Typeface getCopperplateGothicLightRegularFont(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "ufonts.com_futura-book-normal.ttf");
    }

    // Some function.
    public static boolean isInForeground() {
        return mIsInForegroundMode;
    }






    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null;
    }

    public static void showToastAlert(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    /*To convert Image url to base64 */
    public static String convertImageUrlToBase64(String imageUrl) {

        URL url = null;
        try {
            url = new URL(imageUrl);
            try {
                Bitmap imageBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                byteArrayImage = baos.toByteArray();
                String image_profile = "";
                image_profile = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
                return image_profile;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static Bitmap getBitmap(String imageUrl) throws IOException {

        URL url = null;
            url = new URL(imageUrl);
                Bitmap imageBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object

                return imageBitmap;

    }



    public static void showAlertWithPositiveButton(int message, Context context, String field) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message + " " + field);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public static void showAlertWithPositiveButton(String message, Context context, String field) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message + " " + field);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public static void showAlertWithPositiveButton(int message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public static void showAlertWithPositiveButton(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();
            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                // String key = pin_map String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
                System.out.println("key                "+key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    private String getDate(long timeStamp){
        try{
            DateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }



    public static boolean validateFirstName(String firstName) {
        return firstName.matches("[A-Z][a-zA-Z]*");
    } // end method validateFirstName

    // validate last name
    public static boolean validateLastName(String lastName) {
        return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    }

    public static void showToast(Context context) {
        Toast.makeText(context, "Work in Progress", Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager in = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = activity.findViewById(android.R.id.content);
            in.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Throwable e) {
        }

    }




    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public static void hideKeyPad(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public final static boolean isValidEmail(String email) {
        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]{2,10}+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher mMatcher = pattern.matcher(inputStr);
        if (mMatcher.matches()) {
            isValid = true;
        }
        return isValid;
    }*/

    public static void clearAllData(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    public static void shareDialog(Context context, String message) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
//        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void shareDialog(Context context) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Here is the share content body";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }



    public final static boolean isValidPhone(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(target).matches() && (target.length() >= 11 && target.length() <= 15);
        }
    }

    public static void savePreferencesString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }








    public static void removePreferencesString(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void showDialog(Context context, String title, final CharSequence[] items, String[] btnText,
                                  DialogInterface.OnClickListener listener) {

      /*  final CharSequence[] items = { "One", "Two" };*/

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        builder.setSingleChoiceItems(items, -1,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                    }
                });
        builder.setPositiveButton(btnText[0], listener);
        if (btnText.length != 1) {
            builder.setNegativeButton(btnText[1], listener);
        }
        builder.show();
    }

    public static String getPreferencesString(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");

    }



    public static long getTimeStampFromDate(String str_date) {

        Date date = null;
        try {
            DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date = formatter1.parse(str_date);

            System.out.println("date into timestamp " + date.getTime());

        } catch (ParseException e) {
            e.printStackTrace();


        }
        long timeSatmp = date.getTime() / 1000l;

        return timeSatmp;

    }


    public static String getDateTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date currDate = new Date(timestamp * 1000);
        return sdf.format(currDate);
    }

    public static void savePreferencesBoolean(Context context, String key, boolean value) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getPreferencesBoolean(Context context, String key) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, false);

    }


  /*  public static void setFragment(Fragment fragment, boolean removeStack, FragmentActivity activity, int mContainer) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer, fragment);
        } else {
            ftTransaction.replace(mContainer, fragment);
            ftTransaction.addToBackStack(null);
        }
        ftTransaction.commit();
    }*/


    /*
    *  this method is used for remove fragment
    */
    public static void setFragment(Fragment fragment, boolean removeStack, FragmentActivity activity, FrameLayout mContainer) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction ftTransaction = fragmentManager.beginTransaction();
        if (removeStack) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ftTransaction.replace(mContainer.getId(), fragment);
        } else {
            ftTransaction.replace(mContainer.getId(), fragment);
            ftTransaction.addToBackStack(null);
        }
        ftTransaction.commit();
    }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }


    //******* For View Pager Dots




    public static Typeface setSFProDisplayRegular(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.SFProDisplayRegular);
        return tf_light;
    }

    public static Typeface setSFProDisplaySemibold(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.SFProDisplaySemibold);
        return tf_light;
    }



    public static Typeface setFontTextHeader(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.workSansBold);
        return tf_light;
    }

    public static Typeface sfProMedium(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.sfProMedium);
        return tf_light;
    }




    public static Typeface setFontTextNormal(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.roboto);
        return tf_light;
    }

    public static Typeface setFontMedium(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.robotoMedium);
        return tf_light;
    }

    public static Typeface setSfPro(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.sfPro);
        return tf_light;
    }

    public static Typeface setSfProBold(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.sfProBold);
        return tf_light;
    }

    public static Typeface setMontserratMedium(Context context) {
        Typeface tf_light = Typeface.createFromAsset(context.getAssets(), AppConstant.montserratMedium);
        return tf_light;
    }

    public static void setRobotoRegular(Context context, TextView... textViews){

        for(TextView textView : textViews){
            textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf"));
        }
    }


    public static void setRobotoMedium(Context context, TextView... textViews){

        for(TextView textView : textViews){
            textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf"));
        }
    }




    /**
     * Function is used to Send SMS using the Android's SmsManager
     *
     * @param address    - mobile phone number
     * @param msgContent - content of the SMS to be sent out.
     */
    public static void sendsms(String address, String msgContent) {
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> smsString = sms.divideMessage(msgContent);
            sms.sendMultipartTextMessage(address, null, smsString, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SendSMS", "Error sending SMS. Exception: " + e.toString());
        }
    }

    public static String manageUTCLocale(String lastUpdated) throws ParseException {
        String result = "";
        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date parsed = sourceFormat.parse(lastUpdated); // => Date is in UTC now
        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        destFormat.setTimeZone(TimeZone.getDefault());
        result = destFormat.format(parsed);
        return result;
    }

    public static String getTimeStamp(String date_time) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getDefault());
        Date datee;
        try {
            datee = (Date) formatter.parse(date_time);
            Log.e("", "Today is  : " + datee.getTime());
            String timestamp = "" + datee.getTime();
            if (timestamp.length() > 10) {
                timestamp = "" + Long.parseLong(timestamp) / 1000L;
            }
            return timestamp;
        } catch (ParseException pe) {
            pe.printStackTrace();
            return "";
        }

    }

    /*To get the date and time from timestamp*/
    public static String getDateByTimestamp(long timestamp) {
        String date = "";
        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            Log.d("Time zone: ", tz.getDisplayName());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setTimeZone(tz);
            sdfDate.setTimeZone(tz);
            Log.e("timestamp", "time : " + System.currentTimeMillis());
            String localTime = sdf.format(new Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
            String localTimew = sdfDate.format(new Date(timestamp*1000)); // I assume your timestamp is in seconds and you're converting to milliseconds?
            Log.e("Time: ", localTime);
            Log.e("Timew: ", localTimew);

            date = localTimew;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }




    /*To get the date and time from timestamp*/
    public static String getTimeByTimestamp(long timestamp) {
        String time = "";
        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            Log.d("Time zone: ", tz.getDisplayName());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat(" HH:mm");
            sdf.setTimeZone(tz);
            sdf2.setTimeZone(tz);
            Log.e("timestamp", "time : " + System.currentTimeMillis());
            String localTime = sdf.format(new Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
            String localTimew = sdf2.format(new Date(timestamp*1000)); // I assume your timestamp is in seconds and you're converting to milliseconds?
            Log.e("Time: ", localTime);
            Log.e("Timew: ", localTimew);
            time = localTimew;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /*To get the date and time from timestamp*/
    public static String getTimeByCurrentTimestamp(long timestamp) {
        String time = "";
        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();
            Log.d("Time zone: ", tz.getDisplayName());
            SimpleDateFormat sdf2 = new SimpleDateFormat(" HH:mm");
            sdf2.setTimeZone(tz);
            Log.e("timestamp", "time : " + System.currentTimeMillis());
            String localTime = sdf2.format(new Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
            Log.e("Time: ", localTime);
            time = localTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }


    /*To get the date and time from timestamp*/
    public static String getTimsByTimestamp(long timestamp) {
        String time = "";
        try {

            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();

            Log.d("Time zone: ", tz.getDisplayName());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
            sdf.setTimeZone(tz);
            sdf2.setTimeZone(tz);


            Log.e("timestamp", "time : " + System.currentTimeMillis());

            String localTime = sdf.format(new Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
            String localTimew = sdf2.format(new Date(timestamp*1000)); // I assume your timestamp is in seconds and you're converting to milliseconds?
            Log.e("Time: ", localTime);
            Log.e("Timew: ", localTimew);

            time = localTimew;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

//    public static String getTimsByTimestamp(long timestamp) {
//        String time = "";
//        try {
//
//            Calendar cal = Calendar.getInstance();
//            TimeZone tz = cal.getTimeZone();
//
//            Log.d("Time zone: ", tz.getDisplayName());
//
//            SimpleDateFormat sdf = pin_map SimpleDateFormat("HH:mm:ss");
//            SimpleDateFormat sdf2 = pin_map SimpleDateFormat("HH:mm:ss");
//            sdf.setTimeZone(tz);
//            sdf2.setTimeZone(tz);
//
//
//            String localTime = sdf.format(pin_map Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
//            String localTimew = sdf2.format(pin_map Date(timestamp)); // I assume your timestamp is in seconds and you're converting to milliseconds?
//            Log.e("Time: ", localTime);
//            Log.e("Timew: ", localTimew);
//
//            time = localTimew;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return time;
//    }

    public static String getMonthsByMonthsNumber(int months) {
        if (months == 1) {
            return "January";
        } else if (months == 2) {
            return "February";
        } else if (months == 3) {
            return "March";
        } else if (months == 4) {
            return "April";
        } else if (months == 5) {
            return "May";
        } else if (months == 6) {
            return "June";
        } else if (months == 7) {
            return "July";
        } else if (months == 8) {
            return "August";
        } else if (months == 9) {
            return "September";
        } else if (months == 10) {
            return "October";
        } else if (months == 11) {
            return "November";
        } else if (months == 12) {
            return "December";
        }


        return "";


    }


    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    /*
    * It is used to convert String to XML data
    * */
    public static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



//    // converted media time
//    public static String getConvertedTime(double time){
//
//        double h,m,s,mil;
//
//        mil = time % 1000;
//        s = time/1000;
//        m = s/60;
//        h = m/60;
//        s = s % 60;
//        m = m % 60;
////        h = h % 24;
//
//        return ( ((int)h < 10 ? "0"+String.valueOf((int)h) : String.valueOf((int)h))
//                +":"+((int)m < 10 ? "0"+String.valueOf((int)m) : String.valueOf((int)m))
//                +":"+((int)s < 10 ? "0"+String.valueOf((int)s) : String.valueOf((int)s)));
//    }

    // converted media time
    public static String getConvertedTime(double time){

        double h,m,s,mil,d;

        mil = time % 1000;
        s = time/1000;
        m = s/60;
        h = m/60;
        d = h/24;
        s = s % 60;
        m = m % 60;
        h = h % 24;

        if(d>=1) {
            return ( ((int)d < 10 ? "0"+ String.valueOf((int)d) : String.valueOf((int)d))
                    +"d "+((int)h < 10 ? "0"+ String.valueOf((int)h) : String.valueOf((int)h))
                    +":"+((int)m < 10 ? "0"+ String.valueOf((int)m) : String.valueOf((int)m))
                    +":"+((int)s < 10 ? "0"+ String.valueOf((int)s) : String.valueOf((int)s)));
        }
        return ( ((int)h < 10 ? "0"+ String.valueOf((int)h) : String.valueOf((int)h))
                +":"+((int)m < 10 ? "0"+ String.valueOf((int)m) : String.valueOf((int)m))
                +":"+((int)s < 10 ? "0"+ String.valueOf((int)s) : String.valueOf((int)s)));
    }



    /*To generate a number randomly */
    public static int getRandomReferenceNumber() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(9000000);
        return n;

    }

    public static class CustomTextWatcher implements TextWatcher {
        private EditText mEditText;
        private int imageIcon,defImage;
        AppCompatImageView firstImg;
        Activity context;

        public CustomTextWatcher(Activity context,EditText e, int password_colored, AppCompatImageView firstImg, int defImage) {
            mEditText = e;
            imageIcon=password_colored;
            this.firstImg=firstImg;
            this.defImage=defImage;
            this.context=context;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mEditText.getText().length()!=0){
                if(firstImg!=null)
                firstImg.setImageResource(imageIcon);
                else
                    mEditText.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, imageIcon), null, null, null);

            }else{
                if(firstImg!=null)
                firstImg.setImageResource(defImage);
                else
                    mEditText.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, defImage), null, null, null);

            }
        }

        public void afterTextChanged(Editable s) {
        }
    }

    public static void simpleSnackBar(String str, View layout){

        Snackbar snackbar = Snackbar.make(layout, str, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void simpleSnackBar(String str, RelativeLayout layout){

        Snackbar snackbar = Snackbar.make(layout, str, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static void simpleSnackBar(String str, LinearLayout layout){

        Snackbar snackbar = Snackbar.make(layout, str, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    public static void simpleSnackBar(final String str, FrameLayout layout){

        Snackbar snackbar = Snackbar.make(layout, str, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public static void simpleSnackBar(String str, DrawerLayout layout){
        Snackbar snackbar = Snackbar.make(layout, str, Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    public static void simpleSnackBar(Activity activity, String message){
        Snackbar.make(activity.getWindow().getDecorView().findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_SHORT)
                .show();
    }


    public static AlertDialog getInviteDialog(Context context, String dialogMessage, String initialMessage){


        View view = LayoutInflater.from(context).inflate( R.layout.layout_dialog_input_edittext, null, false);
        EditText msgBox = view.findViewById(R.id.dialog_editText);
        msgBox.setText(initialMessage);

        AlertDialog dialog = new AlertDialog.Builder(context, R.style.DialogTheme)
                .setNegativeButton("Cancel", null)
                .setView(view)
               // .setCustomTitle(dialogTitle)
                .setTitle(dialogMessage)
                .create();



        if(dialog.getWindow()!=null) {
            dialog.getWindow()
                    .setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                            WindowManager.LayoutParams.WRAP_CONTENT);
          //  Log.d(TAG, "getInviteDialog: dialogWindow is not null");
        }

        return dialog;

    }


    public static boolean isAnyEditTextEmpty(EditText... editTexts){
        Boolean isAnyEmpty = false;
        for(EditText editText : editTexts){
            if(editText.getText().toString().isEmpty()){
                editText.setError("Field required");
                isAnyEmpty = true;
            }
        }
        return isAnyEmpty;
    }


    public static String getUtcTimeFromMillis(long timeInMillis){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");

       sdf.setTimeZone(TimeZone.getDefault());

        return sdf.format(calendar.getTime());
    }

}
