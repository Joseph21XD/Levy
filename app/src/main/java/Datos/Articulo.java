package Datos;

/**
 * Created by ramir on 5/27/2018.
 */

public class Articulo {
    public String nombre;
    public Double precio;
    public String imagen;
    public String descripcion;
    public String tienda;
    public String url;

    public Articulo(String nombre, Double precio, String imagen, String descripcion, String tienda) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.tienda= tienda;
    }
    public Articulo(String nombre, Double precio, String imagen, String descripcion, String tienda, String url) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.tienda= tienda;
        this.url= url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
