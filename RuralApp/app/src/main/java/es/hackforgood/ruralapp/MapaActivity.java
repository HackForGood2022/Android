package es.hackforgood.ruralapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);


        // MODE NIGHT OFF
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        inicializarBarraNavegacion();
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
}