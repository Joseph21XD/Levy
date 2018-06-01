package com.enigma.levy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Datos.Articulo;

public class Principal extends AppCompatActivity {
    static ArrayList<Articulo> articulos= new ArrayList<>();
    static int mode= R.layout.item_list;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        sharedPreferences= this.getSharedPreferences("com.enigma.levy", getApplicationContext().MODE_PRIVATE);
        String listMode= sharedPreferences.getString("list","lista");
        switch (listMode){
            case "carta": mode= R.layout.item_card;
                            break;
            case "lista":mode= R.layout.item_list;
                break;
            case "azulejo":mode= R.layout.item_tile;
                break;
        }


        articulos.add(new Articulo("Carta YUGIOH Obelisco",12.5,"https://ugc.kn3.net/i/origin/http://perso.wanadoo.es/algrfgr/obelisk.jpg","hola","Amazon.com"));
        articulos.add(new Articulo("Carta YUGIOH Slyfer",12.5,"https://orig00.deviantart.net/a4c6/f/2009/286/6/a/slifer_the_sky_dragon_by_bloodgod741.jpg","hola","Ebay"));
        articulos.add(new Articulo("Carta YUGIOH Dragón alado de Ra",12.5,"https://ugc.kn3.net/i/origin/http://2.bp.blogspot.com/_atS2u-GxoBw/TCUYPgCUNOI/AAAAAAAAAW0/DtomF5obcGo/s1600/ra1.jpg","hola","Amazon.com"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView= toolbar.findViewById(R.id.barText);
        ImageView imageView= toolbar.findViewById(R.id.barImage);

        textView.setText(MainActivity.user.getNombre()+" "+MainActivity.user.getApellido());
        Glide.with(Principal.this).load(MainActivity.user.getImagen()).into(imageView);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.online2).setTag("online");
        tabs.getTabAt(1).setIcon(R.drawable.local).setTag("local");
        tabs.getTabAt(2).setIcon(R.drawable.multy_user).setTag("multy");
        tabs.getTabAt(3).setIcon(R.drawable.user).setTag("user");
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getTag()+""){
                    case "online": tab.setIcon(R.drawable.online2);
                        break;
                    case "local":   tab.setIcon(R.drawable.local2);
                        break;
                    case "user":    tab.setIcon(R.drawable.user2);
                        break;
                    case "multy":   tab.setIcon(R.drawable.multy_user2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getTag()+""){
                    case "online": tab.setIcon(R.drawable.online);
                        break;
                    case "local":   tab.setIcon(R.drawable.local);
                        break;
                    case "user":    tab.setIcon(R.drawable.user);
                        break;
                    case "multy":   tab.setIcon(R.drawable.multy_user);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Principal.this,Ingresar1.class);
                startActivity(intent);
            }
        });


    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListContentFragment(), "");
        adapter.addFragment(new ListContentFragment(), "");
        adapter.addFragment(new ListContentFragment(), "");
        adapter.addFragment(new PerfilFragment(), "");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menusearch);
        SearchView searchView =
                (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("RESULT", "SUBMIT");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("RESULT", "CHANGE");
                return false;
            }
        });
        searchView.setDrawingCacheBackgroundColor(getResources().getColor(R.color.gray));
        searchView.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        ImageView searchClose = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        ImageView searchClose2 = (ImageView) searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.search);
        searchClose2.setImageResource(R.drawable.close);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.primaryTextColor));
        return super.onCreateOptionsMenu(menu);
    }
    }
