import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * author: Pedro Martínez Herrero
 * @since 09/12/2025
 * @until 09/12/2025
 * Actividad: clase PedidoApp
 */

public class PedidosApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PedidosApp");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Crear pedido
        Pedido pedido = new Pedido();

        System.out.println("Ingresa el código del Pedido: ");
        String codigoPedido = sc.nextLine();
        pedido.setCodigo(codigoPedido);

        System.out.println("Ingresa el ID del cliente: ");
        Long idCliente = sc.nextLong();
        pedido.setIdCliente(idCliente);

        List<Producto> losProductos = new ArrayList<>();

        // Crear lista de productos
        Producto p1 = new Producto("PlayStation 5", 549.99, 10);
        Producto p2 = new Producto("Nintendo Switch 2", 499.99, 15);

        losProductos.add(p1);
        losProductos.add(p2);

        // Asignar los productos al pedido
        pedido.setLosProductos(losProductos);

        // Guardar objetos
        em.persist(pedido);

        // Confirmar cambios
        em.getTransaction().commit();

        // Cerrar conexiones
        em.close();
        emf.close();
        sc.close();

        System.out.println("\nPedido guardado correctamente en ObjectDB:");
        System.out.println(" - Código: " + pedido.getCodigo());
        System.out.println(" - ID Cliente: " + pedido.getIdCliente());
        System.out.println(" - Nº Productos: " + pedido.getLosProductos().size());
    }
}