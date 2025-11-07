import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @autor Pedro Martínez Herrero
 * @since 06/11/2025
 * @until 06/11/2025
 */

public class Examen1 {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/ut3";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        // Sentencia SQL para crear la tabla examen
        String sql = "CREATE TABLE examen (" +
                     "DNI CHAR(9) NOT NULL PRIMARY KEY, " +
                     "APELLIDOS VARCHAR(32) NOT NULL, " +
                     "NOTAS DECIMAL(5,2) NOT NULL" +
                     ");";

        // Bloque try-with-resources asegura cierre automático de recursos
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("Tabla 'examen' creada correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear la tabla.");
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        }


        // Sentencia SQL para insertar múltiples filas
        String sql2 = "INSERT INTO examen (DNI, APELLIDOS, NOTAS) VALUES " +
                "('12345678A', 'MARTÍNEZ HERRERO', 7.5);";

        int nFil = 0; // Número de filas insertadas

        // Bloque try-with-resources para asegurar cierre automático de recursos
        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Statement statement = connection.createStatement()) {

            nFil = statement.executeUpdate(sql2); // Ejecuta INSERT y devuelve filas afectadas
            JOptionPane.showMessageDialog(null, "Número de filas insertadas: " + nFil,
                    "Inserción exitosa", JOptionPane.INFORMATION_MESSAGE);

        } catch (
                SQLException e) {
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