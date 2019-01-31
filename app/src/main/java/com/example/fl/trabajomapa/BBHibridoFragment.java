package com.example.fl.trabajomapa;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fl.trabajomapa.Config.ZDialog;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BBHibridoFragment extends Fragment implements OnMapReadyCallback{

    GoogleMap hibrido;

    String uidempresa, uid, nombre, detalle, salario, tipopuesto, direccion, telefono, correo, fecha, disponible;
    Double latitud, longitud;

    TextView tvnombrehibrido, tvlatitudhibrido, tvlongitudhibrido;

    ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    ArrayList<Marker> realTimeMarkers = new ArrayList<>();

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;

    //ZOferta ofe=null;
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
        //dbRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        hibrido = googleMap;

        //PUNTO DE INICIO DEL MAPA
        LatLng inicio = new LatLng(36.6178533,-6.3685917);
        CameraUpdate inicioHibrido =
                CameraUpdateFactory.newLatLngZoom(new LatLng(36.6178533,-6.3685917),5);
        MarkerOptions option = new MarkerOptions();
        option.position(inicio).title("Inicio").icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_limpeza));

        hibrido.addMarker(option);
        hibrido.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        hibrido.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity())));
        hibrido.moveCamera(inicioHibrido);


        //00ORIGINAL
        /*dbRef.child("anuncios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(Marker marker:realTimeMarkers){
                    marker.remove();
                }



                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ZOferta ofe = snapshot.getValue(ZOferta.class);
                    latitud = ofe.getLatitud();
                    longitud = ofe.getLongitud();

                    /*ZOferta info = new ZOferta();
                    info.setNombre(ofe.getNombre());
                    info.setDetalle(ofe.getDetalle());
                    info.setSalario(ofe.getSalario());
                    info.setDireccion(ofe.getDireccion());
                    info.setTelefono(ofe.getTelefono());
                    info.setCorreo(ofe.getCorreo());
                    ZDialog custominfowindow = new ZDialog(getA);
                    hibrido.setInfoWindowAdapter(custominfowindow);*/

                    /*MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(latitud, longitud));
                    tmpRealTimeMarkers.add(hibrido.addMarker(markerOptions));

                }

                realTimeMarkers.clear();
                realTimeMarkers.addAll(tmpRealTimeMarkers);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if(location != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(17)
                    .build();
            hibrido.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }*/

    }



}