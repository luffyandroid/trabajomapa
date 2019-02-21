package com.example.fl.trabajomapa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AdaptadorAnuncio extends ArrayAdapter<ZOferta> {

    private ArrayList<ZOferta> anuncios;
    private Context context;
    DatabaseReference dbRef;

    public AdaptadorAnuncio(Context context, ArrayList<ZOferta> anuncios) {

        super(context, R.layout.list_da_anuncios, anuncios);
        this.anuncios = anuncios;
        this.context = context;

    }

    public View getView(int position, View view, ViewGroup anunciomodificar) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_da_anuncios, null);

        TextView tvtituloDA = (TextView)
                item.findViewById(R.id.tvtituloDA);
        tvtituloDA.setText(anuncios.get(position).getNombre() );

        TextView tvfechaDA = (TextView)
                item.findViewById(R.id.tvfechaDA);
        tvfechaDA.setText(anuncios.get(position).getFecha() );

        final TextView tvocultoDA = (TextView)
                item.findViewById(R.id.tvocultoDA);
        tvocultoDA.setText(anuncios.get(position).getUid() );

        Button btnrenovarDA = (Button)
                item.findViewById(R.id.btnrenovarDA);
        btnrenovarDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");

                Map<String, Object> creacion = new HashMap<>();

                creacion.put("disponible/", "disponible");

                dbRef.child(tvocultoDA.getText().toString()).updateChildren(creacion);
            }
        });

        Button btnborrarDA = (Button)
                item.findViewById(R.id.btnborrarDA);
        btnborrarDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");

                Map<String, Object> creacion = new HashMap<>();

                creacion.put("disponible/", "no disponible");

                dbRef.child(tvocultoDA.getText().toString()).updateChildren(creacion);
            }
        });

        if (anuncios.get(position).getDisponible().equals("no disponible")){
            btnborrarDA.setEnabled(false);
            btnborrarDA.setText("DESACTIVADO");
        }













        return item;
    }
}
