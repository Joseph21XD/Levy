package com.enigma.levy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import Datos.Usuario;

public class MainActivity extends AppCompatActivity {

    static Usuario user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.acercade, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()== R.id.acerca){
            Intent intent= new Intent(MainActivity.this, AcercaDe.class);
            startActivity(intent);
        }
        return true;
    }

    public void registrar(View view){
        Intent intent= new Intent(MainActivity.this, Registrar1.class);
        startActivity(intent);
    }

    public void toMain(View view){
        Principal.find="iphone";
        user=new Usuario(1,"Carlos","Alvarado", "https://upload.wikimedia.org/wikipedia/commons/0/05/Carlos_Alvarado_Le_Chateu_%28cropped%29.jpg",5);
        Intent intent= new Intent(MainActivity.this, Principal.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
