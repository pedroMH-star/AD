import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 * @autor: Pedro Martínez Herrero
 * @since: 23/10/2025
 * @until: 23/10/2025
 * Actividad: Modificación de datos en la tabla CLIENTES usando ResultSet actualizable
 */

public class ModificarClientesResultSet {

    // Datos de conexión
    private static final String urlConnection = "jdbc:mysql://localhost:3306/ut3";
    private static final String user = "root";
    private static final String pwd = "123456";

    public static void main(String[] args) {

        int filasModificadas = 0; // Contador de filas modificadas

        try (Connection connection = DriverManager.getConnection(urlConnection, user, pwd)) {

            // Desactiva auto-commit para manejar transacciones manualmente
            connection.setAutoCommit(false);

            // Crea un Statement para ResultSet actualizable
            try (Statement statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

                // Ejecuta la consulta para obtener todos los registros de CLIENTES
                ResultSet rs = statement.executeQuery("SELECT DNI, APELLIDOS, CP FROM CLIENTES");

                while (rs.next()) {
                    String cp = rs.getString("CP");
                    if (cp == null || cp.isEmpty()) {
                        rs.updateString("CP", "00000"); // Actualiza CP si es nulo
                        rs.updateRow(); // Confirma la actualización en la fila actual
                        filasModificadas++;
                    }
                }

                // Confirma los cambios en la base de datos
                connection.commit();

                // Muestra el resultado en un cuadro de diálogo
                JOptionPane.showMessageDialog(null,
                        "Número de filas modificadas: " + filasModificadas,
                        "Modificación exitosa",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException e) {
                connection.rollback(); // Deshace cambios si hay error
                muestraErrorSQL(e);
            }

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