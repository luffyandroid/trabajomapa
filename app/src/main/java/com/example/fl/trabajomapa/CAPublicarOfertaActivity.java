package com.example.fl.trabajomapa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class CAPublicarOfertaActivity extends AppCompatActivity {

    //DECLARO VARIANTES
    EditText etnombrepuestoCA, etdetallespuestoCA, etsalariopuestoCA,
            etdireccionnegocioCA, ettelefononegocioCA, etcorreonegocioCA;

    CheckBox checkpoliticaCA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capublicar_oferta);

        //ENLAZO VARIANTES
        etnombrepuestoCA = (EditText) findViewById(R.id.etnombrepuestoCA);
        etdetallespuestoCA = (EditText) findViewById(R.id.etdetallespuestoCA);
        etsalariopuestoCA = (EditText) findViewById(R.id.etsalariopuestoCA);
        etdireccionnegocioCA = (EditText) findViewById(R.id.etdireccionnegocioCA);
        ettelefononegocioCA = (EditText) findViewById(R.id.ettelefononegocioCA);
        etcorreonegocioCA = (EditText) findViewById(R.id.etcorreonegocioCA);

        checkpoliticaCA = (CheckBox) findViewById(R.id.checkpoliticaCA);

    }//FIN ONCREATE

    public void publicaroferta(View view) {

        //ENLAZO VARIANTES CAMPOS OBLIGATORIOS
        String nombrepuestoCA = etnombrepuestoCA.getText().toString();
        String salariopuestoCA = etsalariopuestoCA.getText().toString();
        String direccionnegocioCA = etdireccionnegocioCA.getText().toString();
        String telefononegocioCA = ettelefononegocioCA.getText().toString();

        //COMPROBAR SI LOS CAMPOS NO ESTAN VACIOS
        if (nombrepuestoCA.equals("") || direccionnegocioCA.equals("")) {

            Toast.makeText(getApplicationContext(),
                    "Rellena los campos obligatorios",
                    Toast.LENGTH_LONG).show();

        } else {

            boolean error = false;

            //VALIDAR SALARIO Y TELEFONO

            //VALIDAR SALARIO
            if (!Pattern.matches("^[0-9]{9}$", salariopuestoCA)) {
                etsalariopuestoCA.setError("Valores en € completos");
                error = true;
            }

            //VALIDAR TELEFONO
            if (!Pattern.matches("^[0-9]{9}$", telefononegocioCA)) {
                ettelefononegocioCA.setError("Teléfono no válido");
                error = true;
            }

            if (error) {

                Toast.makeText(getApplicationContext(), "Comprueba que los datos son correctos datos", Toast.LENGTH_LONG).show();


            }


        }
    }
}
