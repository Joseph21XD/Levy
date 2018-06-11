package com.enigma.levy;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import Datos.Store;

public class StoreActivity extends AppCompatActivity {

    static Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int position = getIntent().getIntExtra("position", 0);
        //Av. 4 San José 9.932564, -84.080290
        store = ArticuloActivity.store;

        collapsingToolbar.setTitle(store.getNombre());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(StoreActivity.this).load(store.getImagen()).into(placePicutre);

        TextView textView = findViewById(R.id.textView6);
        TextView textView3 = findViewById(R.id.textView10);
        TextView textView2 = findViewById(R.id.textView11);
        textView.setText(store.getNombre());
        textView3.setText(store.getDescripcion());
        textView2.setText(store.getDireccion());
    }

    public void toMap(View view){
        Intent intent= new Intent(StoreActivity.this,MapsActivity.class);
        startActivity(intent);
    }
}
