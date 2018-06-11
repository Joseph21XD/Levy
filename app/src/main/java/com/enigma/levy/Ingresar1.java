package com.enigma.levy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Ingresar1 extends AppCompatActivity {

    static String nombre="";
    static String descripcion="";
    static String precio="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar1);
    }

    public void toInsert2(View view){
        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText3);
        EditText editText3 = findViewById(R.id.editText4);
        nombre = editText.getText().toString();
        precio = editText2.getText().toString();
        descripcion = editText3.getText().toString();
        if(editText.length()!=0 && editText2.length()!=0 && editText3.length()!=0){
            Intent intent= new Intent(Ingresar1.this, Ingresar2.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(Ingresar1.this,"",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText3);
        EditText editText3 = findViewById(R.id.editText4);
        editText.setText(nombre);
        editText2.setText(precio);
        editText3.setText(descripcion);
    }
}
