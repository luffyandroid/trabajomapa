package com.example.fl.trabajomapa;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = "CustomInfoWindowAdapter";
    private LayoutInflater inflater;

    public CustomInfoWindowAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    @Override
    public View getInfoContents(final Marker m) {
        //Carga layout personalizado.
        View v = inflater.inflate(R.layout.infowindow_layout, null);
        String[] info = m.getTitle().split("&");
        String url = m.getSnippet();
        ((TextView)v.findViewById(R.id.tvinfowindow_titulo)).setText("Limpiador de ventanas");
        ((TextView)v.findViewById(R.id.tvnfowindow_detalles)).setText("Media jornada");
        ((TextView)v.findViewById(R.id.tvnfowindow_salario)).setText("600€");
        ((TextView)v.findViewById(R.id.tvnfowindow_direccion)).setText("Calvario,42, Rota, Cádiz");
        ((TextView)v.findViewById(R.id.tvnfowindow_telefono)).setText("687954158");
        ((TextView)v.findViewById(R.id.tvnfowindow_correo)).setText("clinix@outlook.com");
        return v;
    }

    @Override
    public View getInfoWindow(Marker m) {
        View view = inflater.inflate(R.layout.infowindow_layout,null);
        return view;
        //return null;
    }
}
