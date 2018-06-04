package com.enigma.levy;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ArticuloActivity extends AppCompatActivity {

    String mode;
    int position;

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

        position = getIntent().getIntExtra("position", -1);
        mode = getIntent().getStringExtra("mode");

        collapsingToolbar.setTitle(Principal.articulosEnlinea.get(position).getNombre());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(ArticuloActivity.this).load(Principal.articulosEnlinea.get(position).getImagen()).into(placePicutre);

        TextView textView = findViewById(R.id.textView6);
        TextView textView1 = findViewById(R.id.textView7);
        TextView textView2 = findViewById(R.id.textView8);
        TextView textView3 = findViewById(R.id.textView10);
        textView.setText(Principal.articulosEnlinea.get(position).getNombre());
        textView1.setText("Precio: "+Principal.articulosEnlinea.get(position).getPrecio());
        textView2.setText("Tienda: "+Principal.articulosEnlinea.get(position).getTienda());
        textView3.setText(Principal.articulosEnlinea.get(position).getDescripcion());
    }

    public void toStore(View view){
        if(mode.equals("store")){
        Intent intent= new Intent(ArticuloActivity.this,StoreActivity.class);
        startActivity(intent);}
        if(mode.equals("online")){
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Principal.articulosEnlinea.get(position).getUrl()));
            startActivity(i);
        }
        if(mode.equals("user")){
            Intent intent= new Intent(ArticuloActivity.this,PerfilUserActivity.class);
            startActivity(intent);
        }
    }

    public void toComment(View view){
            Intent intent= new Intent(ArticuloActivity.this,CommentActivity.class);
            startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
