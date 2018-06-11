package Datos;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.MenuItem;

import com.enigma.levy.ArticuloActivity;
import com.enigma.levy.MainActivity;
import com.enigma.levy.PerfilUserActivity;
import com.enigma.levy.Principal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by ramir on 6/8/2018.
 */

public class BackendConnection {
    static String dominio= "http://backend.raulgccr.com/";

    public static Usuario Login(String correo, String contraseña){
        JsonTask jsonTask = new JsonTask();
        correo = DataParserJ.parsear(correo);
        contraseña = DataParserJ.parsear(contraseña);
        String resultado;
        Log.d("CORREO", correo);
        try {
            resultado= jsonTask.execute(dominio+"users/login/"+correo+"/"+contraseña+".json").get();
            JSONObject object = new JSONObject(resultado);
            JSONArray array = object.getJSONArray("usuario");
            if(array.length()!=0){
                String id= array.getJSONObject(0).getString("id");
                String nombre= DataParserJ.deparsear(array.getJSONObject(0).getString("nombre"));
                String apellido1= DataParserJ.deparsear(array.getJSONObject(0).getString("primerApellido"));
                String apellido2= DataParserJ.deparsear(array.getJSONObject(0).getString("segundoApellido"));
                String imagen= DataParserJ.deparsear(array.getJSONObject(0).getString("foto"));
                correo= DataParserJ.deparsear(array.getJSONObject(0).getString("correo"));
                String contrasenna= DataParserJ.deparsear(array.getJSONObject(0).getString("contrasenna"));
                String token= array.getJSONObject(0).getString("token");
                return new Usuario(id,nombre,apellido1,apellido2,imagen,correo,contrasenna,token);}
            else return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Usuario LoginToken(String token) {
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"users/login/"+token+".json").get();
            JSONObject object = new JSONObject(resultado);
            JSONArray array = object.getJSONArray("usuario");
            if(array.length()!=0){
                String id= array.getJSONObject(0).getString("id");
                String nombre= DataParserJ.deparsear(array.getJSONObject(0).getString("nombre"));
                String apellido1= DataParserJ.deparsear(array.getJSONObject(0).getString("primerApellido"));
                String apellido2= DataParserJ.deparsear(array.getJSONObject(0).getString("segundoApellido"));
                String imagen= DataParserJ.deparsear(array.getJSONObject(0).getString("foto"));
                String correo= DataParserJ.deparsear(array.getJSONObject(0).getString("correo"));
                String contrasenna= DataParserJ.deparsear(array.getJSONObject(0).getString("contrasenna"));
                token= array.getJSONObject(0).getString("token");
                return new Usuario(id,nombre,apellido1,apellido2,imagen,correo,contrasenna,token);}
            else return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Usuario Registrar(String nombre, String apellido1, String apellido2, String correo, String contrasenna, String imagen){
        JsonTask jsonTask = new JsonTask();
        nombre = DataParserJ.parsear(nombre);
        apellido1 = DataParserJ.parsear(apellido1);
        apellido2 = DataParserJ.parsear(apellido2);
        correo = DataParserJ.parsear(correo);
        contrasenna = DataParserJ.parsear(contrasenna);
        imagen = DataParserJ.parsear(imagen);
        Log.d("NOMBRE PARSEADO", nombre);
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"users/add/"+nombre+"/"+apellido1+"/"+apellido2+"/"+correo+"/"+contrasenna+"/"+imagen+".json").get();
            JSONObject array = new JSONObject(resultado).getJSONObject("user");
                String id= array.getString("id");
                nombre= DataParserJ.deparsear(array.getString("nombre"));
                apellido1= DataParserJ.deparsear(array.getString("primerApellido"));
                apellido2= DataParserJ.deparsear(array.getString("segundoApellido"));
                imagen= DataParserJ.deparsear(array.getString("foto"));
                correo= DataParserJ.deparsear(array.getString("correo"));
                contrasenna= DataParserJ.deparsear(array.getString("contrasenna"));
                String token= array.getString("token");
                Log.d("NOMBRE DEPARSEADO", nombre);
                return new Usuario(id,nombre,apellido1,apellido2,imagen,correo,contrasenna,token);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Articulo NuevoArticulo(String nombre, String desc, Double price, String tienda, String imagen, String token){
        JsonTask jsonTask = new JsonTask();
        nombre = DataParserJ.parsear(nombre);
        desc = DataParserJ.parsear(desc);
        imagen = DataParserJ.parsear(imagen);
        String precio = DataParserJ.parsear(price+"");
        Log.d("NOMBRE PARSEADO", nombre);
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"articles/add/"+nombre+"/"+precio+"/"+desc+"/"+tienda+"/"+imagen+"/"+2+"/"+token+".json").get();
            JSONObject array = new JSONObject(resultado);
            String id= array.getString("id");
            nombre= DataParserJ.deparsear(array.getString("nombre"));
            desc= DataParserJ.deparsear(array.getString("descripcion"));
            imagen= DataParserJ.deparsear(array.getString("image"));
            price= Double.parseDouble(DataParserJ.deparsear(array.getString("precio")));
            tienda= DataParserJ.deparsear(array.getString("proveedor"));
            String modo= DataParserJ.deparsear(array.getString("modo"));
            return new Articulo(id,nombre,price,imagen,desc,tienda,modo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getArticulos(){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"articles.json").get();
            JSONArray array = new JSONArray(resultado);
            for(int i=0; i<array.length(); i++) {
                String id = array.getJSONObject(i).getString("id");
                String nombre = DataParserJ.deparsear(array.getJSONObject(i).getString("nombre"));
                String desc = DataParserJ.deparsear(array.getJSONObject(i).getString("descripcion"));
                String imagen = DataParserJ.deparsear(array.getJSONObject(i).getString("image"));
                Double price = Double.parseDouble(DataParserJ.deparsear(array.getJSONObject(i).getString("precio")));
                String tienda = DataParserJ.deparsear(array.getJSONObject(i).getString("proveedor"));
                String modo = DataParserJ.deparsear(array.getJSONObject(i).getString("modo"));
                if(modo.equals("1")){
                    Principal.articulosStore.add(new Articulo(id,nombre,price,imagen,desc,tienda,modo));
                }
                else{
                    if(tienda.equals(MainActivity.user.getId())){
                        Principal.articulosPersonales.add(new Articulo(id,nombre,price,imagen,desc,tienda,modo));
                    }
                    Principal.articulosUsuarios.add(new Articulo(id,nombre,price,imagen,desc,tienda,modo));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static int Rating(String id, String token){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"scores/indexscore/"+id+"/"+token+".json").get();
            JSONObject object = new JSONObject(resultado);
            String s= object.getString("score");
            if (s.equals("false"))
                    return 0;
            return Integer.parseInt(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Usuario ActualizaUsuario(String id, String nombre, String apellido1, String apellido2, String imagen, String correo, String contrasenna, String token){
        JsonTask jsonTask = new JsonTask();
        nombre = DataParserJ.parsear(nombre);
        apellido1 = DataParserJ.parsear(apellido1);
        apellido2 = DataParserJ.parsear(apellido2);
        correo = DataParserJ.parsear(correo);
        contrasenna = DataParserJ.parsear(contrasenna);
        imagen = DataParserJ.parsear(imagen);
        Log.d("NOMBRE PARSEADO", nombre);
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"users/modify/"+id+"/"+nombre+"/"+apellido1+"/"+apellido2+"/"+correo+"/"+contrasenna+"/"+imagen+"/"+token+".json").get();
            JSONObject object = new JSONObject(resultado);
            JSONObject array= object.getJSONObject("user");
            id= array.getString("id");
            nombre= DataParserJ.deparsear(array.getString("nombre"));
            apellido1= DataParserJ.deparsear(array.getString("primerApellido"));
            apellido2= DataParserJ.deparsear(array.getString("segundoApellido"));
            imagen= DataParserJ.deparsear(array.getString("foto"));
            correo= DataParserJ.deparsear(array.getString("correo"));
            contrasenna= DataParserJ.deparsear(array.getString("contrasenna"));
            token= array.getString("token");
            Log.d("NOMBRE DEPARSEADO", nombre);
            return new Usuario(id,nombre,apellido1,apellido2,imagen,correo,contrasenna,token);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Comentario> ObtenerComentarios(String id, String token){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        ArrayList<Comentario> comentarios= new ArrayList<>();
        try {
            resultado= jsonTask.execute(dominio+"comments/mostrar/"+id+"/"+token+".json").get();
            JSONObject object = new JSONObject(resultado);
            JSONArray comments= object.getJSONArray("commentarios");
            JSONArray users= object.getJSONArray("usuarios");
            for(int i=0; i<comments.length(); i++){
                id= comments.getJSONObject(i).getString("id");
                String persona= DataParserJ.deparsear(comments.getJSONObject(i).getString("persona"));
                String cometario= DataParserJ.deparsear(comments.getJSONObject(i).getString("comentario"));
                String idPersona= DataParserJ.deparsear(users.getJSONObject(i).getString("id"));
                String nombre= DataParserJ.deparsear(users.getJSONObject(i).getString("nombre"));
                String imagen= DataParserJ.deparsear(users.getJSONObject(i).getString("foto"));
                if(persona.equals(idPersona)){
                    comentarios.add(new Comentario(Integer.parseInt(idPersona),nombre,cometario,imagen));
                }
            }
            return comentarios;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comentarios;
    }

    public static void search(String query, String token) {
        JsonTask jsonTask = new JsonTask();
        query= DataParserJ.parsear(query);
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"articles/find/"+query+"/"+token+".json").get();
            JSONObject object = new JSONObject(resultado);
            JSONArray array = object.getJSONArray("articulos");
            for(int i=0; i<array.length(); i++) {
                String id = array.getJSONObject(i).getString("id");
                String nombre = DataParserJ.deparsear(array.getJSONObject(i).getString("nombre"));
                String desc = DataParserJ.deparsear(array.getJSONObject(i).getString("descripcion"));
                String imagen = DataParserJ.deparsear(array.getJSONObject(i).getString("image"));
                Double price = Double.parseDouble(DataParserJ.deparsear(array.getJSONObject(i).getString("precio")));
                String tienda = DataParserJ.deparsear(array.getJSONObject(i).getString("proveedor"));
                String modo = DataParserJ.deparsear(array.getJSONObject(i).getString("modo"));
                if(modo.equals("1")){
                    Principal.articulosStore.add(new Articulo(id,nombre,price,imagen,desc,tienda,modo));
                }
                else{
                    if(tienda.equals(MainActivity.user.getId())){
                        Principal.articulosPersonales.add(new Articulo(id,nombre,price,imagen,desc,tienda,modo));
                    }
                    Principal.articulosUsuarios.add(new Articulo(id,nombre,price,imagen,desc,tienda,modo));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Articulo ActualizaArticulo(String id, String nombre, String desc, Double price, String tienda, String imagen, String token){
        JsonTask jsonTask = new JsonTask();
        nombre = DataParserJ.parsear(nombre);
        desc = DataParserJ.parsear(desc);
        imagen = DataParserJ.parsear(imagen);
        String precio = DataParserJ.parsear(price+"");
        Log.d("NOMBRE PARSEADO", nombre);
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"articles/modify/"+id+"/"+nombre+"/"+precio+"/"+desc+"/"+tienda+"/"+imagen+"/"+2+"/"+token+".json").get();
            JSONObject array = new JSONObject(resultado);
            id= array.getString("id");
            nombre= DataParserJ.deparsear(array.getString("nombre"));
            desc= DataParserJ.deparsear(array.getString("descripcion"));
            imagen= DataParserJ.deparsear(array.getString("image"));
            price= Double.parseDouble(DataParserJ.deparsear(array.getString("precio")));
            tienda= DataParserJ.deparsear(array.getString("proveedor"));
            String modo= DataParserJ.deparsear(array.getString("modo"));
            return new Articulo(id,nombre,price,imagen,desc,tienda,modo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void BorrarArticulo(String id, String token){
        JsonTask jsonTask = new JsonTask();
        try {
            jsonTask.execute(dominio+"articles/delete/"+id+"/"+token+".json").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static Store getTienda(String id){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"stores/"+id+".json").get();
            JSONObject array = new JSONObject(resultado);
            id= array.getString("id");
            String nombre= DataParserJ.deparsear(array.getString("nombre"));
            String desc= DataParserJ.deparsear(array.getString("descripcion"));
            String imagen= DataParserJ.deparsear(array.getString("imagen"));
            String lat= DataParserJ.deparsear(array.getString("latitud"));
            String lon= DataParserJ.deparsear(array.getString("longitud"));
            String dir= DataParserJ.deparsear(array.getString("direccion"));
            return new Store(id,nombre,desc,lat,lon,dir,imagen);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void comentar(String id, String id1, String s, String token) {
        JsonTask jsonTask = new JsonTask();
        s= DataParserJ.parsear(s);
        try {
            jsonTask.execute(dominio+"comments/add/"+id+"/"+id1+"/"+s+"/"+token+".json").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static Usuario getUsuario(String id) {
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"users/"+id+".json").get();
            JSONObject array = new JSONObject(resultado);
            id= array.getString("id");
            String nombre= DataParserJ.deparsear(array.getString("nombre"));
            String apellido1= DataParserJ.deparsear(array.getString("primerApellido"));
            String apellido2= DataParserJ.deparsear(array.getString("segundoApellido"));
            String imagen= DataParserJ.deparsear(array.getString("foto"));
            return new Usuario(id,nombre,apellido1,apellido2,imagen,-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void getArticulosUser(String identifier){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"articles.json").get();
            JSONArray array = new JSONArray(resultado);
            for(int i=0; i<array.length(); i++) {
                String id = array.getJSONObject(i).getString("id");
                String nombre = DataParserJ.deparsear(array.getJSONObject(i).getString("nombre"));
                String desc = DataParserJ.deparsear(array.getJSONObject(i).getString("descripcion"));
                String imagen = DataParserJ.deparsear(array.getJSONObject(i).getString("image"));
                Double price = Double.parseDouble(DataParserJ.deparsear(array.getJSONObject(i).getString("precio")));
                String tienda = DataParserJ.deparsear(array.getJSONObject(i).getString("proveedor"));
                String modo = DataParserJ.deparsear(array.getJSONObject(i).getString("modo"));
                if(modo.equals("2") && tienda.equals(identifier)){
                    PerfilUserActivity.userArticulos.add(new Articulo(id,nombre,price,imagen,desc,tienda,modo));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Score getIdRating(String id,String id1, String token){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"scores/modify/"+id+"/"+id1+"/"+token+".json").get();
            JSONObject object = new JSONObject(resultado).getJSONArray("score").getJSONObject(0);
            String s= object.getString("calificacion");
            String k= object.getString("id");
            return new Score(k, Integer.parseInt(s));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateIdRating(String id,String cal,String cal1,String rate, String token){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"scores/modify/"+id+"/"+cal+"/"+cal1+"/"+rate+"/"+token+".json").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void newIdRating(String cal,String cal1,String rate, String token){
        JsonTask jsonTask = new JsonTask();
        String resultado;
        try {
            resultado= jsonTask.execute(dominio+"scores/add/"+cal+"/"+cal1+"/"+rate+"/"+token+".json").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
