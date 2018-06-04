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

public class StoreActivity extends AppCompatActivity {

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

        collapsingToolbar.setTitle(Principal.articulosEnlinea.get(position).getNombre());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(StoreActivity.this).load(Principal.articulosEnlinea.get(position).getImagen()).into(placePicutre);

        TextView textView = findViewById(R.id.textView6);
        TextView textView3 = findViewById(R.id.textView10);
        textView.setText(Principal.articulosEnlinea.get(position).getNombre());
        textView3.setText(Principal.articulosEnlinea.get(position).getDescripcion());
    }

    public void toMap(View view){
        Intent intent= new Intent(StoreActivity.this,MapsActivity.class);
        startActivity(intent);
    }
}
