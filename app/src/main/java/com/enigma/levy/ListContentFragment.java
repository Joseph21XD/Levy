/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.enigma.levy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import Datos.Articulo;
import Datos.Ebay;
import Datos.JsonTask;

/**
 * Provides UI for the view with List.
 */
public class ListContentFragment extends Fragment {
    ContentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        Log.d("ABRIR","HOLA");
        if(Principal.articulosEnlinea.isEmpty())
            openEbay(Principal.find);
        adapter = new ContentAdapter(recyclerView.getContext(), Principal.articulosEnlinea);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;
        public TextView tienda;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(Principal.mode, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.picture);
            name = (TextView) itemView.findViewById(R.id.name);
            if(Principal.mode==R.layout.item_card || Principal.mode==R.layout.item_list){
            description = (TextView) itemView.findViewById(R.id.comment);
            tienda = (TextView) itemView.findViewById(R.id.store);}
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
                    Intent intent = new Intent(context, ArticuloActivity.class);
                    intent.putExtra("position", getAdapterPosition());
                    intent.putExtra("mode", "online");
                    context.startActivity(intent);
                }
            });
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        ArrayList<Articulo> articulos;
        Context context;

        public ContentAdapter(Context context, ArrayList<Articulo> articulos) {
            this.articulos= articulos;
            this.context= context;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Glide.with(context).load(Uri.parse(articulos.get(position).getImagen())).into(holder.avator);
            holder.name.setText(articulos.get(position).nombre);
            if(Principal.mode==R.layout.item_card || Principal.mode==R.layout.item_list){
            holder.description.setText(articulos.get(position).getPrecio()+"");
            holder.tienda.setText(articulos.get(position).getTienda());}

        }

        @Override
        public int getItemCount() {
            return articulos.size();
        }
    }

    public void setAdapter(ArrayList<Articulo> arts){
        try {
            adapter.articulos = arts;
            adapter.notifyDataSetChanged();
        }
        catch (Exception e){

        }
    }

    public void openEbay(String busqueda){
        JsonTask jsonTask= new JsonTask();
        String url= "https://svcs.ebay.com/services/search/FindingService/v1" +
                "?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0" +
                "&SECURITY-APPNAME="+getString(R.string.ebayKey)+"&RESPONSE-DATA-FORMAT=JSON" +
                "&callback=_cb_findItemsByKeywords&REST-PAYLOAD" +
                "&keywords="+busqueda+
                "&paginationInput.entriesPerPage=6" +
                "&paginationInput.pageNumber="+Principal.searchPage+
                "&GLOBAL-ID=EBAY-US" +
                "&siteid=0";
        try {
            String result= jsonTask.execute(url).get();
            Principal.EbayCont= Ebay.obtenerDatosEbay(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
