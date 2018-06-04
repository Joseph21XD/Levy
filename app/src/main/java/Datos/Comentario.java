package Datos;

/**
 * Created by ramir on 6/3/2018.
 */

public class Comentario {
    int id;
    String nombre;
    String comentario;
    String imagen;

    public Comentario(int id, String nombre, String comentario, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.comentario = comentario;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
