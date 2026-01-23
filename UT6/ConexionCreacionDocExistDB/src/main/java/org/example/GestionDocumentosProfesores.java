package org.example;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XMLResource;

/*
 * author: Pedro Martínez Herrero
 * @since: 22/01/2026
 * @until: 22/01/2026
 * Actividad: Creación y eliminación de documentos XML en eXistDB
 */
public class GestionDocumentosProfesores {

    // URI de la colección /db/Formacion/Profesores
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Formacion/Profesores";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws Exception {

        // 1. Registrar driver de eXistDB (fuera de try)
        Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;

        try {
            // 2. Conectar a la colección Profesores
            col = DatabaseManager.getCollection(URI, USER, PASSWORD);
            if (col == null) {
                System.err.println("No se pudo conectar a la colección: " + URI);
                return;
            }

            System.out.println("Conexión establecida con /db/Formacion/Profesores");

            // 3. Crear dos documentos XML en memoria
            String doc1Name = "PedroMartínez.xml";
            String doc1Content =
                    "<profesor id=\"5\">" +
                        "<nombre>Pedro Martínez</nombre>" +
                        "<dni>12345678A</dni>" +
                        "<email>pmartinez@gmail.com</email>" +
                    "</profesor>";

            String doc2Name = "LauraGomez.xml";
            String doc2Content =
                    "<profesor id=\"6\">" +
                        "<nombre>Laura Gómez</nombre>" +
                        "<dni>87654321B</dni>" +
                        "<email>lgomez@gmail.com</email>" +
                    "</profesor>";

            // 4. Crear recursos XML y almacenarlos en eXistDB
            storeXMLResource(col, doc1Name, doc1Content);
            storeXMLResource(col, doc2Name, doc2Content);

            System.out.println("Documentos creados correctamente.");

            // 5. Eliminar uno de los documentos
            Resource toDelete = col.getResource(doc2Name);
            if (toDelete != null) {
                col.removeResource(toDelete);
                System.out.println("Documento '" + doc2Name + "' eliminado correctamente.");
            } else {
                System.out.println("Documento '" + doc2Name + "' no encontrado para eliminar.");
            }

        } catch (XMLDBException xe) {
            System.err.println("Error XMLDB al crear o eliminar documentos.");
            xe.printStackTrace();
        } finally {
            // 6. Cierre seguro de la colección
            if (col != null) {
                try {
                    col.close();
                    System.out.println("Conexión cerrada correctamente.");
                } catch (XMLDBException xe) {
                    xe.printStackTrace();
                }
            }
        }
    }

    /**
     * Función auxiliar para crear y almacenar un recurso XML en eXistDB
     */
    private static void storeXMLResource(Collection col, String name, String content) throws XMLDBException {
        // Crear recurso XML en memoria
        XMLResource res = (XMLResource) col.createResource(name, "XMLResource");
        res.setContent(content);

        // Almacenar recurso en eXistDB
        col.storeResource(res);
    }
}