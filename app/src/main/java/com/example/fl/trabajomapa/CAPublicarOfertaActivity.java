package com.example.fl.trabajomapa;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import android.widget.AdapterView.OnItemSelectedListener;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBufferResponse;

import com.google.android.gms.location.places.Places;

import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.Task;


import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import static com.example.fl.trabajomapa.BAMapaFinalActivity.EXTRA_ANUNCIO;

public class CAPublicarOfertaActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FloatingActionsMenu fab3;
    final Context context = this;

    //PARTE CODIGO AUTOOMPLETE
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //LOGIN NUEVO
    private static final int MY_REQUEST_CODE = 7117; //El numero que quieras
    List<AuthUI.IdpConfig> providers;



    //DECLARO VARIANTES
    TextView tvocultoCA, tvocultopuestoCA, tvocultofechahoraCA, tvocultolatitudCA, tvocultolongitudCA, tvocultofechaCA;

    Spinner spincategoriaCA;



    EditText etnombreempresaCA, etnombrepuestoCA, etdetallespuestoCA, etsalariopuestoCA,
            etdireccionnegocioCA, ettelefononegocioCA, etcorreonegocioCA;

    CheckBox checkpoliticaCA;


    SignInButton button;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference dbRef;
    Geocoder geocoder = null;
    ZOferta oferta;
    static final String EXTRA_OFERTA = "OFERTA";
    static final String EXTRA_UIDEMPRESA = "UIDEMPRESA";
    static final String EXTRA_UID = "UID";
    static final String EXTRA_NOMBRE = "NOMBRE";
    static final String EXTRA_DETALLES = "DETALLES";
    static final String EXTRA_SALARIO = "SALARIO";
    static final String EXTRA_TIPOPUESTO = "TIPOPUESTO";
    static final String EXTRA_DIRECCION = "DIRECCION";
    static final String EXTRA_LATITUD = "LATITUD";
    static final String EXTRA_LONGITUD = "LONGITUD";
    static final String EXTRA_TELEFONO = "TELEFONO";
    static final String EXTRA_CORREO = "CORREO";
    static final String EXTRA_FECHA = "FECHA";


    private static final String TAGGOOGLE = "GoogleActivity";
    private static final int RC_SIGN_IN = 1;
    private int STORAGE_PERMISSION_CODE= 1;//Para los permisos


    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;



    private static final String TAG = "CAPublicaOfertaActivity";

    //COORDENADAS ENTRE LAS QUE BUSCA EL AUTOCOMPLETE
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(27.666172, -18.273932), new LatLng(42.772283, 4.747570));

    //PARTE CODIGO AUTOOMPLETE
    AutoCompleteTextView etdireccionnegocioAutoCA;
    PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capublicar_oferta);

        //FLOATING BUTTON
        fab3  = (FloatingActionsMenu) findViewById(R.id.menu_fabCA);

        //TOAST COMPROBACIÓN SI HAY INTERNET
        if (!verificaConexion(this)) {
            Toast.makeText(getBaseContext(),
                    "Comprueba tu conexión a Internet", Toast.LENGTH_LONG)
                    .show();
        }

        tvocultopuestoCA = (TextView)findViewById(R.id.tvocultopuestoCA);
        //COSAS DEL BOTON DE GOOGLE SIGN IN
        button = (SignInButton)findViewById(R.id.googleBtn);
        mAuth = FirebaseAuth.getInstance();

        button.setSize(SignInButton.SIZE_WIDE);
        button.setColorScheme(SignInButton.COLOR_DARK);

        geocoder = new Geocoder(this);

        //Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.default_web_client_id))
                .requestIdToken("1018962536927-si5rp3qad920e4vca23vmn5gkeq0ev62.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //00 AUTOCOMPLETE
        etdireccionnegocioAutoCA = (AutoCompleteTextView) findViewById(R.id.etdireccionnegocioAutoCA);
        //INICIAR PROCESO AUTOOMPLETE
        init();


        //VARIANTES DEL SPINNER
        List<ZSpinnerCategoria> items = new ArrayList<ZSpinnerCategoria>(15);
        items.add(new ZSpinnerCategoria(getString(R.string.Otros), R.drawable.map_marker_otros));
        items.add(new ZSpinnerCategoria(getString(R.string.Administracion), R.drawable.map_marker_administracion));
        items.add(new ZSpinnerCategoria(getString(R.string.Agricultura), R.drawable.map_marker_agricultura));
        items.add(new ZSpinnerCategoria(getString(R.string.Animacion), R.drawable.map_marker_animacion));
        items.add(new ZSpinnerCategoria(getString(R.string.Atencionalcliente), R.drawable.map_marker_atencionalcliente));
        items.add(new ZSpinnerCategoria(getString(R.string.CalidadID), R.drawable.map_marker_calidadimasd));
        items.add(new ZSpinnerCategoria(getString(R.string.Comercial), R.drawable.map_marker_comercial));
        items.add(new ZSpinnerCategoria(getString(R.string.Construccion), R.drawable.map_marker_construccion));
        items.add(new ZSpinnerCategoria(getString(R.string.DisenoyArtesgraficas), R.drawable.map_marker_disenoyartesgraficas));
        items.add(new ZSpinnerCategoria(getString(R.string.Educacion), R.drawable.map_marker_educacion));
        items.add(new ZSpinnerCategoria(getString(R.string.Farmaceutica), R.drawable.map_marker_famaceutica));
        items.add(new ZSpinnerCategoria(getString(R.string.Finanzasybanca), R.drawable.map_marker_finanzasybanca));
        items.add(new ZSpinnerCategoria(getString(R.string.Funerarias), R.drawable.map_marker_funerarias));
        items.add(new ZSpinnerCategoria(getString(R.string.Hosteleria), R.drawable.map_marker_hosteleria));
        items.add(new ZSpinnerCategoria(getString(R.string.Informatica), R.drawable.map_marker_informatica));
        items.add(new ZSpinnerCategoria(getString(R.string.Ingenieriaytecnico), R.drawable.map_marker_ingenieriaytecnicos));
        items.add(new ZSpinnerCategoria(getString(R.string.Legal), R.drawable.map_marker_legal));
        items.add(new ZSpinnerCategoria(getString(R.string.Limpeza), R.drawable.map_marker_limpeza));
        items.add(new ZSpinnerCategoria(getString(R.string.Logisticayalmacen), R.drawable.map_marker_logisticayalmacen));
        items.add(new ZSpinnerCategoria(getString(R.string.Marketingycomunicaciones), R.drawable.map_marker_marketingycomunicaciones));
        items.add(new ZSpinnerCategoria(getString(R.string.Musica), R.drawable.map_marker_musica));
        items.add(new ZSpinnerCategoria(getString(R.string.Profesionesyoficios), R.drawable.map_marker_profesionesyoficios));
        items.add(new ZSpinnerCategoria(getString(R.string.Recursoshumanos), R.drawable.map_marker_recursoshumanos));
        items.add(new ZSpinnerCategoria(getString(R.string.Sanidad), R.drawable.map_marker_sanidad));
        items.add(new ZSpinnerCategoria(getString(R.string.Transportes), R.drawable.map_marker_transportes));
        items.add(new ZSpinnerCategoria(getString(R.string.Turismo), R.drawable.map_marker_turismo));
        items.add(new ZSpinnerCategoria(getString(R.string.Venta), R.drawable.map_marker_venta));
        items.add(new ZSpinnerCategoria(getString(R.string.Veterinaria), R.drawable.map_marker_veterinaria));

        //TODAS LAS MIERDAS DEL SPINNER
        spincategoriaCA = (Spinner) findViewById(R.id.spincategoriaCA);
        spincategoriaCA.setAdapter(new ZSpinnerCategoriaAdaptador(this, items));
        spincategoriaCA.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                tvocultopuestoCA.setText(((ZSpinnerCategoria) adapterView.getItemAtPosition(position)).getNombre());
                Toast.makeText(adapterView.getContext(), tvocultopuestoCA.getText().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //PROTOCOLO FECHA Y HORA
        Date d=new Date();

        tvocultofechahoraCA= (TextView) findViewById(R.id.tvocultofechahoraCA);
        SimpleDateFormat ho=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");

        String horaString = ho.format(d);
        tvocultofechahoraCA.setText(horaString);


        //PROTOCOLO FECHA
        Date f=new Date();
        tvocultofechaCA= (TextView) findViewById(R.id.tvocultofechaCA);
        SimpleDateFormat fe=new SimpleDateFormat("dd/MM/yyyy");

        String fechaString = fe.format(f);
        tvocultofechaCA.setText(fechaString);


        //ENLAZO VARIANTES
        etnombreempresaCA = (EditText) findViewById(R.id.etnombreempresaCA);
        etnombrepuestoCA = (EditText) findViewById(R.id.etnombrepuestoCA);
        etdetallespuestoCA = (EditText) findViewById(R.id.etdetallespuestoCA);
        etsalariopuestoCA = (EditText) findViewById(R.id.etsalariopuestoCA);
        //01 etdireccionnegocioCA = (EditText) findViewById(R.id.etdireccionnegocioCA);
        ettelefononegocioCA = (EditText) findViewById(R.id.ettelefononegocioCA);
        etcorreonegocioCA = (EditText) findViewById(R.id.etcorreonegocioCA);

        tvocultoCA = (TextView) findViewById(R.id.tvocultoCA);
        tvocultofechaCA = (TextView) findViewById(R.id.tvocultofechaCA);
        tvocultolatitudCA = (TextView) findViewById(R.id.tvocultolatitudCA);
        tvocultolongitudCA = (TextView) findViewById(R.id.tvocultolongitudCA);

        checkpoliticaCA = (CheckBox) findViewById(R.id.checkpoliticaCA);

        //LOGIN NUEVO
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build() //Builder de GOOGLE
        );



    }//FIN ONCREATE

    //LOGIN NUEVO
    private void showSignInOptions(){
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.MyTheme)
                .build(),MY_REQUEST_CODE
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MY_REQUEST_CODE)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK)
            {
                //Obtener usuario
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //Muestra email en toast
                Toast.makeText(context, "Loqueado con mail "+user.getEmail(), Toast.LENGTH_SHORT).show();

                //dbRef = FirebaseDatabase.getInstance().getReference().child("anuncios");

                String nombreempresaCA = user.getUid();
                String nombrepuestoCA = etnombrepuestoCA.getText().toString();
                String detallespuestoCA = etdetallespuestoCA.getText().toString();
                String salariopuestoCA = etsalariopuestoCA.getText().toString();
                String direccionnegocioCA = etdireccionnegocioAutoCA.getText().toString();
                String telefononegocioCA = ettelefononegocioCA.getText().toString();
                String correonegocioCA = etcorreonegocioCA.getText().toString();
                String latitudnegocioCA = tvocultolatitudCA.getText().toString();
                //TODO es double porque tiene decimales so retraso de hombre
                Double latitudint = Double.parseDouble(latitudnegocioCA);
                String longitudnegocioCA = tvocultolongitudCA.getText().toString();
                Double longitudint = Double.parseDouble(longitudnegocioCA);
                String uidempresa = "empresamolongui";
                String hora = new SimpleDateFormat("HH:mm").format(new Date());
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                Intent mainIntent = new Intent().setClass(
                        CAPublicarOfertaActivity.this, com.example.fl.trabajomapa.CBPayPalActivity.class);

                mainIntent.putExtra("EXTRA_UIDEMPRESA", user.getUid());
                mainIntent.putExtra("EXTRA_UID", user.getUid()+fecha+hora);
                mainIntent.putExtra("EXTRA_NOMBRE", nombrepuestoCA);
                mainIntent.putExtra("EXTRA_DETALLES", detallespuestoCA);
                mainIntent.putExtra("EXTRA_SALARIO", salariopuestoCA);
                mainIntent.putExtra("EXTRA_TIPOPUESTO", tvocultopuestoCA.getText().toString());
                mainIntent.putExtra("EXTRA_DIRECCION", direccionnegocioCA);
                mainIntent.putExtra("EXTRA_LATITUD", tvocultolatitudCA.getText().toString());
                mainIntent.putExtra("EXTRA_LONGITUD", tvocultolongitudCA.getText().toString());
                mainIntent.putExtra("EXTRA_TELEFONO", telefononegocioCA);
                mainIntent.putExtra("EXTRA_CORREO", correonegocioCA);
                mainIntent.putExtra("EXTRA_FECHA", fecha);
                startActivity(mainIntent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent().setClass(this,BAMapaFinalActivity.class);
        startActivity(i);
        finish();
    }
    //COMPROBACIÓN CONEXIÓN INTERNET

    public static boolean verificaConexion(Context ctx) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // No sólo wifi, también GPRS
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        // este bucle debería no ser tan ñapa
        for (int i = 0; i < 2; i++) {
            // ¿Tenemos conexión? ponemos a true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }


    //BOTON OBTENER DIRECCION
    public void obtenerdireccion (View view){
        locationStart();
    }

    //MANDAR OFERTA
    public void publicaroferta(View view) {

        //ENLAZO VARIANTES CAMPOS OBLIGATORIOS
        String nombreempresaCA = etnombreempresaCA.getText().toString();
        String nombrepuestoCA = etnombrepuestoCA.getText().toString();
        String detallespuestoCA = etdetallespuestoCA.getText().toString();
        String salariopuestoCA = etsalariopuestoCA.getText().toString();

        try {
            String direccionnegocioCA = etdireccionnegocioAutoCA.getText().toString();

            List<Address> addressList = geocoder.getFromLocationName(
                    direccionnegocioCA, 5);
            if (addressList != null && addressList.size() > 0) {
                Double lat = (Double) (addressList.get(0).getLatitude() );
                Double lng = (Double) (addressList.get(0).getLongitude() );
                String latitudint = Double.toString(lat);
                tvocultolatitudCA.setText(latitudint);
                String longitudint = Double.toString(lng);
                tvocultolongitudCA.setText(longitudint);

            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        String direccionnegocioCA = etdireccionnegocioAutoCA.getText().toString();
        String telefononegocioCA = ettelefononegocioCA.getText().toString();
        String correonegocioCA = etcorreonegocioCA.getText().toString();
        String latitudnegocioCA = tvocultolatitudCA.getText().toString();
        //TODO es double porque tiene decimales so retraso de hombre
        Double latitudint = Double.parseDouble(latitudnegocioCA);
        String longitudnegocioCA = tvocultolongitudCA.getText().toString();
        Double longitudint = Double.parseDouble(longitudnegocioCA);
        String uidempresa = "empresamolongui";
        String hora = new SimpleDateFormat("H:M").format(new Date());
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        //COMPROBAR SI LOS CAMPOS NO ESTAN VACIOS
        if (nombreempresaCA.equals("")||nombrepuestoCA.equals("") || direccionnegocioCA.equals("") || latitudint.equals(0) || longitudint.equals(0)){

            Toast.makeText(getApplicationContext(),
                    "Rellena los campos obligatorios",
                    Toast.LENGTH_LONG).show();

        } else {

            boolean error = false;

            //VALIDAR SALARIO Y TELEFONO

            //01 QUE NO SEA OBLIGATORIO

            //VALIDAR SALARIO
            /*if (!Pattern.matches("^[0-9]", salariopuestoCA)) {
                etsalariopuestoCA.setError("Valores en € completos");
                error = true;
            }*/


            //01 QUE NO SEA OBLIGATORIO

            //VALIDAR TELEFONO
            if (!Pattern.matches("^[0-9]{9}$", telefononegocioCA)) {
                ettelefononegocioCA.setError("Teléfono no válido");
                error = true;
            }

            //01  QUE NO SEA OBLIGATORIO

            //VALIDAR MAIL
            if (!Pattern.matches("[a-zA-Z0-9._-]+@[a-z0-9]+[.]+[a-z]+", correonegocioCA)) {
                etcorreonegocioCA.setError("Correo no válido");
                error = true;
            }

            //VALIDAR DIRECCION
            if (direccionnegocioCA.equals("")){
                etdireccionnegocioAutoCA.setError("Dirección no valida");
                error = true;
            }

            if (!error) {
                if (!checkpoliticaCA.isChecked()) {

                    //Si el CheckBox no esta chekeado:
                    Toast.makeText(getApplicationContext(),
                            "Debes aceptar la Política de Privacidad",
                            Toast.LENGTH_LONG).show();
                }


                if (error) {

                    Toast.makeText(getApplicationContext(), "Comprueba que los datos son correctos datos", Toast.LENGTH_LONG).show();


                }else {


                    showSignInOptions();


                }
            }


        }
    }





    //PROTOCOLO PARA OBTENER DIRECCION ▼
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        tvocultoCA.setText("Localizacion agregada");


        onStop();
        //002 onPause();
        //001 finish();

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }

    public void setLocation(Location loc) {
        //OBTENER LA DIRECCION DE LA CALLE A PARTIR DE LA LATITUD Y LA LONGITUD
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);


                //004


                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    etdireccionnegocioAutoCA.setText(DirCalle.getAddressLine(0));
                    String latitudauto = String.valueOf(DirCalle.getLatitude());
                    tvocultolatitudCA.setText(latitudauto);
                    String longitudauto = String.valueOf(DirCalle.getLongitude());
                    tvocultolongitudCA.setText(longitudauto);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //COMIENZA GESTION DE LOCALIZACION
    public class Localizacion implements LocationListener {
        CAPublicarOfertaActivity CAPublicarOfertaActivity;

        public CAPublicarOfertaActivity getCAPublicarOfertaActivity() {
            return CAPublicarOfertaActivity;
        }

        public void setMainActivity(CAPublicarOfertaActivity CAPublicarOfertaActivity) {
            this.CAPublicarOfertaActivity = CAPublicarOfertaActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {

            // ESTE METODO SE EJECUTA CADA VEZ QUE EL GPS RECIBE NUEVAS COORDENADAS DEBIDO A LA DETECCION DE UN CAMBIO DE UBICACION
            loc.getLatitude();
            loc.getLongitude();
            String Text = "Lat = " + loc.getLatitude() + "\n Long = " + loc.getLongitude();
            tvocultoCA.setText(Text);
            this.CAPublicarOfertaActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // AVISO DE GPS DESACTIVADO
            tvocultoCA.setText("GPS Desactivado");
        }

        @Override
        public void onProviderEnabled(String provider) {

            // AVISO DE GPS ACTIVO
            tvocultoCA.setText("GPS Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;

                //PROTOCOLO PARA OBTENER DIRECCION ▲
            }
        }
    }


    //DIALOGO DE POLITICA
    public void dialogopolitica(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("POLITICAS DE PRIVACIDAD, CONDICIONES Y PRECIOS");
        builder.setMessage(Html.fromHtml(getString(R.string.Politicamensaje)));
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                checkpoliticaCA.setChecked(true);

            }
        });

        builder.setNegativeButton("Rechazar", null);
                checkpoliticaCA.setChecked(false);

        Dialog dialog = builder.create();
        dialog.show();


    }


    //PROTOCOLO AUTOCOMPLETE
    private void init() {
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        etdireccionnegocioAutoCA.setAdapter(mPlaceAutocompleteAdapter);



        etdireccionnegocioAutoCA.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {

                }

                return false;
            }
        });

        hideSoftKeyboard();
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void clickrenovarCA(View v) {
        Intent mainIntent = new Intent().setClass(
                CAPublicarOfertaActivity.this, DAListaAnunciosActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab3.collapse();
    }

    public void clicatrasCA(View v) {
            onBackPressed();
            fab3.collapse();

        }

    public void clicknormalCA(View v) {
        Intent mainIntent = new Intent().setClass(
                CAPublicarOfertaActivity.this, BAMapaFinalActivity.class);
        startActivity(mainIntent);
        //PARA QUE SE CIERRE AL PULSAR
        fab3.collapse();
    }

    public void clickinfoCA(View v) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_bamapa_info);

       // TextView volvermenu = (TextView) dialog.findViewById(R.id.tvFooterDialogBA);
        Button volvermenu = (Button) dialog.findViewById(R.id.volverBotonDialog);


        volvermenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        dialog.show();
        //PARA QUE SE CIERRE AL PULSAR
        fab3.collapse();
    }


        }
