import java.sql.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 30/10/2025
 * @until 30/10/2025
 * Actividad:  Este programa consulta la precipitación media registrada en la tabla
 * 'datos_climaticos' de la base de datos 'ut3' y muestra el resultado en consola
 */

public class PrecipitacionMedia {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/ut3";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {

        // Sentencia SQL para consultar los datos de la tabla agentes
        String sql = "SELECT AVG(Precipitacion) AS media_precipitacion FROM datos_climaticos;";

        // Bloque try-with-resources: cierra automáticamente los recursos al terminar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("Consulta realizada correctamente.\n");

            // Recorre el resultado de la consulta
            if (resultSet.next()) {
                double media = resultSet.getDouble("media_precipitacion");
                System.out.println("Precipitación media en octubre (Algemesi): " + media + " mm");
            }

        } catch (SQLException e) {
            System.out.println("Error al realizar la consulta.");
            muestraErrorSQL(e);
        }
    }

    // Método para mostrar detalles del error SQL
    public static void muestraErrorSQL(SQLException e) {
        System.out.println("Mensaje de error: " + e.getMessage());
        System.out.println("Estado SQL: " + e.getSQLState());
        System.out.println("Código de error: " + e.getErrorCode());
    }
}