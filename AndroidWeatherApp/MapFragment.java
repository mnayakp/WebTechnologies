package com.mycompany.hw9;

// import the AerisMapView & components
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisTile;

public class MapFragment extends MapViewFragment implements
        AerisCallback, OnAerisMapLongClickListener {
    String lat =null;
    String lon = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), "com.mycompany.hw9");

        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);
        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);

        if(this.getArguments()!=null) {
            lat = this.getArguments().getString("latitude");
            lon = this.getArguments().getString("longitude");
            mapView.moveToLocation(new LatLng(Double.valueOf(lat),Double.valueOf(lon)),10);
        }

        mapView.addLayer(AerisTile.RADSAT);

        mapView.setOnAerisMapLongClickListener(this);


        return view;
    }


    @Override
    public void onResult(EndpointType endpointType, AerisResponse aerisResponse) {

    }

    @Override
    public void onMapLongClick(double v, double v1) {

    }
}
