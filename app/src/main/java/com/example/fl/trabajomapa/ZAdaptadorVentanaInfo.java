package com.example.fl.trabajomapa;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ZAdaptadorVentanaInfo implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context context;

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;
    ZOferta oferta = null;
    TextView nombre, detalles, salario, direccion, telefono, correo;


    public ZAdaptadorVentanaInfo(Context ctx){
        context = ctx;
        mWindow = LayoutInflater.from(ctx).inflate(R.layout.infowindow_layout, null);
    }

    private void rendowWindowText(Marker marker, View view){

        nombre = view.findViewById(R.id.tvinfowindow_titulo);
        detalles = view.findViewById(R.id.tvnfowindow_detalles);
        salario = view.findViewById(R.id.tvnfowindow_salario);
        direccion = view.findViewById(R.id.tvnfowindow_direccion);
        telefono = view.findViewById(R.id.tvnfowindow_telefono);
        correo = view.findViewById(R.id.tvnfowindow_correo);

        nombre.setText(marker.getTitle().toString());
        //detalles.setText(marker.getSnippet().toString());


        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios/" + nombre.getText().toString());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                oferta = dataSnapshot.getValue(ZOferta.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LoginActivity", "DATABASE ERROR");
            }
        };
        dbRef.addValueEventListener(valueEventListener);

        nombre.setText(oferta.getNombre());
        detalles.setText(oferta.getDetalle());
        salario.setText(oferta.getSalario());
        direccion.setText(oferta.getDireccion());
        telefono.setText(oferta.getTelefono());
        correo.setText(oferta.getCorreo());



        //ZMarcador infoWindowData = (ZMarcador) marker.getTag();


    }


    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
        //return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        /*View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.infowindow_layout, null);

        TextView nombre = view.findViewById(R.id.tvinfowindow_titulo);
        TextView detalles = view.findViewById(R.id.tvnfowindow_detalles);


        TextView salario = view.findViewById(R.id.tvnfowindow_salario);
        TextView direccion = view.findViewById(R.id.tvnfowindow_direccion);
        TextView telefono = view.findViewById(R.id.tvnfowindow_telefono);
        TextView correo = view.findViewById(R.id.tvnfowindow_correo);

        nombre.setText(marker.getTitle());
        detalles.setText(marker.getSnippet());

        ZMarcador infoWindowData = (ZMarcador) marker.getTag();

        nombre.setText(infoWindowData.getNombre());
        detalles.setText(infoWindowData.getDetalles());
        salario.setText(infoWindowData.getSalario());
        direccion.setText(infoWindowData.getDireccion());
        telefono.setText(infoWindowData.getTelefono());
        correo.setText(infoWindowData.getCorreo());*/
        rendowWindowText(marker, mWindow);

        return mWindow;
    }
}
