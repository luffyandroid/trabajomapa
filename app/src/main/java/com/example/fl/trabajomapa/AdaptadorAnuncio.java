package com.example.fl.trabajomapa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AdaptadorAnuncio extends ArrayAdapter<ZOferta> {

    private ArrayList<ZOferta> anuncios;
    private Context context;
    DatabaseReference dbRef;
    ZOferta oferta;

    public AdaptadorAnuncio(Context context, ArrayList<ZOferta> anuncios) {

        super(context, R.layout.list_da_anuncios, anuncios);
        this.anuncios = anuncios;
        this.context = context;

    }

    public View getView(final int position, View view, ViewGroup anunciomodificar) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View item = inflater.inflate(R.layout.list_da_anuncios, null);

        TextView tvtituloDA = (TextView)
                item.findViewById(R.id.tvtituloDA);
        tvtituloDA.setText(anuncios.get(position).getNombre() );

        TextView tvfechaDA = (TextView)
                item.findViewById(R.id.tvfechaDA);
        tvfechaDA.setText(anuncios.get(position).getFecha() );

        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        final TextView tvocultoDA = (TextView)
                item.findViewById(R.id.tvocultoDA);
        tvocultoDA.setText(anuncios.get(position).getUid() );

        final Button btnrenovarDA = (Button)
                item.findViewById(R.id.btnrenovarDA);
        btnrenovarDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String uid = tvocultoDA.getText().toString();
                Intent i = new Intent().setClass(context.getApplicationContext(), DBPayPalActivity.class);
                i.putExtra("EXTRA_UIDDA", uid);
                context.startActivity(i);

            }
        });

        final Button btnborrarDA = (Button)
                item.findViewById(R.id.btnborrarDA);

        if (anuncios.get(position).getDisponible().equals("no disponible")){
            btnborrarDA.setText("ACTIVAR");


        }
        if (anuncios.get(position).getDisponible().equals("borrar")){
            btnborrarDA.setText("BORRAR");

        }




        btnborrarDA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnborrarDA.getText().equals("DESACTIVAR")){
                    dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");

                    Map<String, Object> creacion = new HashMap<>();

                    creacion.put("disponible/", "no disponible");

                    dbRef.child(tvocultoDA.getText().toString()).updateChildren(creacion);
                }else{
                    if(btnborrarDA.getText().equals("ACTIVAR")){
                        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");

                        Map<String, Object> creacion = new HashMap<>();

                        creacion.put("disponible/", "disponible");

                        dbRef.child(tvocultoDA.getText().toString()).updateChildren(creacion);
                    }else{
                        if(btnborrarDA.getText().equals("BORRAR")){

                            new AlertDialog.Builder(getContext())
                                    .setTitle("Borrar anuncio")
                                    .setMessage("¿Seguro que desea borrar el anuncio?")
                                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //TODO accion de salir de la app
                                            remove(getItem(position));
                                            dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");
                                            dbRef.child(tvocultoDA.getText().toString()).removeValue();
                                            notifyDataSetChanged();

                                            btnborrarDA.setEnabled(false);
                                            btnborrarDA.setBackgroundColor(500065);
                                            btnrenovarDA.setEnabled(false);
                                            btnrenovarDA.setBackgroundColor(500065);
                                            item.setBackgroundColor(500065);
                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //TODO accion de quedarse en el menu
                                        }
                                    })
                                    .create().show();


                        }
                    }
                }


            }
        });


        return item;
    }
}
