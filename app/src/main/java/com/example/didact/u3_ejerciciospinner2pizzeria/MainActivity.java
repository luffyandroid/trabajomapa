package com.example.didact.u3_ejerciciospinner2pizzeria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    Spinner spMasa;
    RadioGroup rgTamano;
    CheckBox cbHamburguesa, cbJamon, cbPepperonni, cbPimiento, cbCebolla, cbPina,
            cbPollo, cbAtun, cbAceituna;
    TextView tvMasaResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spMasa = (Spinner)findViewById(R.id.sp_masa);
        rgTamano = (RadioGroup)findViewById(R.id.rg_tamano);
        cbAceituna = (CheckBox)findViewById(R.id.cb_aceituna);
        cbHamburguesa = (CheckBox)findViewById(R.id.cb_hamburguesa);
        cbJamon = (CheckBox)findViewById(R.id.cb_jamon);
        cbPepperonni = (CheckBox)findViewById(R.id.cb_pepperonni);
        cbPimiento = (CheckBox)findViewById(R.id.cb_pimiento);
        cbCebolla = (CheckBox)findViewById(R.id.cb_cebolla);
        cbPina = (CheckBox)findViewById(R.id.cb_pina);
        cbPollo = (CheckBox)findViewById(R.id.cb_pollo);
        cbAtun = (CheckBox)findViewById(R.id.cb_atun);
        tvMasaResultado = (TextView)findViewById(R.id.tv_masaresultado);

        String[] masa={"","Fina","Mediana","Gorda"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,masa);
        spMasa.setAdapter(adaptador);

    }

    public void clickBtnMasa(View view){
        String masaSeleccionada = spMasa.getSelectedItem().toString();

        if(masaSeleccionada.equals("")){
            Toast.makeText(this,"Debes seleccionar un tipo de masa",
                    Toast.LENGTH_LONG).show();
        }else{

        }

    }

    public void clickBtnIngrediente(View view){

    }

    public void clickBtnTamano(View view){

    }








}
