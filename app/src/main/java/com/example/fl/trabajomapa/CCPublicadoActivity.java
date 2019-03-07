package com.example.fl.trabajomapa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CCPublicadoActivity extends AppCompatActivity {

    //TIEMPO DE SPLASH
    private static final long SPLASH_SCREEN_DELAY = 3000;
    static final String EXTRA_OFERTA = "OFERTA";
    ZOferta oferta = null;

    TextView tvuidempresaCC, tvuidcc, tvmailempresacc, tvnombreempresaCC,tvnombrepuestoCC, tvdetallespuestoCC, tvsalariopuestoCC, tvtipopuestoCC, tvtelefonopuestoCC,
     tvcorreopuestoCC, tvdireccionpuestoCC, tvlatitudpuestoCC, tvlongitudpuestoCC, tvfechapuestoCC;

    GoogleApiClient googleApiClient;
    DatabaseReference dbRef;
    FirebaseAuth.AuthStateListener mAuthListenerLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccpublicado);

        //PROPIEDADES PANTALLA
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //TV REGISTRADOS PARA PASAR EL NOMBRE, EMAIL E ID.;
        tvuidempresaCC = (TextView) findViewById(R.id.tvuidempresacc);
        tvuidcc = (TextView) findViewById(R.id.tvuidcc);
        tvmailempresacc = (TextView) findViewById(R.id.tvmailempresacc);
        tvnombreempresaCC = (TextView)findViewById(R.id.tvnombreempresaCC);
        tvnombrepuestoCC = (TextView)findViewById(R.id.tvnombrepuestoCC);
        tvdetallespuestoCC = (TextView)findViewById(R.id.tvdetallepuestoCC);
        tvsalariopuestoCC = (TextView)findViewById(R.id.tvsalariopuestoCC);
        tvtipopuestoCC = (TextView)findViewById(R.id.tvtipopuestoCC);
        tvtelefonopuestoCC = (TextView)findViewById(R.id.tvtelefonopuestoCC);
        tvcorreopuestoCC = (TextView)findViewById(R.id.tvcorreopuestoCC);
        tvdireccionpuestoCC = (TextView)findViewById(R.id.tvdireccionpuestoCC);
        tvlatitudpuestoCC = (TextView)findViewById(R.id.tvlatitudpuestoCC);
        tvlongitudpuestoCC = (TextView)findViewById(R.id.tvlongitudpuestoCC);
        tvfechapuestoCC = (TextView)findViewById(R.id.tvfechapuestoCC);

        /*cargauser();
        subirempresa();
        recogerdatos();*/

        /*Bundle b = getIntent().getExtras();
        if (b!=null){


        }*/

        String uidempresa = getIntent().getStringExtra("EXTRA_UIDEMPRESAPP");
        tvuidempresaCC.setText(uidempresa);
        String uid = getIntent().getStringExtra("EXTRA_UIDPP");
        tvuidcc.setText(uid);
        String nombre = getIntent().getStringExtra("EXTRA_NOMBREPP");
        tvnombrepuestoCC.setText(nombre);
        String detalles = getIntent().getStringExtra("EXTRA_DETALLESPP");
        tvdetallespuestoCC.setText(detalles);
        String salario = getIntent().getStringExtra("EXTRA_SALARIOPP");
        tvsalariopuestoCC.setText(salario);
        String tipopuesto = getIntent().getStringExtra("EXTRA_TIPOPUESTOPP");
        tvtipopuestoCC.setText(tipopuesto);
        String direccion = getIntent().getStringExtra("EXTRA_DIRECCIONPP");
        tvdireccionpuestoCC.setText(direccion);
        String latitud = getIntent().getStringExtra("EXTRA_LATITUDPP");
        tvlatitudpuestoCC.setText(latitud);
        String longitud = getIntent().getStringExtra("EXTRA_LONGITUDPP");
        tvlongitudpuestoCC.setText(longitud);
        String telefono = getIntent().getStringExtra("EXTRA_TELEFONOPP");
        tvtelefonopuestoCC.setText(telefono);
        String correo = getIntent().getStringExtra("EXTRA_CORREOPP");
        tvcorreopuestoCC.setText(correo);
        String fecha = getIntent().getStringExtra("EXTRA_FECHAPP");
        tvfechapuestoCC.setText(fecha);

        subiroferta();



        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent mainIntent = new Intent().setClass(
                        CCPublicadoActivity.this, BAMapaFinalActivity.class);
                startActivity(mainIntent);

                finish();

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private void subiroferta(){

        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");
        Double latituddouble = Double.parseDouble(tvlatitudpuestoCC.getText().toString());
        Double longituddouble = Double.parseDouble(tvlongitudpuestoCC.getText().toString());
        Map<String, Object> creacion = new HashMap<>();
        creacion.put("uidempresa/", tvuidempresaCC.getText().toString());
        creacion.put("uid/", tvuidcc.getText().toString());
        creacion.put("nombre/", tvnombrepuestoCC.getText().toString());
        creacion.put("detalles/", tvdetallespuestoCC.getText().toString());
        creacion.put("salario/", tvsalariopuestoCC.getText().toString());
        creacion.put("tipopuesto/", tvtipopuestoCC.getText().toString());
        creacion.put("direccion/", tvdireccionpuestoCC.getText().toString());
        creacion.put("latitud/", latituddouble);
        creacion.put("longitud/", longituddouble);
        creacion.put("telefono/", tvtelefonopuestoCC.getText().toString());
        creacion.put("correo/", tvcorreopuestoCC.getText().toString());
        creacion.put("fecha/", tvfechapuestoCC.getText().toString());
        creacion.put("disponible/", "disponible");

        dbRef.child(tvuidcc.getText().toString()).updateChildren(creacion);

        {
            if (oferta != null){

            }
        }
        Toast.makeText(this, "Subido con exito", Toast.LENGTH_SHORT).show();

    }

    //GOOGLE
    /*Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }

        super.onStart();
        //firebaseauth y demas
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListenerLogout);



    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            //OBTENER DATOS DE LA CUENTA DE GOOGLE
            tvmailempresacc.setText(account.getEmail());
            tvuidcc.setText(account.getId());
        }
    }*/
