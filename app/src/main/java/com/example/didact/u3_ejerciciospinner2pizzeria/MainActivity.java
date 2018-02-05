package com.example.didact.u3_ejerciciospinner2pizzeria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    Spinner spMasa;
    RadioGroup rgTamano;
    CheckBox cbHamburguesa, cbJamon, cbPepperonni, cbPimiento, cbCebolla, cbPina,
            cbPollo, cbAtun, cbAceituna;
    TextView tvMasaResultado, tvIngredienteResulado, tvTamanoResultado;
    Button btnIngrediente, btnTamano;

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
        tvIngredienteResulado = (TextView)findViewById(R.id.tv_ingrediente);
        tvTamanoResultado = (TextView)findViewById(R.id.tv_tamano);
        btnIngrediente = (Button)findViewById(R.id.btn_ingrediente);
        btnTamano = (Button)findViewById(R.id.btn_tamano);

        String[] masa={"","Fina","Mediana","Gorda"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1,masa);
        spMasa.setAdapter(adaptador);

    }

    public void clickBtnMasa(View view){
        String masaSeleccionada = spMasa.getSelectedItem().toString();

        if(masaSeleccionada.equals("")){
            Toast.makeText(this, "Debes seleccionar un tipo de masa",
                    Toast.LENGTH_LONG).show();
            btnIngrediente.setEnabled(false);
            btnTamano.setEnabled(false);
            tvMasaResultado.setText("Tipo masa: ");
            tvIngredienteResulado.setText("Ingredientes: ");
            tvTamanoResultado.setText("Tamaño: ");
        }else{
            tvMasaResultado.setText("Tipo masa: "+masaSeleccionada);
            btnIngrediente.setEnabled(true);
        }

    }

    public void clickBtnIngrediente(View view){

        String pedidoingredientes = "";

        if(cbAceituna.isChecked()){
            pedidoingredientes += " aceituna";
        }

        if(cbAtun.isChecked()){
            pedidoingredientes += " atun";
        }

        if(cbJamon.isChecked()){
            pedidoingredientes += " jamon";
        }

        if(cbHamburguesa.isChecked()){
            pedidoingredientes += " hamburguesa";
        }

        if(cbPepperonni.isChecked()){
            pedidoingredientes += " pepperonni";
        }

        if(cbPimiento.isChecked()){
            pedidoingredientes += " pimiento";
        }

        if(cbPina.isChecked()){
            pedidoingredientes += " piña";
        }

        if(cbPollo.isChecked()){
            pedidoingredientes += " pollo";
        }

        if(cbCebolla.isChecked()){
            pedidoingredientes += " cebolla";
        }

        if(pedidoingredientes.equals("")){
            Toast.makeText(this, "Debes seleccionar al menos un ingrediente",
                    Toast.LENGTH_LONG).show();
            btnTamano.setEnabled(false);
            tvIngredienteResulado.setText("Ingredientes ");
            tvTamanoResultado.setText("Tamaño: ");
        }else{
            tvIngredienteResulado.setText("Ingredientes: "+pedidoingredientes);
            btnTamano.setEnabled(true);
        }


    }

    public void clickBtnTamano(View view){

        int idRadio = rgTamano.getCheckedRadioButtonId();

        if (idRadio == -1){
            Toast.makeText(this, "Debes seleccionar un tamano",
                    Toast.LENGTH_SHORT).show();
            tvTamanoResultado.setText("Tamaño: ");
        }else {

            RadioButton radioButtonSeleccionado = (RadioButton) findViewById(idRadio);

            String tamanoRadioSeleccionado = radioButtonSeleccionado.getText().toString();

            String masaSeleccionada = spMasa.getSelectedItem().toString();

            String pedidoingredientes = "";

            if(cbAceituna.isChecked()){
                pedidoingredientes += " aceituna";
            }

            if(cbAtun.isChecked()){
                pedidoingredientes += " atun";
            }

            if(cbJamon.isChecked()){
                pedidoingredientes += " jamon";
            }

            if(cbHamburguesa.isChecked()){
                pedidoingredientes += " hamburguesa";
            }

            if(cbPepperonni.isChecked()){
                pedidoingredientes += " pepperonni";
            }

            if(cbPimiento.isChecked()){
                pedidoingredientes += " pimiento";
            }

            if(cbPina.isChecked()){
                pedidoingredientes += " piña";
            }

            if(cbPollo.isChecked()){
                pedidoingredientes += " pollo";
            }

            if(cbCebolla.isChecked()){
                pedidoingredientes += " cebolla";
            }

            tvTamanoResultado.setText("Tamaño: "+tamanoRadioSeleccionado);

            Toast.makeText(this,"Usted ha pedido una pizza de masa "+masaSeleccionada+" con "+pedidoingredientes
            +" y tamano "+tamanoRadioSeleccionado,Toast.LENGTH_LONG).show();
        }


    }








}
