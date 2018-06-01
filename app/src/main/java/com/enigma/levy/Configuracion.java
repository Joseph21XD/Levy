package com.enigma.levy;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Configuracion extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioButton radioButton3;

    public void radioClick(View view){

        if(view.getTag().equals("lista")){
            radioButton.setChecked(true);
            radioButton2.setChecked(false);
            radioButton3.setChecked(false);
            sharedPreferences.edit().putString("list","lista").apply();
        }
        else if(view.getTag().equals("carta")){
            radioButton.setChecked(false);
            radioButton2.setChecked(false);
            radioButton3.setChecked(true);
            sharedPreferences.edit().putString("list","carta").apply();
        }
        else{
            radioButton.setChecked(false);
            radioButton2.setChecked(true);
            radioButton3.setChecked(false);
            sharedPreferences.edit().putString("list","azulejo").apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int postion = getIntent().getIntExtra("position", 0);
        Resources resources = getResources();

        collapsingToolbar.setTitle(MainActivity.user.getNombre()+ " " + MainActivity.user.getApellido());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(Configuracion.this).load(MainActivity.user.getImagen()).into(placePicutre);

        sharedPreferences= this.getSharedPreferences("com.enigma.levy", getApplicationContext().MODE_PRIVATE);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);

    }
}
