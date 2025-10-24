import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Pedro Martínez Herrero
 * @since 20/10/2025
 * @until 21/10/2025
 * Actividad: Consulta SELECT sobre base de datos
 */

public class ConsultaSELECT {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/ut3";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {

        // Sentencia SQL para consultar los datos de la tabla agentes
        String sql = "SELECT CODIGO_AGENTE, NOMBRE_AGENTE, FRASE_CLAVE FROM agentes;";

        // Bloque try-with-resources: cierra automáticamente los recursos al terminar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Consulta realizada correctamente.\n");

            // Recorre el resultado de la consulta
            while (resultSet.next()) {
                int codigo = resultSet.getInt("CODIGO_AGENTE");
                String nombre = resultSet.getString("NOMBRE_AGENTE");
                String frase = resultSet.getString("FRASE_CLAVE");

                System.out.println("Código Agente: " + codigo +
                                    ", Nombre Agente: " + nombre +
                                    ", Frase Clave: " + frase);
            }

        } catch (SQLException e) {
            System.out.println("Error al realizar la consulta.");
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    // Método para mostrar detalles del error SQL
    public static void muestraErrorSQL(SQLException e) {
        System.out.println("Mensaje de error: " + e.getMessage());
        System.out.println("Estado SQL: " + e.getSQLState());
        System.out.println("Código de error: " + e.getErrorCode());
    }
}