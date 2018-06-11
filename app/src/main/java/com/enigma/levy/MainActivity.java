package com.enigma.levy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;

import Datos.BackendConnection;
import Datos.Usuario;

public class MainActivity extends AppCompatActivity {

    public static Usuario user;
    SharedPreferences sharedPreferences;

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
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText3);
        user=BackendConnection.Login(editText.getText().toString(), editText2.getText().toString());
        if(user!=null){
            Log.d("IMAGEN", user.getImagen());
            sharedPreferences.edit().putString("token",user.getToken()).apply();
            Principal.find="iphone";
            Intent intent= new Intent(MainActivity.this, Principal.class);
            startActivity(intent);}
        else{
            Toast.makeText(MainActivity.this,"Correo o contraseña invalidos", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AWSMobileClient.getInstance().initialize(this).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences= this.getSharedPreferences("com.enigma.levy", getApplicationContext().MODE_PRIVATE);
        String token= sharedPreferences.getString("token","");
        if(!token.equals("")){
            user=BackendConnection.LoginToken(token);
            if(user!=null){
                Log.d("IMAGEN", user.getImagen());
                sharedPreferences.edit().putString("token",user.getToken()).apply();
                Principal.find="iphone";
                Intent intent= new Intent(MainActivity.this, Principal.class);
                startActivity(intent);}
            else{
                Toast.makeText(MainActivity.this,"Correo o contraseña invalidos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
