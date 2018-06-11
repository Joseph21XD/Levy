package com.enigma.levy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrar1 extends AppCompatActivity {

    static String nombre="";
    static String apellido1="";
    static String apellido2="";
    static String correo="";
    static String contrasenna="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText3);
        EditText editText3 = findViewById(R.id.editText4);
        EditText editText4 = findViewById(R.id.editText5);
        EditText editText5 = findViewById(R.id.editText6);
        editText.setText(nombre);
        editText2.setText(apellido1);
        editText5.setText(apellido2);
        editText3.setText(correo);
        editText4.setText(contrasenna);
    }

    public void toRegister2(View view){
        EditText editText = findViewById(R.id.editText);
        EditText editText2 = findViewById(R.id.editText3);
        EditText editText3 = findViewById(R.id.editText4);
        EditText editText4 = findViewById(R.id.editText5);
        EditText editText5 = findViewById(R.id.editText6);
        nombre = editText.getText().toString();
        apellido1 = editText2.getText().toString();
        apellido2 = editText5.getText().toString();
        correo = editText3.getText().toString();
        contrasenna = editText4.getText().toString();
        if(editText.length()!=0 && editText2.length()!=0 && editText3.length()!=0 && editText4.length()!=0 && editText5.length()!=0){
            Intent intent= new Intent(Registrar1.this, Registrar2.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(Registrar1.this,"Ingrese todos los datos solicitados",Toast.LENGTH_SHORT).show();
        }
    }
}
