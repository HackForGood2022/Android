package es.hackforgood.ruralapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.hackforgood.ruralapp.async.GetPuebloPorNombreAsyncTask;
import es.hackforgood.ruralapp.async.GetPueblosAsyncTask;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ListView mListaPueblos;
    private EditText buscadorEditText;
    private ImageButton buscarPuebloBoton;

    List<GetPueblosAsyncTask.Pueblo> listaPueblos = new ArrayList<GetPueblosAsyncTask.Pueblo>();

    // Google maps
    private GoogleMap mMap;
    private Marker markerCarPosition;
    private String matriculaString;
    private ScrollView popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);


        // MODE NIGHT OFF
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        inicializarBarraNavegacion();

        GetPueblosAsyncTask myTask = new GetPueblosAsyncTask(this);
        myTask.execute();

        popUp = (ScrollView) findViewById(R.id.popUpPueblo);
        mListaPueblos = (ListView) findViewById(R.id.lista_pueblos);
        buscadorEditText = (EditText) findViewById(R.id.barra_busqueda);

        buscarPuebloBoton = (ImageButton) findViewById(R.id.buscarPueblo);
        buscarPuebloBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String filtro = buscadorEditText.getText().toString();

                List<GetPueblosAsyncTask.Pueblo> listaPueblosFiltrada = new ArrayList<GetPueblosAsyncTask.Pueblo>();
                for (GetPueblosAsyncTask.Pueblo pueblo : listaPueblos){
                    if (filtro==null || filtro.isEmpty() || pueblo.name.contains(filtro)){
                        listaPueblosFiltrada.add(pueblo);
                    }
                }

                ListPueblosAdapter adapter = new ListPueblosAdapter(MapaActivity.this, listaPueblosFiltrada);
                // get the ListView and attach the adapter
                mListaPueblos.setAdapter(adapter);
            }
        });

        ImageButton imageButton = (ImageButton) findViewById(R.id.cerrar);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // To make disable Google Maps realign north button on top left
        mMap.getUiSettings().setCompassEnabled(false);

        LatLng carPosition = new LatLng(Double.parseDouble("40.416667"), Double.parseDouble("-3.7025"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carPosition, 5.0f));

    }

    private void inicializarBarraNavegacion () {
        ImageButton buttonOfertas = (ImageButton) findViewById(R.id.home);
        ImageButton buttonLupa = (ImageButton) findViewById(R.id.search);
        ImageButton buttonChat = (ImageButton) findViewById(R.id.chat);
        ImageButton buttonProfile = (ImageButton) findViewById(R.id.profile);

        buttonOfertas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MapaActivity.this, OfertasActivity.class);
                startActivity(i);
            }
        });


        buttonLupa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MapaActivity.this, LupaActivity.class);
                startActivity(i);
            }
        });

        buttonChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MapaActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MapaActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    // ASYNC TASK FRIENDS ADAPTER
    public void setupAdapter(GetPueblosAsyncTask.Result resultado)
    {
        if (resultado.result!=null && resultado.result.equals("success")){
            // NOTIFICATIONS NUMBER
            for (GetPueblosAsyncTask.Pueblo pueblo : resultado.pueblos){
                Log.d("MAPA", pueblo.name);
            }

            listaPueblos = resultado.pueblos;
        }
    }


    public void clickadoPueblo(String nombrePueblo, String latitud, String longitud) {
        Log.d("CLICKADO",nombrePueblo);
        // Se presenta lista vacía
        List<GetPueblosAsyncTask.Pueblo> listaVacia = new ArrayList<GetPueblosAsyncTask.Pueblo>();
        ListPueblosAdapter adapter = new ListPueblosAdapter(this, listaVacia);
        // get the ListView and attach the adapter
        mListaPueblos.setAdapter(adapter);

        // Poner en el mapa
        if (markerCarPosition!=null) markerCarPosition.remove();
        LatLng carPosition = new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud));
        markerCarPosition = mMap.addMarker(new MarkerOptions()
                .position(carPosition)
                .title(nombrePueblo));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(carPosition, 12.0f));


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                TextView text = (TextView) findViewById(R.id.nombrePuebloDesplegable);
                text.setText(nombrePueblo);

                GetPuebloPorNombreAsyncTask myTask = new GetPuebloPorNombreAsyncTask(MapaActivity.this);
                myTask.execute(nombrePueblo);

                return false;
            }
        });
    }

    @SuppressLint("ResourceType")
    public void setUpInfoPueblo(GetPuebloPorNombreAsyncTask.Result result, String nombre){
        TextView nombrePuebloDesplegable = (TextView) findViewById(R.id.nombrePuebloDesplegable);
        nombrePuebloDesplegable.setText(nombre);
        TextView descripcionPuebloDesplegable = (TextView) findViewById(R.id.descripcion_pueblo);
        descripcionPuebloDesplegable.setText(result.description);
        ImageButton imageButton = (ImageButton) findViewById(R.id.fotoPueblo);
        if (nombre.equals("Villanueva de Sigena")){
            imageButton.setImageResource(R.raw.foto1);
        } else if (nombre.equals("Alcolea")){
            imageButton.setImageResource(R.raw.foto2);
        } else if (nombre.equals("La Puebla de Hijar")){
            imageButton.setImageResource(R.raw.foto3);
        } else if (nombre.equals("Fago")){
            imageButton.setImageResource(R.raw.foto4);
        } else if (nombre.equals("Canfranc")){
            imageButton.setImageResource(R.raw.foto5);
        }


        popUp.setVisibility(View.VISIBLE);
    }
}