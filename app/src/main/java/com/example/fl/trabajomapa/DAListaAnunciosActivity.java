package com.example.fl.trabajomapa;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DAListaAnunciosActivity extends AppCompatActivity {

    private FloatingActionsMenu fab4;
    final Context context = this;

    //VARIANTES DE DECLARADAS
    ListView listanunciosDA;
    ArrayList<ZOferta> listaoferta = new ArrayList<ZOferta>();

    //LOGIN NUEVO
    private static final int MY_REQUEST_CODE = 7117; //El numero que quieras
    List<AuthUI.IdpConfig> providers;

    DatabaseReference dbRef;
    ValueEventListener valueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dalista_anuncios);

        //LOGIN NUEVO
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build() //Builder de GOOGLE
        );

        showSignInOptions();


        //FLOATING BUTTON
        fab4  = (FloatingActionsMenu) findViewById(R.id.menu_fabDA);



        listanunciosDA = (ListView)findViewById(R.id.listanunciosDA);


    }//FIN ONCREATE

    //LOGIN NUEVO
    private void showSignInOptions(){
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK)
            {
                //Obtener usuario
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Muestra email en toast
                Toast.makeText(context, "Loqueado con mail "+user.getEmail(), Toast.LENGTH_SHORT).show();
                cargarDatosFirebase();

            }
        }
    }

    //COMIENZO COSAS DE LUFFY

    private void cargarListView (DataSnapshot dataSnapshot){

        //Obtener usuario
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Muestra email en toast
        //Toast.makeText(context, "Loqueado con mail "+user.getEmail(), Toast.LENGTH_SHORT).show();

        ZOferta oferta = dataSnapshot.getValue(ZOferta.class);

        if(oferta.getUidempresa().equals(user.getUid())) {

        listaoferta.add(dataSnapshot.getValue(ZOferta.class));



            AdaptadorAnuncio adaptadorAnuncio = new AdaptadorAnuncio(this, listaoferta);
            listanunciosDA.setAdapter(adaptadorAnuncio);

        }

    }

    private void cargarDatosFirebase(){

        dbRef= FirebaseDatabase.getInstance().getReference().child("anuncios");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaoferta.clear();
                for (DataSnapshot ofertaDataSnapshot: dataSnapshot.getChildren()){
                    cargarListView(ofertaDataSnapshot);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DAListaAnunciosActivity", "DATABASE ERROR");
            }
        };

        dbRef.addValueEventListener(valueEventListener);

    }

    //FIN COSAS DE LUFFY





    //BOTONES DE ELIMINAR Y O MODIFICAR

    /*private void clickRenovar() {

    }


    private void clickBorrar() {

    }*/

    public void clickpublicarDA(View v) {
        Intent mainIntent = new Intent().setClass(
                DAListaAnunciosActivity.this, CAPublicarOfertaActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab4.collapse();
    }

    public void clicknormalDA(View v) {
        Intent mainIntent = new Intent().setClass(
                DAListaAnunciosActivity.this, BAMapaFinalActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab4.collapse();
    }


    public void clicatrasDA(View v) {
        onBackPressed();
        fab4.collapse();

    }

    public void clickinfoDA(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bamapa_info);

        TextView tvDialog = (TextView) dialog.findViewById(R.id.tvDialog);
        TextView tvDialogCorreo = (TextView) dialog.findViewById(R.id.tvDialogCorreo);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);

        tvDialog.setText(Html.fromHtml(getString(R.string.Infomensaje)));


        volvermenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        tvDialogCorreo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String[] TO = {"fulgenll@hotmail.com"};
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
                            Toast.makeText(DAListaAnunciosActivity.this,
                                    "No tienes clientes de email instalados.", Toast.LENGTH_LONG).show();
                        }
                    }
                });


        dialog.show();
        //PARA QUE SE CIERRE AL PULSAR
        fab4.collapse();
    }


}

