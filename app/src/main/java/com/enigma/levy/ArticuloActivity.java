package com.enigma.levy;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ArticuloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        int position = getIntent().getIntExtra("position", -1);

        collapsingToolbar.setTitle(Principal.articulos.get(position).getNombre());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(ArticuloActivity.this).load(Principal.articulos.get(position).getImagen()).into(placePicutre);

        TextView textView = findViewById(R.id.textView6);
        TextView textView1 = findViewById(R.id.textView7);
        TextView textView2 = findViewById(R.id.textView8);
        TextView textView3 = findViewById(R.id.textView10);
        textView.setText(Principal.articulos.get(position).getNombre());
        textView1.setText("Precio: "+Principal.articulos.get(position).getPrecio());
        textView2.setText("Tienda: "+Principal.articulos.get(position).getTienda());
        textView3.setText(Principal.articulos.get(position).getDescripcion());
    }

    public void toStore(View view){
        Intent intent= new Intent(ArticuloActivity.this,StoreActivity.class);
        startActivity(intent);
    }
}
