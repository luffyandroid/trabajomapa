package com.example.fl.trabajomapa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
            .environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)
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

            Intent i = new Intent().setClass(getApplicationContext(), CCPublicadoActivity.class);
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

                Intent u = new Intent().setClass(getApplicationContext(), BAMapaActivity.class);
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
