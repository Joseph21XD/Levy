package com.enigma.levy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Datos.Articulo;
import Datos.BackendConnection;
import Datos.Comentario;
import Datos.Usuario;

public class CommentActivity extends AppCompatActivity {
    ArrayList<Comentario> comentarios;
    SharedPreferences sharedPreferences;
    ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        sharedPreferences= this.getSharedPreferences("com.enigma.levy", getApplicationContext().MODE_PRIVATE);
        comentarios= BackendConnection.ObtenerComentarios(ArticuloActivity.articulo.getId(),sharedPreferences.getString("token",""));
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new CommentActivity.ContentAdapter(CommentActivity.this, comentarios);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
    }

    public void comentar(View view){
        EditText editText = findViewById(R.id.commenttext);
        BackendConnection.comentar(ArticuloActivity.articulo.getId(),MainActivity.user.getId(),editText.getText().toString(),MainActivity.user.getToken());
        comentarios.clear();
        comentarios= BackendConnection.ObtenerComentarios(ArticuloActivity.articulo.getId(),sharedPreferences.getString("token",""));
        setAdapter(comentarios);
    }

    public void setAdapter(ArrayList<Comentario> arts){
        try {
            adapter.comentarios = arts;
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public ImageView avator2;
        public TextView name;
        public TextView name2;
        public TextView comment;
        public TextView comment2;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_list_comment, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.picture);
            avator2 = (ImageView) itemView.findViewById(R.id.picture2);
            name = (TextView) itemView.findViewById(R.id.name);
            name2 = (TextView) itemView.findViewById(R.id.name2);
            comment = (TextView) itemView.findViewById(R.id.comment);
            comment2 = (TextView) itemView.findViewById(R.id.comment2);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Context vcontext = v.getContext();
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
    public static class ContentAdapter extends RecyclerView.Adapter<CommentActivity.ViewHolder> {
        // Set numbers of List in RecyclerView.
        ArrayList<Comentario> comentarios;
        Context context;

        public ContentAdapter(Context context, ArrayList<Comentario> comentarios) {
            this.comentarios= comentarios;
            this.context= context;
        }
        @Override
        public CommentActivity.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CommentActivity.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(CommentActivity.ViewHolder holder, int position) {
            Log.d("COMETARIO", comentarios.get(position).getId()+"/"+MainActivity.user.getId());
            if((comentarios.get(position).getId()+"").equals(MainActivity.user.getId())){
                Glide.with(context).load(Uri.parse(comentarios.get(position).getImagen())).into(holder.avator2);
                holder.name2.setText(comentarios.get(position).getNombre());
                holder.comment2.setText(comentarios.get(position).getComentario());
                holder.avator2.setVisibility(View.VISIBLE);
                holder.name2.setVisibility(View.VISIBLE);
                holder.comment2.setVisibility(View.VISIBLE);
            }
            else{
                Glide.with(context).load(Uri.parse(comentarios.get(position).getImagen())).into(holder.avator);
                holder.name.setText(comentarios.get(position).getNombre());
                holder.comment.setText(comentarios.get(position).getComentario());
                holder.avator.setVisibility(View.VISIBLE);
                holder.name.setVisibility(View.VISIBLE);
                holder.comment.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return comentarios.size();
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
