package com.enigma.levy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import Datos.Articulo;

public class PerfilFragment extends Fragment {

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        RecyclerView recyclerView= getView().findViewById(R.id.recycler);
        PerfilFragment.ContentAdapter adapter = new PerfilFragment.ContentAdapter(recyclerView.getContext(), Principal.articulos);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ImageView buttonLogout= getView().findViewById(R.id.imageView4);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        ImageView buttonSettings= getView().findViewById(R.id.imageView5);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings(v);
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
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);
            tienda = (TextView) itemView.findViewById(R.id.list_tienda);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Context context = v.getContext();
                    /*Intent intent = new Intent(context, Main6Activity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);*/
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    /*Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);*/
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ListContentFragment.ViewHolder> {
        // Set numbers of List in RecyclerView.
        ArrayList<Articulo> articulos;
        Context context;

        public ContentAdapter(Context context, ArrayList<Articulo> articulos) {
            this.articulos= articulos;
            this.context= context;
        }
        @Override
        public ListContentFragment.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListContentFragment.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ListContentFragment.ViewHolder holder, int position) {
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
