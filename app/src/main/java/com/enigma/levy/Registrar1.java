package com.enigma.levy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Registrar1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar1);
    }

    public void toRegister2(View view){
        Intent intent= new Intent(Registrar1.this, Registrar2.class);
        startActivity(intent);
    }
}
