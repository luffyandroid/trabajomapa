package com.example.fl.trabajomapa;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fl.trabajomapa.Config.ZDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class BBSateliteFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap satelite;
    ZOferta usu=null;

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;

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
        CameraUpdate inicioSatelite =
                CameraUpdateFactory.newLatLngZoom(new LatLng(36.6178533,-6.3685917),5);
        MarkerOptions option = new MarkerOptions();
        option
                .position(inicio)
                .title("Inicio");
        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios/");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ZOferta oferta = dataSnapshot.getValue(ZOferta.class);
                Toast.makeText(getContext(), oferta.nombre, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        satelite.addMarker(option);
        satelite.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        satelite.moveCamera(inicioSatelite);
        satelite.setInfoWindowAdapter(new ZDialog(LayoutInflater.from(getActivity())));


    }
}
