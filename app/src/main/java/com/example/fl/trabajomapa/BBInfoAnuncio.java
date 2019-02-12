package com.example.fl.trabajomapa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.GoogleMap;

public class BBInfoAnuncio extends AppCompatActivity {

    private FloatingActionsMenu fab2;
    final Context context = this;
    Button btninfowindow_compartirBB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbinfo_anuncio);

        btninfowindow_compartirBB = (Button) findViewById(R.id.btninfowindow_compartirBB);

        //FLOATING BUTTON
        fab2  = (FloatingActionsMenu) findViewById(R.id.menu_fabBB);

        /*
        //BOTON FLOTANTE
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //BOTONES DEL FLOAT MENU

        View Btnpublicar, Btnrenovar, Btnatras, Btninfo;

        Btnpublicar = findViewById(R.id.botonBAMapaPublicar);
        Btnrenovar = findViewById(R.id.botonBAMapaRenovar);
        Btnatras = findViewById(R.id.botonBAMapaAtrasBB);
        Btninfo = findViewById(R.id.botonBAMapaInfo);

        //FLOATING BUTTON Y SUS DIFERENTES SECCIONES
        FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.menu_fabBB);

        fab.setOnClickListener(this);

        Btnpublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent().setClass(
                        BBInfoAnuncio.this, CAPublicarOfertaActivity.class);
                startActivity(mainIntent);
            }
        });
        Btnrenovar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent().setClass(
                        BBInfoAnuncio.this, DAListaAnunciosActivity.class);
                startActivity(mainIntent);
            }
        });
        Btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent().setClass(
                        BBInfoAnuncio.this, BAMapaFinalActivity.class);
                startActivity(mainIntent);
            }
        });
        Btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_bamapa_info);

                TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);

                volvermenu.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                dialog.show();
            }
        });
        */
    }


    public void clickcompartirBB(View v) {

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
        compartir.putExtra(android.content.Intent.EXTRA_TEXT, (Parcelable) nombre);
        startActivity(Intent.createChooser(compartir, "Compartir vía"));

    }

    public void clicktelefonoBB(View v) {
        try {
            TextView telefono = v.findViewById(R.id.tvnfowindow_telefono);
            telefono.getText().toString();
            Uri number = Uri.parse("tel:"+ telefono);
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BBInfoAnuncio.this,
                    "No hay apliación para llamar", Toast.LENGTH_LONG).show();
        }
    }
    public void clickMailBB (View v){

        TextView correo = v.findViewById(R.id.tvnfowindow_correo);
        correo.getText().toString();

        String[] TO = {"info@didact.es"}; //001 AQUI DEBERÍA IR CORREO
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            //finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BBInfoAnuncio.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_LONG).show();
        }
    }

    public void clickpublicarBB(View v) {
        Intent mainIntent = new Intent().setClass(
                BBInfoAnuncio.this, CAPublicarOfertaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab2.collapse();
    }

    public void clickrenovarBB(View v) {
        Intent mainIntent = new Intent().setClass(
                BBInfoAnuncio.this, DAListaAnunciosActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab2.collapse();
    }

    public void clicatrasBB(View v) {
        Intent mainIntent = new Intent().setClass(
                BBInfoAnuncio.this, BAMapaFinalActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab2.collapse();
    }

    public void clickinfoBB(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bamapa_info);

        //TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);

        volvermenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        dialog.show();
        //PARA QUE SE CIERRE AL PULSAR
        fab2.collapse();
    }


}