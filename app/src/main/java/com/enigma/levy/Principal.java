package com.enigma.levy;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Datos.Articulo;

public class Principal extends AppCompatActivity {
    static ArrayList<Articulo> articulos= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

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


    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new ListContentFragment(), "EnLinea");
        adapter.addFragment(new ListContentFragment(), "Local");
        adapter.addFragment(new ListContentFragment(), "Usuarios");
        adapter.addFragment(new PerfilFragment(), "Perfil");
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
        return super.onCreateOptionsMenu(menu);
    }
    }
