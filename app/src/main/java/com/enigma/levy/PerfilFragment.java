package com.enigma.levy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Datos.Articulo;

public class PerfilFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        RecyclerView recyclerView= getView().findViewById(R.id.recycler);
        PerfilFragment.ContentAdapter adapter = new PerfilFragment.ContentAdapter(recyclerView.getContext(), Principal.articulosEnlinea);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayout buttonLogout= getView().findViewById(R.id.layoutLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        LinearLayout buttonSettings= getView().findViewById(R.id.layoutSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings(v);
            }
        });

        RatingBar ratingBar = getView().findViewById(R.id.ratingBar);
        ratingBar.setRating((float) MainActivity.user.getRating()/2);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingBar.setRating((float) MainActivity.user.getRating()/2);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    public void logout(View view){
        Context context= view.getContext();
        Intent intent =new Intent(context,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    public void settings(View view){
        Context context= view.getContext();
        Intent intent =new Intent(context,Configuracion.class);
        context.startActivity(intent);
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
    public static class ContentAdapter extends RecyclerView.Adapter<PerfilFragment.ViewHolder> {
        // Set numbers of List in RecyclerView.
        ArrayList<Articulo> articulos;
        Context context;

        public ContentAdapter(Context context, ArrayList<Articulo> articulos) {
            this.articulos= articulos;
            this.context= context;
        }
        @Override
        public PerfilFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new PerfilFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(PerfilFragment.ViewHolder holder, int position) {
            Glide.with(context).load(Uri.parse(articulos.get(position).getImagen())).into(holder.avator);
            holder.name.setText(articulos.get(position).nombre);
            holder.description.setText(articulos.get(position).getPrecio()+"");
            holder.tienda.setText(articulos.get(position).getTienda());

        }

        @Override
        public int getItemCount() {
            return articulos.size();
        }
    }


}
