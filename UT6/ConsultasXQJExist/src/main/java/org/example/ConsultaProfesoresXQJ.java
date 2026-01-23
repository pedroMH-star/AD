package org.example;

import javax.xml.xquery.*;
import java.lang.reflect.InvocationTargetException;

/*
 * author: Pedro Martínez Herrero
 * @since: 22/01/2026
 * @until: 22/01/2026
 * Actividad: Consultas XQuery en eXistDB con XQJ
 * Objetivo: Consultar el documento JesusLozano.xml en /db/Formacion/Profesores
 */

public class ConsultaProfesoresXQJ {

    private static String nomClaseDS = "net.xqj.exist.ExistXQDataSource";
    private static final String USER = "admin";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws Exception {

        XQConnection xqConectar = null;

        //PASO 1: CARGAR E INSTANCIAR EL DRIVER
        XQDataSource xqDS = (XQDataSource) Class.forName(nomClaseDS)
                .getDeclaredConstructor()
                .newInstance();

        // 2. Configurar propiedades de conexión
        xqDS.setProperty("serverName", "localhost");
        xqDS.setProperty("port", "8080");
        xqDS.setProperty("user", USER);
        xqDS.setProperty("password", PASSWORD);

        // 3. Abrir conexión
        xqConectar = xqDS.getConnection();
        System.out.println("Conexión XQJ establecida correctamente.");

        try {
            // 4. Crear expresión XQuery
            XQExpression xqExp = xqConectar.createExpression();

            // 5. Definir consulta XQuery
            String nombreQ = "doc('/db/Formacion/Profesores/JesusLozano.xml')/profesor/nombre";
            String dniQ    = "doc('/db/Formacion/Profesores/JesusLozano.xml')/profesor/dni";
            String emailQ  = "doc('/db/Formacion/Profesores/JesusLozano.xml')/profesor/email";

            // 6. Ejecutar consulta
            XQResultSequence result1 = xqExp.executeQuery(nombreQ);
            XQResultSequence result2 = xqExp.executeQuery(dniQ);
            XQResultSequence result3 = xqExp.executeQuery(emailQ);

            // 7. Mostrar resultados
            System.out.println("\nContenido del documento JesusLozano.xml:");

            System.out.print("Nombre: ");
            while (result1.next()) {
                System.out.println(result1.getItemAsString(null));
            }

            System.out.print("DNI: ");
            while (result2.next()) {
                System.out.println(result2.getItemAsString(null));
            }

            System.out.print("Email: ");
            while (result3.next()) {
                System.out.println(result3.getItemAsString(null));
            }

            // Otra opción, hacer una sola consulta que traiga los tres campos juntos

            /*
                String xquery = "doc('/db/Formacion/Profesores/JesusLozano.xml')/profesor/(nombre, dni, email)";
                XQResultSequence result = xqExp.executeQuery(xquery);

                System.out.println("\nContenido del documento JesusLozano.xml:");
                while (result.next()) {
                    System.out.println(result.getItemAsString(null));
                }
           */

            // 8. Cerrar la expresión
            xqExp.close();

        } finally {
            // 9. Cierre seguro de la conexión
            if (xqConectar != null) {
                xqConectar.close();
                System.out.println("\nConexión cerrada correctamente.");
            }
        }
    }
}