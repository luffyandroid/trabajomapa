package com.example.fl.trabajomapa;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class AdaptadorMarcador extends ArrayAdapter<ZOferta> {
    ArrayList<ZOferta> oferta;
    Context o;

    public AdaptadorMarcador(Context c, ArrayList<ZOferta> recetas) {
        super(c, R.layout.marcadorpersonalizado, recetas);
        this.oferta = recetas;
        this.o = o;
    }
    public View getView(int position, View view, ViewGroup
            viewGroup) {
        LayoutInflater inflater =
                LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.marcadorpersonalizado, null);

        TextView tvUid = (TextView)
                item.findViewById(R.id.tvuidmarcador);
        tvUid.setText(oferta.get(position).getUid() );

        TextView tvNombre = (TextView)
                item.findViewById(R.id.tvnombremarcador);
        tvNombre.setText(oferta.get(position).getNombre() );

        TextView tvDetalles = (TextView)
                item.findViewById(R.id.tvdetallemarcador);
        tvDetalles.setText(oferta.get(position).getDetalles() );

        TextView tvSalario = (TextView)
                item.findViewById(R.id.tvsalariomarcador);
        tvSalario.setText(oferta.get(position).getSalario() );

        TextView tvTipoPuesto = (TextView)
                item.findViewById(R.id.tvtipopuestomarcador);
        tvTipoPuesto.setText(oferta.get(position).getTipopuesto() );

        TextView tvDireccion = (TextView)
                item.findViewById(R.id.tvdireccionmarcador);
        tvDireccion.setText(oferta.get(position).getDireccion() );

        /*TextView tvLatitud = (TextView)
                item.findViewById(R.id.tvlatitudnmarcador);
        tvLatitud.setText(oferta.get(position).getLatitud() );

        TextView tvLongitud = (TextView)
                item.findViewById(R.id.tvlongitudnmarcador);
        tvLongitud.setText(oferta.get(position).getLongitud() );*/

        TextView tvTelefono = (TextView)
                item.findViewById(R.id.tvtelefonomarcador);
        tvTelefono.setText(oferta.get(position).getTelefono() );

        TextView tvCorreo = (TextView)
                item.findViewById(R.id.tvcorreomarcador);
        tvCorreo.setText(oferta.get(position).getCorreo() );

        TextView tvFecha = (TextView)
                item.findViewById(R.id.tvfechamarcador);
        tvFecha.setText(oferta.get(position).getFecha() );

        TextView tvDisponible = (TextView)
                item.findViewById(R.id.tvdisponiblemarcador);
        tvDisponible.setText(oferta.get(position).getDisponible() );

        /*String imagen = oferta.get(position).getFoto();
        /*int idImagen = c.getResources().getIdentifier(imagen,"drawable", c.getPackageName());*/
        //ImageView iv_logo = (ImageView) item.findViewById(R.id.imgFotoItem);
        /*iv_logo.setImageResource(idImagen);

        cargarImagen(imagen, item, iv_logo);*/

        return item;
    }


}
