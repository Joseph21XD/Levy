package Datos;

/**
 * Created by ramir on 5/27/2018.
 */

public class Usuario {
    String id;
    String nombre;
    String apellido1;
    String apellido2;
    String imagen;
    String correo;
    String contrasenna;
    String token;
    int rating = -1;

    public Usuario(String id, String nombre, String apellido1, String apellido2, String imagen, int rating) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.imagen = imagen;
        this.rating= rating;
        this.id = id;
    }

    public Usuario(String id, String nombre, String apellido1, String apellido2, String imagen, String correo, String contrasenna, String token) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.imagen = imagen;
        this.correo = correo;
        this.contrasenna = contrasenna;
        this.token = token;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido) {
        this.apellido1 = apellido;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido) {
        this.apellido2 = apellido;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
