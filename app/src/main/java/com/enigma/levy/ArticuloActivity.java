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

import Datos.Articulo;
import Datos.BackendConnection;
import Datos.Store;
import Datos.Usuario;

public class ArticuloActivity extends AppCompatActivity {

    String mode;
    int position;
    static Articulo articulo;
    static Usuario usuario;
    static Store store;

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

        if(mode.equals("online")){
            Button button = findViewById(R.id.button6);
            button.setVisibility(View.INVISIBLE);
        }
        if(mode.equals("personal")){
            Button button = findViewById(R.id.button5);
            button.setVisibility(View.INVISIBLE);
        }
        TextView textView = findViewById(R.id.textView6);
        TextView textView1 = findViewById(R.id.textView7);
        TextView textView2 = findViewById(R.id.textView8);
        TextView textView3 = findViewById(R.id.textView10);
        ImageView placePicutre = (ImageView) findViewById(R.id.image);

        if(mode.equals("online"))
            articulo = Principal.articulosEnlinea.get(position);
        else if(mode.equals("store")){
            store = BackendConnection.getTienda(Principal.articulosStore.get(position).getTienda());
            articulo = Principal.articulosStore.get(position);
        }
        else if(mode.equals("personal"))
            articulo = Principal.articulosPersonales.get(position);
        else{
            usuario = BackendConnection.getUsuario(Principal.articulosStore.get(position).getTienda());
            articulo = Principal.articulosUsuarios.get(position);
        }

        collapsingToolbar.setTitle(articulo.getNombre());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));
        Glide.with(ArticuloActivity.this).load(articulo.getImagen()).into(placePicutre);
        textView.setText(articulo.getNombre());
        textView1.setText("Precio: "+articulo.getPrecio());
        if(mode.equals("store"))
            textView2.setText("Proveedor: "+store.getNombre());
        else if(mode.equals("user"))
            textView2.setText("Proveedor: "+usuario.getNombre());
        else if(mode.equals("personal"))
            textView2.setText("Proveedor: "+MainActivity.user.getNombre()+" "+MainActivity.user.getApellido1()+" "+MainActivity.user.getApellido2());
        else
            textView2.setText("Proveedor: "+articulo.getTienda());
        textView3.setText(articulo.getDescripcion());
    }

    public void toStore(View view){
        if(mode.equals("store")){
        Intent intent= new Intent(ArticuloActivity.this,StoreActivity.class);
        startActivity(intent);}
        if(mode.equals("online")){
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(articulo.getUrl()));
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

}
