package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

/**
 * author: Pedro Martínez Herrero
 * @since 18/11/2025
 * @until 19/11/2025
 */

public class Main {

    // EntityManagerFactory: Fabrica que crea EntityManagers (UNA por aplicación)
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public static  void main(String[] args) {

        CrearNuevaLlamada();

        ConsultarRegistroExistente();

        ModificarRegistroExistente();

        EliminarRegistro();

        GestionEstados();

    }

    // Crear método para crear una nueva llamada

    public static Integer CrearNuevaLlamada() {

        // EntityManager: Gestor que maneja las operaciones con la BD (UNO por operación)
        EntityManager em = emf.createEntityManager();

        // Variable para controlar la transacción
        EntityTransaction tx = null;

        // Variable para guardar el ID generado
        Integer codigoGenerado = null;

        // Declaramos la simLlamante
        Integer simLlamante = 617478396;

        // Bloque try-catch-finally
        try {
            // 1º Iniciamos una transacción
            tx = em.getTransaction();
            tx.begin(); // Iniciamos la transacción
            System.out.println("Transacción iniciada");

            // 2º Creamos el objeto en estado transitorio (aquí es posible que necesiteis objetos de otro tipo de entidades)
             // 1. Creamos un objeto LlamadasEmitidas en estado TRANSITORIO
             // (existe en memoria pero NO en la BD)
            LlamadasEmitida nuevaLlamada = new LlamadasEmitida();

            // 3º Configuramos la Nueva Llamada

             // Necesitamos primero obtener la tarjeta telefónica (FK)
             // Suponemos que existe una tarjeta con NUMERO_SIM = 617478396
            TarjetasTelefonica tarjeta = em.find(TarjetasTelefonica.class, simLlamante);

            if (tarjeta == null) {
                // Si no existe, creamos una tarjeta de ejemplo
                System.out.println("La tarjeta XXXX no existe. Creando una de ejemplo...");
                tarjeta = new TarjetasTelefonica();
                tarjeta.setId(simLlamante);

                // La tarjeta necesita un agente (FK)
                Agente agente = em.find(Agente.class, 9);

                if (agente == null) {
                    System.out.println("La agente 9 no existe. Creando uno de ejemplo...");
                    agente = new Agente();
                    agente.setId(9);
                    agente.setNombreAgente("Agente Demo");
                    agente.setFraseClave("Clave123");
                    em.persist(agente);
                }
                tarjeta.setCodigoAgenteAsociado(agente);
                em.persist(tarjeta);

            }
            // Ahora sí, configuramos la nueva llamada
            nuevaLlamada.setSimLlamante(tarjeta);
            nuevaLlamada.setNumeroLlamado(987654321);
            nuevaLlamada.setDuracionLlamada(300);
            nuevaLlamada.setImporteLlamada(12.50f);

            nuevaLlamada.setId(1000073);
            System.out.println("Objeto creado en estado TRANSITORIO: ");

            // 4º Usamos persist() para ese objeto
            em.persist(nuevaLlamada);
            System.out.println("\n persist() ejecutando -> Objeto en estado PERSISTENTE");

            // 5º Hacemos commit
            tx.commit();
            System.out.println("\n commit() ejecutado -> INSERT realizado en BD");

            // Ahora el objeto YA tiene su ID asignado
            codigoGenerado = nuevaLlamada.getId();

        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
                System.err.println("Error: Transacción revertida");
            }
            System.err.println("Error al crear la llamada. Causa: " + e.getMessage());
        } finally {
            em.close();
            System.out.println("EntityManager cerrado\n");
        }
        return codigoGenerado;
    }

    private static void ConsultarRegistroExistente() {
        EntityManager em = emf.createEntityManager();

        try {
            int idBuscar = 1000054;

            // MÉTODO 1: find() -Carga INMEDIATA (EAGER)
            System.out.println("1. Usando find(): ");
            LlamadasEmitida llamada1 = em.find(LlamadasEmitida.class, idBuscar);

            if (llamada1 != null) {
                System.out.println("El número llamado es: " + llamada1.getNumeroLlamado());
                System.out.println("La duración de la llamda es: " + llamada1.getDuracionLlamada());
                System.out.println("El importe de la llamada es: " + llamada1.getImporteLlamada());
                System.out.println("El id de la llamada es: " + llamada1.getId());

                if (llamada1.getSimLlamante() != null) {
                    System.out.println("La SIM llamante es:" + llamada1.getSimLlamante().getId());
                } else {
                    System.out.println("La SIM llamante es: NULA");
                }

            } else {
                System.out.println("El registro con ID " + idBuscar + " no existe");
            }

            // Se puede probar el getReference
            System.out.println("\n2. Usando getReference(): ");
            try {
                LlamadasEmitida llamadaRef = em.getReference(LlamadasEmitida.class, idBuscar);
                System.out.println("Objeto obtenido con getReference(): " + llamadaRef.getId());
            } catch (Exception ex) {
                System.out.println("El registro con ID " + idBuscar + " no existe (getReference())");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Modifica un registro existente: incrementa el IMPORTE_LLAMADA en un 10%
    private static void ModificarRegistroExistente() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        try {
            int idModificar = 1000054; // ID de ejemplo
            tx = em.getTransaction();
            tx.begin();

            LlamadasEmitida llamada = em.find(LlamadasEmitida.class, idModificar);

            if (llamada != null) {
                System.out.println("Importe actual: " + llamada.getImporteLlamada());
                // Incrementamos el importe un 10%
                float nuevoImporte = llamada.getImporteLlamada() * 1.10f;
                llamada.setImporteLlamada(nuevoImporte);

                // commit sincroniza los cambios
                tx.commit();
                System.out.println("Registro modificado con éxito. Nuevo importe: " + nuevoImporte);
            } else {
                System.out.println("No se encontró el registro con ID: " + idModificar);
                tx.rollback();
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Elimina un registro existente
    private static void EliminarRegistro() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        try {
            int idEliminar = 1000072; // ID del registro creado en CrearNuevaLlamada()
            tx = em.getTransaction();
            tx.begin();

            LlamadasEmitida llamada = em.find(LlamadasEmitida.class, idEliminar);
            if (llamada != null) {
                em.remove(llamada);
                tx.commit();
                System.out.println("Registro eliminado con éxito. ID: " + idEliminar);
            } else {
                System.out.println("No se encontró el registro con ID: " + idEliminar);
                tx.rollback();
            }

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Gestión de estados: detach() y merge()
    private static void GestionEstados() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;

        try {
            int idGestion = 1000070; // ID de ejemplo
            tx = em.getTransaction();
            tx.begin();

            LlamadasEmitida llamada = em.find(LlamadasEmitida.class, idGestion);
            System.out.println("Estado inicial (persistente) - Importe: " + llamada.getImporteLlamada());

            // detach(): objeto deja de ser gestionado
            em.detach(llamada);
            llamada.setImporteLlamada(llamada.getImporteLlamada() + 5);
            tx.commit(); // No se guardan cambios porque está detached
            System.out.println("Después de detach() y commit - Importe en BD no cambia");

            // Reasociar con merge()
            tx = em.getTransaction();
            tx.begin();
            LlamadasEmitida llamadaDetached = llamada; // objeto detached modificado
            LlamadasEmitida llamadaMergeada = em.merge(llamadaDetached); // ahora vuelve a persistente
            tx.commit();
            System.out.println("Después de merge() - Importe guardado: " + llamadaMergeada.getImporteLlamada());

        } catch (Exception e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}