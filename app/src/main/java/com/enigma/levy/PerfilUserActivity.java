package com.enigma.levy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Datos.Articulo;
import Datos.BackendConnection;
import Datos.Score;
import Datos.Usuario;

public class PerfilUserActivity extends AppCompatActivity {
    public static ArrayList<Articulo> userArticulos = new ArrayList<>();
    public static Usuario user;
    Score score;
    TextView textViewRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));
        user = ArticuloActivity.usuario;
        userArticulos.clear();
        BackendConnection.getArticulosUser(user.getId());
        user.setRating((BackendConnection.Rating(user.getId(),MainActivity.user.getToken())));

        collapsingToolbar.setTitle(user.getNombre()+" "+user.getApellido1()+" "+user.getApellido2());
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primaryColor));

        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        Glide.with(PerfilUserActivity.this).load(user.getImagen()).into(placePicutre);

        RecyclerView recyclerView= findViewById(R.id.recycler);
        PerfilUserActivity.ContentAdapter adapter = new PerfilUserActivity.ContentAdapter(PerfilUserActivity.this, userArticulos);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PerfilUserActivity.this));

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        score= BackendConnection.getIdRating(user.getId(),MainActivity.user.getId(),MainActivity.user.getToken());
        if(score!=null)
            ratingBar.setRating((float)score.getScore()/2);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    String rate= ((int)(v*2))+"";
                    if(score!=null){
                        BackendConnection.updateIdRating(score.getId(),user.getId(),MainActivity.user.getId(),rate,MainActivity.user.getToken());
                        score= BackendConnection.getIdRating(user.getId(),MainActivity.user.getId(),MainActivity.user.getToken());
                        user.setRating((BackendConnection.Rating(user.getId(),MainActivity.user.getToken())));
                    }
                    else{
                        BackendConnection.newIdRating(user.getId(),MainActivity.user.getId(),rate,MainActivity.user.getToken());
                        score= BackendConnection.getIdRating(user.getId(),MainActivity.user.getId(),MainActivity.user.getToken());
                        user.setRating((BackendConnection.Rating(user.getId(),MainActivity.user.getToken())));
                    }
                if(user.getRating()>=8){
                    textViewRate.setText("BUENA");
                    textViewRate.setTextColor(getResources().getColor(R.color.buena));
                }
                else if(user.getRating()>=4){
                    textViewRate.setText("MEDIA");
                    textViewRate.setTextColor(getResources().getColor(R.color.medio));
                }
                else{
                    textViewRate.setText("MALA");
                    textViewRate.setTextColor(getResources().getColor(R.color.mala));
                }
            }
        });

        textViewRate = findViewById(R.id.textView15);
        if(user.getRating()>=8){
            textViewRate.setText("BUENA");
            textViewRate.setTextColor(getResources().getColor(R.color.buena));
        }
        else if(user.getRating()>=4){
            textViewRate.setText("MEDIA");
            textViewRate.setTextColor(getResources().getColor(R.color.medio));
        }
        else{
            textViewRate.setText("MALA");
            textViewRate.setTextColor(getResources().getColor(R.color.mala));
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;
        public TextView tienda;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.picture);
            name = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.comment);
            tienda = (TextView) itemView.findViewById(R.id.store);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Context vcontext = v.getContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(vcontext);

                    final CharSequence[] items = new CharSequence[3];

                    items[0] = "Editar";
                    items[1] = "Eliminar";
                    items[2] = "Cancelar";

                    builder.setTitle("¿Qué desea hacer?")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case 0:
                                            Intent intent = new Intent(vcontext, EditActivity.class);
                                            intent.putExtra("position", getAdapterPosition());
                                            vcontext.startActivity(intent);
                                            break;
                                        case 1:
                                            break;
                                        case 2: break;
                                    }
                                }
                            }).setIcon(R.drawable.logo);

                    builder.create().show();
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<PerfilUserActivity.ViewHolder> {
        // Set numbers of List in RecyclerView.
        ArrayList<Articulo> articulos;
        Context context;

        public ContentAdapter(Context context, ArrayList<Articulo> articulos) {
            this.articulos= articulos;
            this.context= context;
        }
        @Override
        public PerfilUserActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PerfilUserActivity.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(PerfilUserActivity.ViewHolder holder, int position) {
            Glide.with(context).load(Uri.parse(articulos.get(position).getImagen())).into(holder.avator);
            holder.name.setText(articulos.get(position).nombre);
            holder.description.setText(articulos.get(position).getPrecio()+"");
            holder.tienda.setText(user.getNombre());

        }

        @Override
        public int getItemCount() {
            return articulos.size();
        }
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
