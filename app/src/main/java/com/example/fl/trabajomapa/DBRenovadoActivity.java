package com.example.fl.trabajomapa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DBRenovadoActivity extends AppCompatActivity {

    private static final long SPLASH_SCREEN_DELAY = 3000;
    ZOferta oferta = null;

    TextView tvuidDB, tvuidempresaDB, tvfechaDB;

    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbrenovado);

        //PROPIEDADES PANTALLA
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //TV PARA SUBIR COSAS
        tvuidDB = (TextView)findViewById(R.id.tvuidDB);
        tvuidempresaDB = (TextView)findViewById(R.id.tvuidempresaDB);
        tvfechaDB = (TextView)findViewById(R.id.tvfechaDB);

        String uidempresa = getIntent().getStringExtra("EXTRA_UIDEMPRESAPP");
        tvuidempresaDB.setText(uidempresa);
        String uid = getIntent().getStringExtra("EXTRA_UIDPP");
        tvuidDB.setText(uid);
        String fecha = getIntent().getStringExtra("EXTRA_FECHAPP");
        tvfechaDB.setText(fecha);

        subiroferta();



        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent mainIntent = new Intent().setClass(
                        DBRenovadoActivity.this, BAMapaFinalActivity.class);
                startActivity(mainIntent);

                finish();

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);

    }

    private void subiroferta(){


        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String uid = getIntent().getStringExtra("EXTRA_UIDDB");
        dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");

        Map<String, Object> creacion = new HashMap<>();
        creacion.put("fecha/", fecha);
        creacion.put("disponible/", "disponible");

        dbRef.child(uid).updateChildren(creacion);

        {
            if (oferta != null){

            }
        }
        Toast.makeText(this, "Subido con exito", Toast.LENGTH_SHORT).show();

    }

}
