import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * author: Pedro Martínez Herrero
 * @since 04/12/2025
 * @until 04/12/2025
 * Actividad: clase Producto
 */

@Entity
public class Producto {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private double precio;
    private int stock;

    public Producto() {
        // Constructor por defecto obligatorio
    }

    public Producto(String nombre, double precio, int stock) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}