package es.hackforgood.ruralapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LupaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupa);

        // MODE NIGHT OFF
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        inicializarBarraNavegacion();
    }

    private void inicializarBarraNavegacion () {
        ImageButton buttonOfertas = (ImageButton) findViewById(R.id.home);
        ImageButton buttonMapa = (ImageButton) findViewById(R.id.mapa);
        ImageButton buttonChat = (ImageButton) findViewById(R.id.chat);
        ImageButton buttonProfile = (ImageButton) findViewById(R.id.profile);

        buttonOfertas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LupaActivity.this, OfertasActivity.class);
                startActivity(i);
            }
        });

        buttonMapa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LupaActivity.this, MapaActivity.class);
                startActivity(i);
            }
        });

        buttonChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LupaActivity.this, ChatActivity.class);
                startActivity(i);
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(LupaActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }
}