package com.example.fl.trabajomapa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdaptadorAnuncio extends ArrayAdapter<ZOferta> {

    private ArrayList<ZOferta> anuncios;
    private Context context;
    DatabaseReference dbRef;
    ValueEventListener valueEventListener;
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

                //dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios/"+uid);


                Intent i = new Intent().setClass(context.getApplicationContext(), DBPayPalActivity.class);
                i.putExtra("EXTRA_UIDDA", uid);
                context.startActivity(i);

            }
        });

        final Button btnborrarDA = (Button)
                item.findViewById(R.id.btnborrarDA);

        if (anuncios.get(position).getDisponible().equals("no disponible")){
            //btnborrarDA.setEnabled(false);
            btnborrarDA.setText("ACTIVAR");


        }
        if (anuncios.get(position).getDisponible().equals("borrar")){
            //btnborrarDA.setEnabled(false);
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

                            Toast.makeText(context, "Señor Stark, no me encuentro bien.....", Toast.LENGTH_LONG).show();
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
                    }
                }


            }
        });


        return item;
    }
}
