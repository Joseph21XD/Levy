package Datos;

/**
 * Created by ramir on 5/27/2018.
 */

public class Usuario {
    String nombre;
    String apellido;
    String imagen;
    int rating;

    public Usuario(String nombre, String apellido, String imagen, int rating) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.imagen = imagen;
        this.rating= rating;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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
}
