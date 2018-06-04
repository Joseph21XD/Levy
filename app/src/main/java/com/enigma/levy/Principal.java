package com.enigma.levy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Datos.Articulo;
import Datos.Ebay;
import Datos.JsonTask;

public class Principal extends AppCompatActivity {
    public static ArrayList<Articulo> articulosEnlinea = new ArrayList<>();
    public static ArrayList<Articulo> articulosUsuarios = new ArrayList<>();
    public static ArrayList<Articulo> articulosStore = new ArrayList<>();
    ArrayList<Fragment> fragments = new ArrayList<>();
    public static int mode= R.layout.item_list;
    SharedPreferences sharedPreferences;
    int EbayCont;
    int searchPage=1;
    static String find;

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

        articulosEnlinea.clear();
        openEbay(find);
        articulosStore.add(new Articulo("Carta YUGIOH Obelisco",12.5,"https://ugc.kn3.net/i/origin/http://perso.wanadoo.es/algrfgr/obelisk.jpg","hola","Amazon.com"));
        articulosStore.add(new Articulo("Carta YUGIOH Slyfer",12.5,"https://orig00.deviantart.net/a4c6/f/2009/286/6/a/slifer_the_sky_dragon_by_bloodgod741.jpg","hola","Ebay"));
        articulosStore.add(new Articulo("Carta YUGIOH Dragón alado de Ra",12.5,"https://ugc.kn3.net/i/origin/http://2.bp.blogspot.com/_atS2u-GxoBw/TCUYPgCUNOI/AAAAAAAAAW0/DtomF5obcGo/s1600/ra1.jpg","hola","Amazon.com"));
        articulosUsuarios.add(new Articulo("Carta YUGIOH Obelisco",12.5,"https://ugc.kn3.net/i/origin/http://perso.wanadoo.es/algrfgr/obelisk.jpg","hola","Amazon.com"));
        articulosUsuarios.add(new Articulo("Carta YUGIOH Slyfer",12.5,"https://orig00.deviantart.net/a4c6/f/2009/286/6/a/slifer_the_sky_dragon_by_bloodgod741.jpg","hola","Ebay"));
        articulosUsuarios.add(new Articulo("Carta YUGIOH Dragón alado de Ra",12.5,"https://ugc.kn3.net/i/origin/http://2.bp.blogspot.com/_atS2u-GxoBw/TCUYPgCUNOI/AAAAAAAAAW0/DtomF5obcGo/s1600/ra1.jpg","hola","Amazon.com"));
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
        fragments.add(new ListContentFragment());
        fragments.add(new ListContentFragmentStore());
        fragments.add(new ListContentFragmentUser());
        fragments.add(new PerfilFragment());
        adapter.addFragment(fragments.get(0), "");
        adapter.addFragment(fragments.get(1), "");
        adapter.addFragment(fragments.get(2), "");
        adapter.addFragment(fragments.get(3), "");
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
                articulosEnlinea.clear();
                ListContentFragment listContentFragment= (ListContentFragment) fragments.get(0);
                find= query.replaceAll(" ","%20");
                openEbay(find);
                listContentFragment.setAdapter(articulosEnlinea);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
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


    public void openEbay(String busqueda){
        JsonTask jsonTask= new JsonTask();
        String url= "https://svcs.ebay.com/services/search/FindingService/v1" +
                "?OPERATION-NAME=findItemsByKeywords&SERVICE-VERSION=1.0.0" +
                "&SECURITY-APPNAME="+getString(R.string.ebayKey)+"&RESPONSE-DATA-FORMAT=JSON" +
                "&callback=_cb_findItemsByKeywords&REST-PAYLOAD" +
                "&keywords="+busqueda+
                "&paginationInput.entriesPerPage=6" +
                "&paginationInput.pageNumber="+searchPage+
                "&GLOBAL-ID=EBAY-US" +
                "&siteid=0";
        try {
            String result= jsonTask.execute(url).get();
            EbayCont= Ebay.obtenerDatosEbay(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
