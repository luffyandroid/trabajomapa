package com.example.fl.trabajomapa;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



public abstract class AdaptadorAnuncio extends BaseAdapter {

    private ArrayList<?> anuncios;
    private int R_layout_Anuncio;
    private Context context;

    public AdaptadorAnuncio(Context context, int R_R_layout_Anuncio, ArrayList<?> anuncios) {

        super();
        this.context = context;
        this.anuncios = anuncios;
        this.R_layout_Anuncio = R_R_layout_Anuncio;
    }

    @Override
    public View getView(int position, View view, ViewGroup anunciomodificar) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R_layout_Anuncio, null);

        }
        onAnuncio(anuncios.get(position), view);
        return view;
    }

    @Override
    public int getCount() {

        return anuncios.size();
    }

    @Override
    public Object getItem(int posicion) {

        return anuncios.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {

        return posicion;
    }
    
    public  abstract void onAnuncio (Object anuncio, View view);


    }