import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Pedro Martínez Herrero
 * @since 21/10/2025
 * @until 21/10/2025
 * Actividad: Consulta SELECT con PreparedStatement
 */

public class ConsultaPREPARED {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/ut3";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        int codigoAgenteBuscar = 7; // Valor del CODIGO_AGENTE a buscar

        String sql = "SELECT CODIGO_AGENTE, NOMBRE_AGENTE, FRASE_CLAVE FROM agentes WHERE CODIGO_AGENTE = ?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establece la conexión con la base de datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prepara la consulta parametrizada
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, codigoAgenteBuscar); // Asigna el valor al parámetro

            // Ejecuta la consulta
            resultSet = preparedStatement.executeQuery();

            // Procesa los resultados
            while (resultSet.next()) {
                int codigo = resultSet.getInt("CODIGO_AGENTE");
                String nombre = resultSet.getString("NOMBRE_AGENTE");
                String frase = resultSet.getString("FRASE_CLAVE");

                System.out.println("Código Agente: " + codigo +
                        ", Nombre Agente: " + nombre +
                        ", Frase Clave: " + frase);
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta.");
            muestraErrorSQL(e);
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
        } finally {
            // Cierra recursos
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (preparedStatement != null) preparedStatement.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (connection != null) connection.close(); } catch (SQLException e) { /* ignorar */ }
        }
    }

    // Método para mostrar detalles del error SQL
    public static void muestraErrorSQL(SQLException e) {
        System.out.println("Mensaje de error: " + e.getMessage());
        System.out.println("Estado SQL: " + e.getSQLState());
        System.out.println("Código de error: " + e.getErrorCode());
    }
}