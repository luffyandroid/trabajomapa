package com.example.fl.trabajomapa;

import android.Manifest;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fl.trabajomapa.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class BAMapaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //001
    private GoogleMap mapa;

    final Context context = this;

    //CAMBIA TEXTO NAVEGATION
    TextView tvnavegation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bamapa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerPosicion();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //PARA CAMBIAR DATOS NAVEGATION BAR //CAMBIA TEXTO NAVEGATION
        View header = navigationView.getHeaderView(0);
        tvnavegation = (TextView) header.findViewById(R.id.tvnavegation);


        //FRAGMENT POR DEFECTO
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.ftMain, new BBSateliteFragment());
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bamapa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_satelite) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            //CAMBIA TEXTO NAVEGATION
            tvnavegation.setText("Mapa que puedes ver el relieve de las calles");
            ft.replace(R.id.ftMain, new BBSateliteFragment());
            ft.commit();
        } else if (id == R.id.nav_calles) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            //CAMBIA TEXTO NAVEGATION
            tvnavegation.setText("Mapa que puedes ver el nombre de las calles");
            ft.replace(R.id.ftMain, new BBCallesFragment());
            ft.commit();
        } else if (id == R.id.nav_hibrido) {
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            //CAMBIA TEXTO NAVEGATION
            tvnavegation.setText("Mapa que puedes ver relieve y nombre de las calles");
            ft.replace(R.id.ftMain, new BBHibridoFragment());
            ft.commit();
        } else if (id == R.id.nav_publicar) {
            //CAMBIA TEXTO NAVEGATION
            tvnavegation.setText("");
            Intent mainIntent = new Intent().setClass(getApplicationContext(), CAPublicarOfertaActivity.class);
            startActivity(mainIntent);
        }else if (id == R.id.nav_renovar) {
            Intent mainIntent = new Intent().setClass(getApplicationContext(), BAMapaFinalActivity.class);
            startActivity(mainIntent);
        } else if (id == R.id.nav_info) {

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.dialog_bamapa_info);

            //TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);
            Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);


            volvermenu.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //001
    private void obtenerPosicion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mapa.setMyLocationEnabled(true);

        CameraPosition camPos = mapa.getCameraPosition();

        LatLng coordenadas = camPos.target;
        double latitud = coordenadas.latitude;
        double longitud = coordenadas.longitude;

        Toast.makeText(this, "Lat: " + latitud + " | Long: " + longitud, Toast.LENGTH_SHORT).show();
    }






}
