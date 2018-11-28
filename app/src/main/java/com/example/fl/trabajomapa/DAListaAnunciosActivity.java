package com.example.fl.trabajomapa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DAListaAnunciosActivity extends AppCompatActivity {

    //VARIANTES DE DECLARADAS
    private ListView listanunciosDA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dalista_anuncios);

        ArrayList<ZOferta> anunciospublicadosusuario = new ArrayList<ZOferta>();

        anunciospublicadosusuario.add(new ZOferta("1234", "1234", "Diseñador", "1234", "1234", "tipouesto", "direccion", 6845.54, 68.24, "telefono", "correo", "27/12/1989", "disponible"));
        anunciospublicadosusuario.add(new ZOferta("5678", "5678", "Cineasta", "5678", "5678", "tipouesto", "direccion", 6845.89, 6845.47, "telefono", "correo", "08/12/1859", "disponible"));
        anunciospublicadosusuario.add(new ZOferta("9012", "9012", "9012", "9012", "9012", "tipouesto", "direccion", 684.74, 68.96, "telefono", "correo", "10/11/1459", "disponible"));
        anunciospublicadosusuario.add(new ZOferta("3456", "3456", "Actor", "3456", "3456", "tipouesto", "direccion", 68451.78, 68.12, "telefono", "correo", "10/11/1459", "disponible"));
        anunciospublicadosusuario.add(new ZOferta("3456", "3456", "Actor", "3456", "3456", "tipouesto", "direccion", 684515.49, 6878.18, "telefono", "correo", "10/11/1459", "disponible"));
        anunciospublicadosusuario.add(new ZOferta("1234", "1234", "Actor", "1234", "1234", "tipouesto", "direccion", 6845.48, 68498468.98, "telefono", "correo", "10/11/1459", "disponible"));
        anunciospublicadosusuario.add(new ZOferta("uidempresa", "uid", "Actor", "detalle", "salario", "tipouesto", "direccion", 6845.45, 684168.78, "telefono", "correo", "10/11/1459", "disponible"));
        anunciospublicadosusuario.add(new ZOferta("uidempresa", "uid", "Actor", "detalle", "salario", "tipouesto", "direccion", 68451.78, 688468.78, "telefono", "correo", "10/11/1459", "disponible"));

        //ENLAZO VARIANTES DECLARADAS
        listanunciosDA = (ListView) findViewById(R.id.listanunciosDA);
        listanunciosDA.setAdapter(new AdaptadorAnuncio(this, R.layout.list_da_anuncios, anunciospublicadosusuario) {

            @Override
            public void onAnuncio(Object anuncios, View view) {
                if (anuncios != null) {

                    TextView txtnombre = (TextView) view.findViewById(R.id.tvtituloDA);
                    if (txtnombre != null)
                        txtnombre.setText(((ZOferta) anuncios).getNombre());


                    TextView txtfecha = (TextView) view.findViewById(R.id.tvfechaDA);
                    if (txtfecha != null)
                        txtfecha.setText(((ZOferta) anuncios).getFecha());
                }
            }
        });



        listanunciosDA.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> anunciomodificar, View view, int posicion, long id) {
                ZOferta elegido = (ZOferta) anunciomodificar.getItemAtPosition(posicion);

                CharSequence texto = "Seleccionado: " + elegido.getFecha();
                Toast toast = Toast.makeText(DAListaAnunciosActivity.this, texto, Toast.LENGTH_LONG);
                toast.show();

            }
        });


    }//FIN ONCREATE

}

