package org.example;

import jakarta.persistence.*;

/**
 * author: Pedro Martínez Herrero
 * @since 27/11/2025
 * @until 27/11/2025
 * Examen UT4
 */

/**
 * ═══════════════════════════════════════════════════════════════════════════
 * App.Java - GESTIÓN DE PERSISTENCIA DE CLIENTES CON JPA E HIBERNATE
 * ═══════════════════════════════════════════════════════════════════════════
 */

public class App {
    // EntityManagerFactory: Fabrica que crea EntityManagers (UNA por aplicación)
    // Esto lee el fichero 'persistence.xml' y busca la unidad de persistencia "default"
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    /**
     * ═══════════════════════════════════════════════════════════════════
     * MÉTODO MAIN - PUNTO DE ENTRADA DEL PROGRAMA
     * ═══════════════════════════════════════════════════════════════════
     */
    public static void main(String[] args) {
        System.out.println("=== INICIO DE LA APLICACIÓN DE GESTIÓN DE CLIENTES PARA EL EXAMEN ===\n");

        try {

            // ═════════════════════════════════════════════════════════
            // APARTADO A: INSERTAR UN NUEVO CLIENTE
            // ═════════════════════════════════════════════════════════
            InsertarNuevoCliente();

            // ═════════════════════════════════════════════════════════
            // APARTADO B: CONSULTAR UN CLIENTE EXISTENTE
            // ═════════════════════════════════════════════════════════
            ConsultarClienteExistente();

            // ═════════════════════════════════════════════════════════
            // APARTADO C: MODIFICAR UN CLIENTE EXISTENTE
            // ═════════════════════════════════════════════════════════
            ModificarClienteExistente();

            // ═════════════════════════════════════════════════════════
            // APARTADO D: ELIMINAR UN CLIENTE EXISTENTE
            // ═════════════════════════════════════════════════════════
            EliminarClienteExistente();

        }catch (Exception e){
            System.err.println("Mensaje: " + e.getMessage());
            e.printStackTrace();
        }finally {
            // Cerramos la factoría al finalizar la aplicación
            emf.close();
            System.out.println("\n=== FIN DE LA APLICACIÓN ===");

        }

    }

    /**
     * ═══════════════════════════════════════════════════════════════════
     * IMPLEMENTACIÓN DE LOS MÉTODOS DEL EXAMEN
     * ═══════════════════════════════════════════════════════════════════
     *
     * @return
     */

    private static String InsertarNuevoCliente() {
        // TODO: Implementar apartado A
        // EntityManager: Gestor que maneja las operaciones con la BD (UNO por operación)
        EntityManager em = emf.createEntityManager();

        // Variable para controlar la transacción
        EntityTransaction tx = null;

        // Variable para guardar el ID generado
        String codigoGenerado = null;

        // Bloque try-catch-finally
        try {
            // 1º Iniciamos una transacción
            tx = em.getTransaction();
            tx.begin(); // Iniciamos la transacción
            System.out.println("Transacción iniciada");

            // 2º Creamos el objeto en estado transitorio (aquí es posible que necesiteis objetos de otro tipo de entidades)
            // 1. Creamos un objeto Cliente en estado TRANSITORIO
            // (existe en memoria pero NO en la BD)
            Cliente nuevoCliente = new Cliente();

            // Ahora sí, configuramos el nuevo cliente
            nuevoCliente.setDni("96878083K");
            nuevoCliente.setApellidos("OLIVARES");
            nuevoCliente.setCp("30510");

            // 4º Usamos persist() para ese objeto
            em.persist(nuevoCliente);
            System.out.println("\n persist() ejecutando -> Objeto en estado PERSISTENTE");

            // 5º Hacemos commit
            tx.commit();
            System.out.println("\n commit() ejecutado -> INSERT realizado en BD");

            // Ahora el objeto YA tiene su ID asignado
            codigoGenerado = nuevoCliente.getDni();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
                System.err.println("Error: Transacción revertida");
            }
            System.err.println("Error al crear el Cliente. Causa: " + e.getMessage());
        } finally {
            em.close();
            System.out.println("EntityManager cerrado\n");
        }
        return codigoGenerado;
    }

    private static void ConsultarClienteExistente() {
        // TODO: Implementar apartado B
        EntityManager em = emf.createEntityManager();

        try {
            String idBuscar = "96878083K";

            // MÉTODO 1: find() -Carga INMEDIATA (EAGER)
            System.out.println("1. Usando find(): ");
            Cliente cliente4 = em.find(Cliente.class, idBuscar);

            if (cliente4 != null) {
                System.out.println("El DNI es: " + cliente4.getDni());
                System.out.println("El apellido es: " + cliente4.getApellidos());
                System.out.println("El CP es: " + cliente4.getCp());

            } else {
                System.out.println("El registro con ID " + idBuscar + " no existe");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void ModificarClienteExistente() {
        // TODO: Implementar apartado C
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        try {
            String DNIModificar = "96878083K"; // DNO de ejemplo
            tx = em.getTransaction();
            tx.begin();

            Cliente dni = em.find(Cliente.class, DNIModificar);

            if (dni != null) {
                System.out.println("El CP actual: " + dni.getCp());
                // cp.getCp()
                String nuevoCP = dni.getCp();
                String newCP = "30520";
                nuevoCP = newCP;
                dni.setCp(nuevoCP);

                // commit sincroniza los cambios
                tx.commit();
                System.out.println("Registro modificado con éxito. Nuevo CP: " + nuevoCP);
            } else {
                System.out.println("No se encontró el registro con DNI: " + DNIModificar);
                tx.rollback();
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private static void EliminarClienteExistente() {
        // TODO: Implementar apartado D
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        try {
            String DNIEliminar = "96878083K"; // DNI del registro creado en InsertarNuevoCliente()
            tx = em.getTransaction();
            tx.begin();

            Cliente dni = em.find(Cliente.class, DNIEliminar);
            if (dni != null) {
                em.remove(dni);
                tx.commit();
                System.out.println("Registro eliminado con éxito. DNI: " + DNIEliminar);
            } else {
                System.out.println("No se encontró el registro con DNI: " + DNIEliminar);
                tx.rollback();
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
