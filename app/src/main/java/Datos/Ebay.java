package Datos;

import android.util.Log;

import com.enigma.levy.Principal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ramir on 6/2/2018.
 */

public class Ebay {
    public static int obtenerDatosEbay(String resultado) throws JSONException {
        ArrayList<Articulo> arrayList= new ArrayList<>();
        resultado= resultado.substring(28);
        resultado= resultado.substring(0,resultado.length()-1);
        JSONObject obj = new JSONObject(resultado);
        JSONArray find = obj.getJSONArray("findItemsByKeywordsResponse");
        obj = find.getJSONObject(0);
        find = obj.getJSONArray("searchResult");
        JSONArray page = obj.getJSONArray("paginationOutput");
        obj = find.getJSONObject(0);
        int cant= Integer.parseInt(obj.getString("@count"));
        find = obj.getJSONArray("item");
        for(int i=0; i<cant; i++){
            JSONObject item = find.getJSONObject(i);
            String itemId= item.getJSONArray("itemId").getString(0);
            String title= item.getJSONArray("title").getString(0);
            String galleryURL= item.getJSONArray("galleryURL").getString(0);
            String viewItemURL= item.getJSONArray("viewItemURL").getString(0);
            String location= item.getJSONArray("location").getString(0);
            String coin= item.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).getString("@currencyId");
            String price= item.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice").getJSONObject(0).getString("__value__");
            //Log.d("ITEM", itemId+" "+title+" "+galleryURL+" "+location+" "+coin+" "+price);
            Principal.articulosEnlinea.add(new Articulo(title, Double.parseDouble(price), galleryURL,"Id: "+itemId+"\nUbicación: "+location+"\nMoneda: "+coin,"EBAY.com",viewItemURL));
        }
        int pages = page.getJSONObject(0).getJSONArray("totalPages").getInt(0);
        //Log.d("PAGES",pages+"");
        return pages;

    }
}
