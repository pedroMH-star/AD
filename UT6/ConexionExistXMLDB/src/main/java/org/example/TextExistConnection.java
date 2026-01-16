package org.example;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import java.util.List;

/*
 * author: Pedro Martínez Herrero
 * @since: 13/01/2026
 * @until: 15/01/2026
 * Actividad: Conexión a eXistDB y consulta de colecciones
 */

public class TextExistConnection {

    // URI de la colección raíz y credenciales
    private static final String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    public static void main(String[] args) {

        Collection rootCollection = null;

        try {
            // 1. Cargar y registrar el driver de eXistDB
            Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
            Database database = (Database) cl.getDeclaredConstructor().newInstance();
            DatabaseManager.registerDatabase(database);

            // 2. Conectar a la colección raíz
            rootCollection = DatabaseManager.getCollection(URI, USER, PASSWORD);

            if (rootCollection == null) {
                System.err.println("No se pudo conectar a la colección raíz: " + URI);
                return;
            }

            System.out.println("Conexión establecida con eXistDB en " + URI);
            System.out.println("Listado de colecciones y recursos de /db:");

            // 3. Listado recursivo de colecciones y recursos
            listCollectionsRecursive(rootCollection, "/db");

        } catch (ClassNotFoundException e) {
            System.err.println("Driver de eXistDB no encontrado.");
            e.printStackTrace();
        } catch (XMLDBException e) {
            System.err.println("Error XMLDB al conectar o consultar la base de datos.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado.");
            e.printStackTrace();
        } finally {
            // 4. Cierre seguro de la colección raíz
            if (rootCollection != null) {
                try {
                    rootCollection.close();
                    System.out.println("Conexión cerrada correctamente.");
                } catch (XMLDBException e) {
                    System.err.println("Error al cerrar la colección raíz.");
                    e.printStackTrace();
                }
            }
        }
    }

    // Función recursiva que lista todas las subcolecciones y recursos XML
    private static void listCollectionsRecursive(Collection col, String path) throws XMLDBException {

        // Listar subcolecciones (XML:DB ahora devuelve List<String>)
        List<String> childCollections = col.listChildCollections();

        if (childCollections.isEmpty()) {
            System.out.println("(No hay subcolecciones en " + path + ")");
        }

        // Recorrer subcolecciones
        for (String child : childCollections) {
            String childPath = path + "/" + child;
            System.out.println(" - Colección: " + childPath);

            // Abrir subcolección y recorrer recursivamente
            Collection subCol = null;
            try {
                // Construimos URI manualmente para subcolecciones
                subCol = DatabaseManager.getCollection(URI + "/" + child, USER, PASSWORD);
                if (subCol != null) {
                    listCollectionsRecursive(subCol, childPath);
                }
            } finally {
                if (subCol != null) {
                    subCol.close();
                }
            }
        }

        // Listar recursos XML dentro de la colección actual
        List<String> resources = col.listResources();
        for (String res : resources) {
            System.out.println(" - Recurso: " + path + "/" + res);
        }
    }
}