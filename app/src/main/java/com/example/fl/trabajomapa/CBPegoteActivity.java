package com.example.fl.trabajomapa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CBPegoteActivity extends AppCompatActivity {

    TextView tvuidCB, tvnombreempresaCB,tvnombrepuestoCB, tvdetallespuestoCB, tvsalariopuestoCB, tvtipopuestoCB, tvtelefonopuestoCB,
            tvcorreopuestoCB, tvdireccionpuestoCB, tvlatitudpuestoCB, tvlongitudpuestoCB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbpegote);

        tvuidCB = (TextView) findViewById(R.id.tvuidCB);
        tvnombreempresaCB = (TextView)findViewById(R.id.tvnombreempresaCB);
        tvnombrepuestoCB = (TextView)findViewById(R.id.tvnombrepuestoCB);
        tvdetallespuestoCB = (TextView)findViewById(R.id.tvdetallepuestoCB);
        tvsalariopuestoCB = (TextView)findViewById(R.id.tvsalariopuestoCB);
        tvtipopuestoCB = (TextView)findViewById(R.id.tvtipopuestoCB);
        tvtelefonopuestoCB = (TextView)findViewById(R.id.tvtelefonopuestoCB);
        tvcorreopuestoCB = (TextView)findViewById(R.id.tvcorreopuestoCB);
        tvdireccionpuestoCB = (TextView)findViewById(R.id.tvdireccionpuestoCB);
        tvlatitudpuestoCB = (TextView)findViewById(R.id.tvlatitudpuestoCB);
        tvlongitudpuestoCB = (TextView)findViewById(R.id.tvlongitudpuestoCB);

        recogerdatos();

    }


    private void recogerdatos(){
        Bundle extrasoferta = getIntent().getExtras();
        String nombreempresa = extrasoferta.getString("NOMBRE EMPRESA");
        String nombrepuesto = extrasoferta.getString("NOMBRE PUESTO");
        String detallepuesto = extrasoferta.getString("DETALLE PUESTO");
        String salariopuesto = extrasoferta.getString("SALARIO PUESTO");
        String tipodepuesto = extrasoferta.getString("TIPO PUESTO");
        String direccionpuesto = extrasoferta.getString("DIRECCION PUESTO");
        String telefonopuesto = extrasoferta.getString("TELEFONO PUESTO");
        String correopuesto = extrasoferta.getString("CORREO PUESTO");
        String latitudpuesto = extrasoferta.getString("LATITUD PUESTO");
        String longitudpuesto = extrasoferta.getString("LONGITUD PUESTO");
        tvnombreempresaCB.setText(nombreempresa);
        tvnombrepuestoCB.setText(nombrepuesto);
        tvdetallespuestoCB.setText(detallepuesto);
        tvsalariopuestoCB.setText(salariopuesto);
        tvtipopuestoCB.setText(tipodepuesto);
        tvdireccionpuestoCB.setText(direccionpuesto);
        tvtelefonopuestoCB.setText(telefonopuesto);
        tvcorreopuestoCB.setText(correopuesto);
        tvlatitudpuestoCB.setText(latitudpuesto);
        tvlongitudpuestoCB.setText(longitudpuesto);

    }

    public void pegote(View view){
        Intent intent = new Intent().setClass(getApplicationContext(), CCPublicadoActivity.class);
        intent.putExtra("NOMBRE EMPRESA",tvnombreempresaCB.getText().toString());
        intent.putExtra("NOMBRE PUESTO",tvnombrepuestoCB.getText().toString());
        intent.putExtra("DETALLE PUESTO",tvdetallespuestoCB.getText().toString());
        intent.putExtra("SALARIO PUESTO",tvsalariopuestoCB.getText().toString());
        intent.putExtra("TIPO PUESTO",tvtipopuestoCB.getText().toString());
        intent.putExtra("DIRECCION PUESTO",tvdireccionpuestoCB.getText().toString());
        intent.putExtra("TELEFONO PUESTO",tvtelefonopuestoCB.getText().toString());
        intent.putExtra("CORREO PUESTO",tvcorreopuestoCB.getText().toString());
        intent.putExtra("LATITUD PUESTO",tvlatitudpuestoCB.getText().toString());
        intent.putExtra("LONGITUD PUESTO",tvlongitudpuestoCB.getText().toString());
        startActivity(intent);
    }

}
