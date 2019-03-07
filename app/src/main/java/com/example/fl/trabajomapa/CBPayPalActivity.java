package com.example.fl.trabajomapa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fl.trabajomapa.Config.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class CBPayPalActivity extends AppCompatActivity {

    //PROTOCOLO PARA CODIGO PAYPAL
    public static final int PAYPAL_REQUEST_CODE = 7171;


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    //CREO QUE ES PARA ELIMINAR LA ACTIVIDAD DE PAYPAL CUANDO FINALIZA
    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbpay_pal);





        //COMENZAR PROTOCOLO PAYPAL
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        processPayment();
    }

    //CONFIRMACION DATOS DE PAGO
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE){

            String uidempresa = getIntent().getStringExtra("EXTRA_UIDEMPRESA");
            String uid = getIntent().getStringExtra("EXTRA_UID");
            String nombre = getIntent().getStringExtra("EXTRA_NOMBRE");
            String detalles = getIntent().getStringExtra("EXTRA_DETALLES");
            String salario = getIntent().getStringExtra("EXTRA_SALARIO");
            String tipopuesto = getIntent().getStringExtra("EXTRA_TIPOPUESTO");
            String direccion = getIntent().getStringExtra("EXTRA_DIRECCION");
            String latitud = getIntent().getStringExtra("EXTRA_LATITUD");
            String longitud = getIntent().getStringExtra("EXTRA_LONGITUD");
            String telefono = getIntent().getStringExtra("EXTRA_TELEFONO");
            String correo = getIntent().getStringExtra("EXTRA_CORREO");
            String fecha = getIntent().getStringExtra("EXTRA_FECHA");
            Intent i = new Intent().setClass(getApplicationContext(), CCPublicadoActivity.class);
            i.putExtra("EXTRA_UIDEMPRESAPP", uidempresa);
            i.putExtra("EXTRA_UIDPP", uid);
            i.putExtra("EXTRA_NOMBREPP", nombre);
            i.putExtra("EXTRA_DETALLESPP", detalles);
            i.putExtra("EXTRA_SALARIOPP", salario);
            i.putExtra("EXTRA_TIPOPUESTOPP", tipopuesto);
            i.putExtra("EXTRA_DIRECCIONPP", direccion);
            i.putExtra("EXTRA_LATITUDPP", latitud);
            i.putExtra("EXTRA_LONGITUDPP", longitud);
            i.putExtra("EXTRA_TELEFONOPP", telefono);
            i.putExtra("EXTRA_CORREOPP", correo);
            i.putExtra("EXTRA_FECHAPP", fecha);
            startActivity(i);
            finish();

            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation !=null){

                }
            }
            //SI LE DA AL BOTON DE ATRAS
            if (resultCode == Activity.RESULT_CANCELED) {

                //008 QUE SI SE CANCELA VAYA A LA ACTIVITY DEL MENU //NO PURULA

                Intent u = new Intent().setClass(getApplicationContext(), BAMapaFinalActivity.class);
                startActivity(u);
                finish();
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            }
        }//SI LOS DATOS METIDOS NO SON CORRECTOS
        if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID){
            Toast.makeText(this, "Inválido", Toast.LENGTH_LONG).show();
        }
    }

    private void processPayment() {

        //COGE EL VALOR DEL PAGO
        String amount = "3";

        //CONVIERTE EL VALOR DEL PAGO A EUROS
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "EUR",

                //CONCEPTO DEL PAGO
                "Publicación oferta de empleo en GeoWork", PayPalPayment.PAYMENT_INTENT_SALE);

        //PROCESO DE PAGO
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }
}
