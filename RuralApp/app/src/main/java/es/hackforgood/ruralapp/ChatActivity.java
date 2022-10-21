package es.hackforgood.ruralapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        // MODE NIGHT OFF
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        inicializarBarraNavegacion();
    }

    private void inicializarBarraNavegacion () {
        ImageButton buttonOfertas = (ImageButton) findViewById(R.id.home);
        ImageButton buttonMapa = (ImageButton) findViewById(R.id.mapa);
        ImageButton buttonLupa = (ImageButton) findViewById(R.id.search);
        ImageButton buttonProfile = (ImageButton) findViewById(R.id.profile);

        buttonOfertas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChatActivity.this, OfertasActivity.class);
                startActivity(i);
            }
        });

        buttonMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChatActivity.this, MapaActivity.class);
                startActivity(i);
            }
        });

        buttonLupa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChatActivity.this, LupaActivity.class);
                startActivity(i);
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(ChatActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }
}