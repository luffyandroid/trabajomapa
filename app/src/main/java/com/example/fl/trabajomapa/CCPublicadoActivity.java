package com.example.fl.trabajomapa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


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


    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccpublicado);

        //PROPIEDADES PANTALLA

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


}