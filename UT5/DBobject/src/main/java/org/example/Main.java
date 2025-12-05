package org.example;

import jakarta.persistence.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Open a database connection
        // (create a new database if it doesn't exist yet):
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("$objectdb/db/points.odb");
        EntityManager em = emf.createEntityManager();

        // Store 1000 Point objects in the database:
        em.getTransaction().begin();
        for (int i = 0; i < 1000; i++) {
            point.Point p = new point.Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();

        // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + q1.getSingleResult());

        // Find the average X value:
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Average X: " + q2.getSingleResult());

        // Retrieve all the Point objects from the database:
        TypedQuery<point.Point> query =
            em.createQuery("SELECT p FROM Point p", point.Point.class);
        List<point.Point> results = query.getResultList();
        for (point.Point p : results) {
            System.out.println(p);
        }

        // Close the database connection:
        em.close();
        emf.close();
    }
}