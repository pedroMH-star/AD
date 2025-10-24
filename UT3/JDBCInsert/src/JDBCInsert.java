import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @author Pedro Martínez Herrero
 * @since 23/10/2025
 * @until 23/10/2025
 * Actividad: Inserción de datos en la tabla CLIENTES
 */

public class JDBCInsert {

    // Datos de conexión
    private static final String urlConnection = "jdbc:mysql://localhost:3306/ut3";
    private static final String user = "root";
    private static final String pwd = "123456";

    public static void main(String[] args) {

        // Sentencia SQL para insertar múltiples filas
        String sql = "INSERT INTO clientes (DNI, APELLIDOS, CP) VALUES " +
                     "('12345678A', 'Pérez', '28001')," +
                     "('87654321B', 'Gómez', '28002')," +
                     "('11223344C', 'Sánchez', NULL);";

        int nFil = 0; // Número de filas insertadas

        // Bloque try-with-resources para asegurar cierre automático de recursos
        try (Connection connection = DriverManager.getConnection(urlConnection, user, pwd);
             Statement statement = connection.createStatement()) {

            nFil = statement.executeUpdate(sql); // Ejecuta INSERT y devuelve filas afectadas
            JOptionPane.showMessageDialog(null, "Número de filas insertadas: " + nFil,
                    "Inserción exitosa", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    // Método para mostrar detalles del error SQL
    public static void muestraErrorSQL(SQLException e) {
        JOptionPane.showMessageDialog(null,
                "Mensaje de error: " + e.getMessage() +
                        "\nEstado SQL: " + e.getSQLState() +
                        "\nCódigo de error: " + e.getErrorCode(),
                "Error SQL",
                JOptionPane.ERROR_MESSAGE);
    }
}