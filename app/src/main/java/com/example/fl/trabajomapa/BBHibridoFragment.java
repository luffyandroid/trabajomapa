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
public class BBHibridoFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap hibrido;

    public BBHibridoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bbhibrido, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment vistaHibrido = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.vistaHibrido);
        vistaHibrido.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        hibrido = googleMap;

        //PUNTO DE INICIO DEL MAPA
        LatLng inicio = new LatLng(36.6178533,-6.3685917);
        MarkerOptions option = new MarkerOptions();
        option.position(inicio).title("Inicio");
        hibrido.addMarker(option);
        hibrido.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        hibrido.moveCamera(CameraUpdateFactory.newLatLng(inicio));
    }
}
