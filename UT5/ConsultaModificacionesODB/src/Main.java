import jakarta.persistence.*;
import java.util.List;

/**
 * author: Pedro Martínez Herrero
 * @since 11/12/2025
 * @until 11/12/2025
 * Actividad: Consultas y actualizaciones
 */

public class Main {
    public static void main(String[] args) {

        // Conexión con ObjectDB
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("InventarioCompleto.odb");
        EntityManager em = emf.createEntityManager();

        // a) Productos con stock < 20
        System.out.println("### Productos con stock bajo (<20) ###");
        TypedQuery<Producto> consultaStock = em.createQuery(
                "SELECT p FROM Producto p WHERE p.stock < 20", Producto.class);

        List<Producto> productosBajoStock = consultaStock.getResultList();

        for (Producto p : productosBajoStock) {
            System.out.println(p);
        }


        // b) ACTUALIZAR PRECIO de producto id = 5
        em.getTransaction().begin();
        Producto pid5 = em.find(Producto.class, 5);

        if (pid5 != null) {
            pid5.setPrecio(140.00);
            System.out.println("\nPrecio actualizado para el ID 5: ");
            System.out.println(pid5);
        }

        em.getTransaction().commit();


        // c) ACTUALIZAR STOCK de producto id = 17 (restar 3)
        em.getTransaction().begin();
        Producto pid17 = em.find(Producto.class, 17);

        if (pid17 != null) {
            pid17.setStock(pid17.getStock() - 3);
            System.out.println("\nStock actualizado para el ID 17: ");
            System.out.println(pid17);
        }

        em.getTransaction().commit();

        // Cerrar conexiones
        em.close();
        emf.close();

        System.out.println("\n### Cambios guardados correctamente ###");
    }
}