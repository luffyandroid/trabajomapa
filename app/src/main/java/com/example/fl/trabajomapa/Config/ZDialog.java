package com.example.fl.trabajomapa.Config;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.fl.trabajomapa.R;
import com.example.fl.trabajomapa.ZOferta;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class ZDialog implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public ZDialog (Context ctx){
        context = ctx;
    }



    //private static final String TAG = "CustomInfoWindowAdapter";
    //private LayoutInflater inflater;


    /*public ZDialog(LayoutInflater inflater){
        this.inflater = inflater;
    }*/

    @Override
    public View getInfoWindow(Marker m) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.infowindow_layout, null);

        TextView nombre = v.findViewById(R.id.tvinfowindow_titulo);
        TextView detalle = v.findViewById(R.id.tvnfowindow_detalles);
        TextView salario = v.findViewById(R.id.tvnfowindow_salario);
        TextView direccion = v.findViewById(R.id.tvnfowindow_direccion);
        TextView telefono = v.findViewById(R.id.tvnfowindow_telefono);
        TextView correo = v.findViewById(R.id.tvnfowindow_correo);

        ZOferta infoWindowData = (ZOferta) m.getTag();

        nombre.setText(infoWindowData.getNombre());
        detalle.setText(infoWindowData.getDetalle());
        salario.setText(infoWindowData.getSalario());
        direccion.setText(infoWindowData.getDireccion());
        telefono.setText(infoWindowData.getTelefono());
        correo.setText(infoWindowData.getCorreo());

        return v;
    }


}
