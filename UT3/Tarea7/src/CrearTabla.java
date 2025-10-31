import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @autor Pedro Martínez Herrero
 * @since 30/10/2025
 * @until 30/10/2025
 * Actividad: Se utilizará para almacenar datos climáticos importados desde un CSV
 */

public class CrearTabla {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/ut3";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Sentencia SQL para crear la tabla clientes
        String sql = "CREATE TABLE IF NOT EXISTS datos_climaticos (" +
                     "Provincia VARCHAR(15), " +
                     "Estacion VARCHAR(15), " +
                     "Fecha VARCHAR (15)," +
                     "Temperatura DECIMAL(5,2)," +
                     "Humedad DECIMAL(5,2)," +
                     "Precipitacion DECIMAL(4,1)" +
                     ");";

        // Bloque try-with-resources asegura cierre automático de recursos
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Tabla 'datos_climaticos' creada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear la tabla.");
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