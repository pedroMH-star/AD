import jakarta.persistence.*;
import java.util.List;

/**
 * author: Pedro Martínez Herrero
 * @since 09/12/2025
 * @until 09/12/2025
 * Actividad: clase Pedido
 */

@Entity
public class Pedido {

    @Id
    @GeneratedValue
    private Long id; // ID real autogenerado por ObjectDB

    private Long idCliente; // El id cliente
    private String codigo;   // Código del pedido (lo introduce el usuario)

    @OneToMany(cascade = CascadeType.ALL)
    private List<Producto> losProductos;

    public Pedido() {
        // Constructor por defecto obligatorio
    }

    public Pedido(String codigo, Long idCliente) {
        this.codigo = codigo;
        this.idCliente = idCliente;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<Producto> getLosProductos() {
        return losProductos;
    }
    public void setLosProductos(List<Producto> losProductos) {
        this.losProductos = losProductos;
    }
}
