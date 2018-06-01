package com.enigma.levy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Ingresar1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar1);
    }

    public void toInsert2(View view){
        Intent intent= new Intent(Ingresar1.this,Ingresar2.class);
        startActivity(intent);
    }
}
