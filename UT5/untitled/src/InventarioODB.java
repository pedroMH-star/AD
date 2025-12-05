import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * author: Pedro Martínez Herrero
 * @since 04/12/2025
 * @until 04/12/2025
 * Actividad: clase InventarioODB
 */

public class InventarioODB {
    public static void main(String[] args) {

        // Crear la conexión con la base de datos
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("inventario.odb");
        EntityManager em = emf.createEntityManager();

        // Inicia la transacción
        em.getTransaction().begin();

        // Crear productos
        Producto p1 = new Producto("PlayStation 5", 549.99, 10);
        Producto p2 = new Producto("Nintendo Switch 2", 499.99, 15);
        Producto p3 = new Producto("Xbox Series X", 499.99, 7);
        Producto p4 = new Producto("Teclado Mecánico RGB", 89.99, 25);
        Producto p5 = new Producto("Logitech G502", 49.99, 30);

        // Guardar objetos
        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(p4);
        em.persist(p5);

        // Confirmar cambios
        em.getTransaction().commit();

        // Cerrar conexiones
        em.close();
        emf.close();

        System.out.println("Productos guardados correctamente con Jakarta Persistence.");
    }
}