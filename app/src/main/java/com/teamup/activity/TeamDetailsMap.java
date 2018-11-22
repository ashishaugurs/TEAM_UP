package com.teamup.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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

public class TeamDetailsMap extends BaseActivity implements OnMapReadyCallback {

    TextView playerName,playerLocation,playerNeeded,soccer,anyAge,gender,text_player,text_loc,text_loc_name,send_msg,addtofav;
    WorkaroundMapFragment mapFragment;
    GoogleMap googleMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_details_with_map);
      //  setBackText();
       // setHeadValue("Team Details",context);
        setToolbar("Team Details");
        findId();
        setUpMap();
    }

    private void findId() {

        playerName=findViewById(R.id.playerName);
        playerLocation=findViewById(R.id.playerLocation);
        playerNeeded=findViewById(R.id.playerNeeded);
        soccer=findViewById(R.id.soccer);
        anyAge=findViewById(R.id.anyage);
        gender=findViewById(R.id.gender);
        text_player=findViewById(R.id.text_player);
        text_loc=findViewById(R.id.text_loc);
        text_loc_name=findViewById(R.id.text_loc_name);
        send_msg=findViewById(R.id.send_msg);
        addtofav=findViewById(R.id.invite);

        playerName.setTypeface(CommonUtils.setFontTextHeader(context));
        playerLocation.setTypeface(CommonUtils.setFontTextNormal(context));
        playerNeeded.setTypeface(CommonUtils.setFontTextNormal(context));
        soccer.setTypeface(CommonUtils.setFontTextNormal(context));
        anyAge.setTypeface(CommonUtils.setFontTextNormal(context));
        gender.setTypeface(CommonUtils.setFontTextNormal(context));
        text_player.setTypeface(CommonUtils.setFontTextNormal(context));
        text_loc.setTypeface(CommonUtils.setFontMedium(context));
        text_loc_name.setTypeface(CommonUtils.setFontTextNormal(context));

        send_msg.setTypeface(CommonUtils.setFontTextHeader(context));
        addtofav.setTypeface(CommonUtils.setFontTextNormal(context));


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            this.googleMap = googleMap;
            mapFragment
                    .setListener(new WorkaroundMapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {
                         //   mapScroll.requestDisallowInterceptTouchEvent(true);
                        }
                    });

            LatLng refLtLg=new LatLng(50.4334, 30.5219);
            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            googleMap.addMarker(new MarkerOptions()
                    .position(refLtLg)
                    .title(String.valueOf("Fabianmouth Local Stadium"))
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