/*
    private void cargauser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        {
            if (user != null) {
                // Name, email address, and profile photo Url
                tvmailempresacc.setText(user.getEmail());
                tvuidcc.setText(user.getUid());
            }
        }
    }

    private void subirempresa(){
        Bundle extrasoferta = getIntent().getExtras();
        String nombreempresa = extrasoferta.getString("NOMBRE EMPRESA");

        tvnombreempresaCC.setText(nombreempresa);
        String uidfinal = tvuidcc.getText().toString();

        //PA PUBLICAR ACTIVITY
        ZEmpresa nuevaEmpresa=new ZEmpresa(uidfinal,tvmailempresacc.getText().toString(),nombreempresa);
        dbRef = FirebaseDatabase.getInstance().getReference().child("empresas");
        dbRef.child(uidfinal).setValue(nuevaEmpresa, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){

                }else{

                    Toast.makeText(getApplicationContext(),"No se puede insertar el anuncio",Toast.LENGTH_LONG).show();

                }
            }
        });

        //{
        //if (user != null){

        //}
        //}


    }

    private void recogerdatos(){
        Bundle extrasoferta = getIntent().getExtras();
        String nombreempresa = extrasoferta.getString("NOMBRE EMPRESA");
        String nombrepuesto = extrasoferta.getString("NOMBRE PUESTO");
        String detallepuesto = extrasoferta.getString("DETALLE PUESTO");
        String salariopuesto = extrasoferta.getString("SALARIO PUESTO");
        String tipopuesto = extrasoferta.getString("TIPO PUESTO");
        String direccionpuesto = extrasoferta.getString("DIRECCION PUESTO");
        String telefonopuesto = extrasoferta.getString("TELEFONO PUESTO");
        String correopuesto = extrasoferta.getString("CORREO PUESTO");
        String latitudpuesto = extrasoferta.getString("LATITUD PUESTO");
        String longitudpuesto = extrasoferta.getString("LONGITUD PUESTO");
        tvnombreempresaCC.setText(nombreempresa);
        tvnombrepuestoCC.setText(nombrepuesto);
        tvdetallespuestoCC.setText(detallepuesto);
        tvsalariopuestoCC.setText(salariopuesto);
        tvtipopuestoCC.setText(tipopuesto);
        tvdireccionpuestoCC.setText(direccionpuesto);
        tvtelefonopuestoCC.setText(telefonopuesto);
        tvcorreopuestoCC.setText(correopuesto);
        tvlatitudpuestoCC.setText(latitudpuesto);
        tvlongitudpuestoCC.setText(longitudpuesto);

        //PA PUBLICAR ACTIVITY
                    dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");

                    String uidfinal = tvuidcc.getText().toString();

                    Map<String, Object> creacion = new HashMap<>();
                    creacion.put("uidempresa/", uidfinal);
                    creacion.put("uid/", uidfinal + "fecha" + "hora");
                    creacion.put("nombre/", tvnombrepuestoCC.getText().toString());
                    creacion.put("detalles/", tvdetallespuestoCC.getText().toString());
                    creacion.put("salario/", tvsalariopuestoCC.getText().toString());
                    creacion.put("tipopuesto/", tvtipopuestoCC.getText().toString());
                    creacion.put("direccion/", tvdireccionpuestoCC.getText().toString());
                    creacion.put("latitud/", Double.parseDouble(tvlatitudpuestoCC.getText().toString()));
                    creacion.put("longitud/", Double.parseDouble(tvlongitudpuestoCC.getText().toString()));
                    creacion.put("telefono/", tvtelefonopuestoCC.getText().toString());
                    creacion.put("correo/", tvcorreopuestoCC.getText().toString());
                    creacion.put("fecha/", "fecha");
                    creacion.put("disponible/", "disponible");

                    dbRef.child(uidfinal + "fecha" + "hora").updateChildren(creacion);



                    //{
                    //if (user != null){

                    //}
                    //}

    }

    private void enviarEmailUsuario() {

        try {
            //ENVIAR EMAIL USUARIO
            String email = tvuidcc.getText().toString();

            //RECOGER
            String to = email;
            String subject = getString(R.string.AsuntoEmailPagoRealizado);
            String cuerpo01 = getString(R.string.CuerpoEmail01);
            String cuerpo02 = getString(R.string.CuerpoEmail02);
            String message = cuerpo01 + cuerpo02;
            //everything is filled out
            //send email
            new ZSimpleMail().sendEmail(to, subject, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}