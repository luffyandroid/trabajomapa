package com.example.fl.trabajomapa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public abstract class ZAdaptadorVentanaInfo implements View.OnTouchListener, GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context context;

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;
    ZOferta oferta = null;
    TextView nombre, detalles, salario, direccion, telefono, correo;


    //001
    Button btninfowindow_compartir;
    private OnInfoWindowElemTouchListener infoButtonListener;

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


        //001

        btninfowindow_compartir = view.findViewById(R.id.btninfowindow_compartir);



        nombre.setText(marker.getTitle().toString());
        //detalles.setText(marker.getSnippet().toString());


        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios/" + nombre.getText().toString());

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                oferta = dataSnapshot.getValue(ZOferta.class);

                nombre.setText(oferta.getNombre());
                detalles.setText(oferta.getDetalle());
                salario.setText(oferta.getSalario());
                direccion.setText(oferta.getDireccion());
                telefono.setText(oferta.getTelefono());
                correo.setText(oferta.getCorreo());

                //rellenardatos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LoginActivity", "DATABASE ERROR");
            }
        };
        dbRef.addValueEventListener(valueEventListener);

        //ZMarcador infoWindowData = (ZMarcador) marker.getTag();


    }



    /* void rellenardatos() {
        nombre.setText(oferta.getNombre());
        detalles.setText(oferta.getDetalle());
        salario.setText(oferta.getSalario());
        direccion.setText(oferta.getDireccion());
        telefono.setText(oferta.getTelefono());
        correo.setText(oferta.getCorreo());
    }*/

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

    //BOTONES DEL INFOWINDOWS

    public void botontelefono (View v) {

        if  (telefono.equals("no")) {
            Toast.makeText(context.getApplicationContext(),
                    nombre + " no tiene teléfono de contacto",
                    Toast.LENGTH_LONG).show();
        } else {
            try {
                Uri number = Uri.parse("tel:" + telefono);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                context.startActivity(callIntent);
            } catch (android.content.ActivityNotFoundException ex) {

            }
        }
    }




    public void compartir (View v) {

        Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
        compartir.setType("text/plain");

        TextView nombre = v.findViewById(R.id.tvinfowindow_titulo);
        TextView detalle = v.findViewById(R.id.tvnfowindow_detalles);
        TextView salario = v.findViewById(R.id.tvnfowindow_salario);
        TextView direccion = v.findViewById(R.id.tvnfowindow_direccion);
        TextView telefono = v.findViewById(R.id.tvnfowindow_telefono);
        TextView correo = v.findViewById(R.id.tvnfowindow_correo);

        nombre.getText().toString();
        detalle.getText().toString();
        salario.getText().toString();
        direccion.getText().toString();
        telefono.getText().toString();
        correo.getText().toString();


        compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Empleo encontrado en GeoWork " + nombre);
        //  compartir.putExtra(android.content.Intent.EXTRA_TEXT, nombre + detalle);
        // startActivity(Intent.createChooser(compartir, "Compartir vía"));

    }


    private class OnInfoWindowElemTouchListener {
    }
}
