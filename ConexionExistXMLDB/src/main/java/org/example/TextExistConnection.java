package org.example;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import org.exist.xmldb.EXistBinaryResource;


public class TextExistConnection {

    // URI de la colección raíz
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db";

    public static void main(String args[]) throws Exception {

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;

        try {
            // get the collection
            col = DatabaseManager.getCollection(URI, "admin", "");

            if (col == null) {
                System.err.println("No se pudo conectar a la colección raíz");
                return;
            }

            System.out.println("Subcolecciones de /db: ");
            listCollectionsRecursive(col, "/db");

        } finally {
            if (col != null) {
                try {
                    col.close();
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }

    /**
     * Función recursiva que lista todas las subcolecciones
     */
    private static void listCollectionsRecursive (Collection col, String path) throws XMLDBException {
        // Listar subcolecciones
        String[] childCollections = col.listChildCollections();

        for (String child : childCollections) {
            String childPath = path + "/" + child;
            System.out.println(" - " + childPath);

            // Abrir la subcolección para listar sus hijos de forma recursiva
            Collection subCol = null;
            try {
                subCol = DatabaseManager.getCollection(col.getURI() + "/" + child, "admin", "");
                if (subCol != null) {
                    listCollectionsRecursive(subCol, childPath);
                }
            } finally {
                if (subCol != null) {
                    try {
                        subCol.close();
                    } catch (XMLDBException xe) {
                        xe.printStackTrace();
                    }
                }
            }
        }
    }
}
