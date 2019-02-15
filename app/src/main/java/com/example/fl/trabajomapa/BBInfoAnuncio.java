package com.example.fl.trabajomapa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BBInfoAnuncio extends AppCompatActivity {
    static final String EXTRA_ANUNCIO = "ANUNCIO";

    //ETIQUETAS SUBIDA DE BASE DE DATOS
    DatabaseReference dbRef;
    ValueEventListener valueEventListener;
    ZOferta anuncio = null;

    private FloatingActionsMenu fab2;
    final Context context = this;
    Button btninfowindow_compartirBB;
    TextView detalles, salario, direccion, telefono, correo, nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbinfo_anuncio);

        btninfowindow_compartirBB = (Button) findViewById(R.id.btninfowindow_compartirBB);

        //FLOATING BUTTON
        fab2  = (FloatingActionsMenu) findViewById(R.id.menu_fabBB);


        nombre = (TextView) findViewById(R.id.tvnfowindow_titulobb);
        detalles = (TextView) findViewById(R.id.tvnfowindow_detalles);
        salario = (TextView) findViewById(R.id.tvnfowindow_salario);
        direccion = (TextView) findViewById(R.id.tvnfowindow_direccion);
        telefono = (TextView) findViewById(R.id.tvnfowindow_telefono);
        correo = (TextView) findViewById(R.id.tvnfowindow_correo);

        Bundle b = getIntent().getExtras();

        if (b!=null){
            anuncio = b.getParcelable(BAMapaFinalActivity.EXTRA_ANUNCIO);
            nombre.setText(anuncio.getNombre());
            detalles.setText(anuncio.getDetalles());
            salario.setText(anuncio.getSalario());
            direccion.setText(anuncio.getDireccion());
            telefono.setText(anuncio.getTelefono());
            correo.setText(anuncio.getCorreo());

        }
    }



    //ESTE FUNCIONA BIEN PERO NO EN WHASAPP (HTML)
    public void clickcompartirBB(View v) {

        Intent compartir = new Intent(android.content.Intent.ACTION_SEND);
        compartir.setType("text/plain");
        //compartir.setType("text/plain");
        String detalles_ = detalles.getText().toString();
        String salario_ = salario.getText().toString();
        String direccion_ = direccion.getText().toString();
        String telefono_ = telefono.getText().toString();
        String correo_ = correo.getText().toString();
        String nombre_ = nombre.getText().toString();

        String enviado = "Enviado desde GeoWork";

        SpannableString en = new SpannableString(enviado);

        //ESTILO DE FUENTE

        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        StyleSpan italicSpan = new StyleSpan(Typeface.ITALIC);
        StyleSpan bolditalicSpan = new StyleSpan(Typeface.BOLD_ITALIC);

        //ESTILO EJECUTADO
        en.setSpan(bolditalicSpan, 0, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //TEXTO CON ESTILO

        compartir.putExtra(android.content.Intent.EXTRA_SUBJECT, "Compartir oferta de" + nombre_);
        compartir.putExtra(android.content.Intent.EXTRA_TEXT, ("Empleo de " + nombre_ + "\n"
                + "\uD83D\uDD38" + " " + detalles_ + "\n"
                + "\uD83D\uDCB6" + " " + "salario de " + salario_ + "\n"
                + "\uD83D\uDCCD" + " " + direccion_ + "\n"
                + "\uD83D\uDCDE" + " " + telefono_ + "\n"
                + "\uD83D\uDCE7" + " " + correo_ + "\n"
                + en));
        //compartir.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtm"Oferta de " + nombre_ );
        startActivity(Intent.createChooser(compartir, "Compartir vía"));

    }

    public void clickdireccionBB(View v){
        try {
            Uri location = Uri.parse("https://www.google.es/maps/@"+anuncio.getLatitud()+","+anuncio.getLongitud()+",20z");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
            startActivity(mapIntent);
        }catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BBInfoAnuncio.this, "No hay aplicación para ver la dirección", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktelefonoBB(View v) {
        try {

            Uri number = Uri.parse("tel:"+ telefono.getText().toString());
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            startActivity(callIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(BBInfoAnuncio.this,
                    "No hay apliación para llamar", Toast.LENGTH_LONG).show();
        }
    }
    public void clickMailBB (View v){


        String[] TO = {correo.getText().toString()}; //001 AQUI DEBERÍA IR CORREO
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
        onBackPressed();
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