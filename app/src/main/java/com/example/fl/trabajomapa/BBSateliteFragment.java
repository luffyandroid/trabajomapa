package com.example.fl.trabajomapa;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class BBSateliteFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap satelite;

    public BBSateliteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bbsatelite, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment vistaSatelite = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.vistaSatelite);
        vistaSatelite.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        satelite = googleMap;

        //PUNTO DE INICIO DEL MAPA
        LatLng inicio = new LatLng(36.6178533,-6.3685917);
        MarkerOptions option = new MarkerOptions();
        option.position(inicio).title("Inicio");
        satelite.addMarker(option);
        satelite.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        satelite.moveCamera(CameraUpdateFactory.newLatLng(inicio));
    }
}
