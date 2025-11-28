package org.example;

import jakarta.persistence.*;
import java.util.List;

/**
 * author: Pedro Martínez Herrero
 * @since 23/11/2025
 * @until 24/11/2025
 * Actividad: UT4 - Gestión de consultas con JPA (JPQL vs SQL Nativo)
 */

public class MainConsultas {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("### Consultas SQL Nativo ###");
            consultarSQLNativoSimple(em);
            consultarSQLNativoFiltrado(em);

            System.out.println("\n### Consultas JPQL ###");
            consultarJPQLSimple(em);
            consultarJPQLCondicion(em);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    // ====================================
    // SQL nativo
    // ====================================

    // Consulta simple: solo SIM_LLAMANTE, NUMERO_LLAMADO, IMPORTE_LLAMADA
    public static void consultarSQLNativoSimple(EntityManager em) {
        System.out.println("\nConsulta SQL Nativo Simple:");
        List<Object[]> resultados = em.createNativeQuery(
                "SELECT NUMERO_SIM, NUMERO_LLAMADO, IMPORTE_LLAMADA FROM LLAMADAS_EMITIDAS"
        ).getResultList();

        for (Object[] fila : resultados) {
            System.out.println("SIM: " + fila[0] + ", Número llamado: " + fila[1] + ", Importe: " + fila[2]);
        }
    }

    // Consulta con filtrado: duración > 300
    public static void consultarSQLNativoFiltrado(EntityManager em) {
        System.out.println("\nConsulta SQL Nativo con Duracion > 300:");
        List<Object[]> resultados = em.createNativeQuery(
                "SELECT * FROM LLAMADAS_EMITIDAS WHERE DURACION_LLAMADA > 300"
        ).getResultList();

        for (Object[] fila : resultados) {
            System.out.println("ID: " + fila[0] + ", SIM: " + fila[1] + ", Número llamado: " + fila[2] +
                    ", Duración: " + fila[3] + ", Importe: " + fila[4]);
        }
    }


    // ====================================
    // JPQL
    // ====================================

    // Consulta básica: todas las llamadas
    public static void consultarJPQLSimple(EntityManager em) {
        System.out.println("\nConsulta JPQL Simple:");
        List<LlamadasEmitida> llamadas = em.createQuery(
                "SELECT l FROM LlamadasEmitida l", LlamadasEmitida.class
        ).getResultList();

        for (LlamadasEmitida l : llamadas) {
            System.out.println("SIM: " + l.getNumeroSim() +
                    ", Número llamado: " + l.getNumeroLlamado() +
                    ", Importe: " + l.getImporteLlamada());
        }
    }

    // Consulta con condición: importe < 300
    public static void consultarJPQLCondicion(EntityManager em) {
        System.out.println("\nConsulta JPQL con Importe < 300:");
        List<LlamadasEmitida> llamadas = em.createQuery(
                "SELECT l FROM LlamadasEmitida l WHERE l.importeLlamada < 300", LlamadasEmitida.class
        ).getResultList();

        for (LlamadasEmitida l : llamadas) {
            System.out.println("SIM: " + l.getNumeroSim() +
                    ", Número llamado: " + l.getNumeroLlamado() +
                    ", Importe: " + l.getImporteLlamada());
        }
    }
}