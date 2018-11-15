package com.example.fl.trabajomapa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class CCPublicadoActivity extends AppCompatActivity {

    //TIEMPO DE SPLASH
    private static final long SPLASH_SCREEN_DELAY = 3000;

    TextView tvuidcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ccpublicado);

        //PROPIEDADES PANTALLA
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //TV REGISTRADOS PARA PASAR EL NOMBRE, EMAIL E ID.;
        tvuidcc = (TextView) findViewById(R.id.tvuidcc);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                Intent mainIntent = new Intent().setClass(
                        CCPublicadoActivity.this, BAMapaActivity.class);
                startActivity(mainIntent);

                finish();

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
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
}