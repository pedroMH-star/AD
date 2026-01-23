package org.example;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

/*
 * author: Pedro Martínez Herrero
 * @since: 20/01/2026
 * @until: 22/01/2026
 * Actividad: Creación y eliminación de colecciones en eXistDB
 */
public class GestionColeccionesExistXMLDB {

    // URI de la colección /db/Formacion en modo cliente-servidor (XML-RPC)
    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Formacion/";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws Exception {

        final String driver = "org.exist.xmldb.DatabaseImpl";

        // 1. Registrar el driver de eXistDB
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;

        try {
            // 2. Conectar a la colección /db/Formacion
            col = DatabaseManager.getCollection(URI, USER, PASSWORD);
            if (col == null) {
                System.out.println("No se pudo conectar a la colección /db/Formacion");
                return;
            }

            // 3. Obtener el servicio de gestión de colecciones
            CollectionManagementService cms = (CollectionManagementService)
                    col.getService("CollectionManagementService", "1.0");

            if (cms == null) {
                System.out.println("No se pudo obtener CollectionManagementService. Revisa permisos de la colección.");
                col.close();
                return;
            }

            // 4. Crear colecciones (subcarpetas)
            if (col.getChildCollection("Alumno") == null) {
                cms.createCollection("Alumno");
                System.out.println("Colección 'Alumno' creada correctamente");
            } else {
                System.out.println("Colección 'Alumno' ya existe");
            }

            if (col.getChildCollection("Error") == null) {
                cms.createCollection("Error");
                System.out.println("Colección 'Error' creada correctamente");
            } else {
                System.out.println("Colección 'Error' ya existe");
            }

            // 5. Eliminar la colección 'Error'
            try {
                cms.removeCollection("Error");
                System.out.println("Colección 'Error' eliminada correctamente");
            } catch (XMLDBException xe) {
                System.out.println("No se pudo eliminar 'Error' o quizás no existe");
            }

            col.close();

        } catch (XMLDBException xe) {
            xe.printStackTrace();
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
}