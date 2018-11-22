package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamup.R;
import com.teamup.fragment.WorkaroundMapFragment;
import com.teamup.utils.CommonUtils;


public class EventDetails extends BaseActivity implements OnMapReadyCallback {

    TextView gameName, gameType, gameStadium, stadiumStreet, stadiumDistance, matchDay, matchTime,
            addToCalendar, ticketPrice, ticketSite, payOption, notes, notesContent, readMore, location,
            locationName, notGOing, going, notSure;
    WorkaroundMapFragment mapFragment;
    GoogleMap googleMap;
    NestedScrollView mapScroll;
    LinearLayout notGoingParent,goingParent,notSureParent;
    ImageView notGoingImg,goingImg,notSureImg;
    boolean expandable=false;
    AppCompatImageView rightIcon;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_event_details);
        context = this;
     //   setHeadValue("Event Name", context);
      //  setBackText();
        setToolbar("Event name");
        getIDsTypeFace();
        setUpMap();
        listener();
    }

    private void listener() {

        notGoingParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notGoingImg.setImageResource(R.mipmap.not_going_color);
                notGOing.setTextColor(getResources().getColor(R.color.red_text));
                goingImg.setImageResource(R.mipmap.going_small);
                going.setTextColor(getResources().getColor(R.color.text_write));
                notSureImg.setImageResource(R.mipmap.note_sure);
                notSure.setTextColor(getResources().getColor(R.color.text_write));


            }
        });

        goingParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                notGoingImg.setImageResource(R.mipmap.not_going);
                notGOing.setTextColor(getResources().getColor(R.color.text_write));
                goingImg.setImageResource(R.mipmap.going_color);
                going.setTextColor(getResources().getColor(R.color.green_text));
                notSureImg.setImageResource(R.mipmap.note_sure);
                notSure.setTextColor(getResources().getColor(R.color.text_write));


            }
        });

        notSureParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notGoingImg.setImageResource(R.mipmap.not_going);
                notGOing.setTextColor(getResources().getColor(R.color.text_write));
                goingImg.setImageResource(R.mipmap.going_small);
                going.setTextColor(getResources().getColor(R.color.text_write));
                notSureImg.setImageResource(R.mipmap.not_sure_color);
                notSure.setTextColor(getResources().getColor(R.color.yellow_text));


            }
        });

    }

    private void getIDsTypeFace() {

        notGoingImg=findViewById(R.id.not_going_img);
        goingImg=findViewById(R.id.going_img);
        notSureImg=findViewById(R.id.not_sure_img);

        notGoingParent=findViewById(R.id.not_going_parent);
        goingParent=findViewById(R.id.going_parent);
        notSureParent=findViewById(R.id.not_sure_parent);

        mapScroll = findViewById(R.id.mapScroll);
        gameName = findViewById(R.id.titleEvent);
        gameType = findViewById(R.id.gameType);
        gameStadium = findViewById(R.id.stadium_name);
        stadiumStreet = findViewById(R.id.stadium_details);
        stadiumDistance = findViewById(R.id.distance);
        matchDay = findViewById(R.id.day_event);
        matchTime = findViewById(R.id.time_event);
        addToCalendar = findViewById(R.id.addtoc);
        ticketPrice = findViewById(R.id.ticket_price);
        ticketSite = findViewById(R.id.ticket_booking);
        payOption = findViewById(R.id.paymentOption);
        notes = findViewById(R.id.note);
        notesContent = findViewById(R.id.note_content);
        readMore = findViewById(R.id.read_more);
        location = findViewById(R.id.location);
        locationName = findViewById(R.id.stadium_name_map);
        notGOing = findViewById(R.id.not_going);
        going = findViewById(R.id.going);
        notSure = findViewById(R.id.not_sure);
        ticketPrice.setTypeface(CommonUtils.setFontTextHeader(context));
        ticketSite.setTypeface(CommonUtils.setFontTextNormal(context));
        payOption.setTypeface(CommonUtils.setFontMedium(context));
        notes.setTypeface(CommonUtils.setFontMedium(context));
        notesContent.setTypeface(CommonUtils.setFontTextNormal(context));
        readMore.setTypeface(CommonUtils.setFontMedium(context));
        location.setTypeface(CommonUtils.setFontMedium(context));
        locationName.setTypeface(CommonUtils.setFontTextNormal(context));
        notGOing.setTypeface(CommonUtils.setFontTextNormal(context));
        going.setTypeface(CommonUtils.setFontTextNormal(context));
        notSure.setTypeface(CommonUtils.setFontTextNormal(context));
        gameName.setTypeface(CommonUtils.setFontTextHeader(context));
        gameType.setTypeface(CommonUtils.setFontTextNormal(context));
        gameStadium.setTypeface(CommonUtils.setFontMedium(context));
        stadiumStreet.setTypeface(CommonUtils.setFontTextNormal(context));
        stadiumDistance.setTypeface(CommonUtils.setFontMedium(context));
        matchDay.setTypeface(CommonUtils.setFontTextHeader(context));
        matchTime.setTypeface(CommonUtils.setFontTextNormal(context));
        addToCalendar.setTypeface(CommonUtils.setFontMedium(context));
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            this.googleMap = googleMap;
            mapFragment
                    .setListener(new WorkaroundMapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {
                            mapScroll.requestDisallowInterceptTouchEvent(true);
                        }
                    });

            LatLng refLtLg=new LatLng(50.4334, 30.5219);
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            googleMap.addMarker(new MarkerOptions()
                    .position(refLtLg)
                    .title(String.valueOf("Olimpiyskiy National Stadium"))
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.black_marker)));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(refLtLg, 14.0f));

        } catch (Exception e) {
            e.printStackTrace();
      }
    }

    public void setUpMap() {

        if (googleMap == null) {
            mapFragment = (WorkaroundMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.map);
            View mapParameter = mapFragment.getView();
            mapFragment.getMapAsync(this);
        }
    }

}