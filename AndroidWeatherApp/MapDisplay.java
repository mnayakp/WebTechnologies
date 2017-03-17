package com.mycompany.hw9;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

public class MapDisplay extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        String lat = getIntent().getExtras().get("latitude").toString();
        String lon = getIntent().getExtras().get("longitude").toString();

        MapFragment fragmentOne = new MapFragment();

                Bundle b = new Bundle();
                b.putString("latitude", lat);
                b.putString("longitude", lon);
                fragmentOne.setArguments(b);

        fragmentTransaction.add(R.id.frameLayoutMapFragmentContainer, fragmentOne);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();

        setContentView(R.layout.activity_map_display);
    }
}
