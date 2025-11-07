import java.sql.*;
import javax.swing.JOptionPane;

/**
 * @autor Pedro Martínez Herrero
 * @since 06/11/2025
 * @until 06/11/2025
 */

public class Examen2 {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/ut3";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {


        // --- APATADO A ---
        System.out.println("=== APARTADO A ===");
        // Sentencia SQL para consultar los datos de la tabla llamadas_emitidas
        String sql1 ="SELECT NUMERO_LLAMADO FROM llamadas_emitidas WHERE CODIGO_LLAMADA = 1000017;";

        // Bloque try-with-resources: cierra automáticamente los recursos al terminar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql1)) {

            System.out.println("Consulta realizada correctamente.\n");

            // Recorre el resultado de la consulta
            if (resultSet.next()) {
                int numero = resultSet.getInt("NUMERO_LLAMADO");
                System.out.println("Llamada: " + numero);
            }

        } catch (SQLException e) {
            System.out.println("Error al realizar la consulta.");
            muestraErrorSQL(e);
        }


        // --- APARTADO B ---
        System.out.println("=== APARTADO B ===");

        // Sentencia SQL para consultar los datos de la tabla duracion_llamada
        String sql2 ="SELECT DURACION_LLAMADA FROM llamadas_emitidas WHERE CODIGO_LLAMADA = ?;";
        int codigoLlamada =  1000054; // Valor del CODIGO_LLAMADA a buscar

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {

            // Prepara la consulta parametrizada
            preparedStatement.setInt(1, codigoLlamada); // Asigna el valor al parámetro
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int duracion = resultSet.getInt("DURACION_LLAMADA");
                    System.out.println("La duración de la llamada " + codigoLlamada + " es: " + duracion);
                } else {
                    System.out.println("No se encontró la llamada con código " + codigoLlamada);
                }
            }
        } catch (SQLException e) {
            muestraErrorSQL(e);
        }


        // --- APARTADO C ---
        System.out.println("\n=== APARTADO C ===");
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            ResultSet resultSet = statement.executeQuery("SELECT CODIGO_LLAMADA, DURACION_LLAMADA FROM llamadas_emitidas");

            // Desactiva auto-commit para manejar transacciones manualmente
            boolean modificado = false;

            while (resultSet.next()) {
                int codigo = resultSet.getInt("CODIGO_LLAMADA");
                if (codigo == 1000054) {
                    resultSet.updateInt("DURACION_LLAMADA", 111);
                    resultSet.updateRow();
                    modificado = true;
                    System.out.println("Duración modificada a 111 para la llamada " + codigo);
                }
            }

            if (!modificado) {
                System.out.println("No se encontró la llamada 1000054 para modificar.");
            }
        } catch (SQLException e) {
            muestraErrorSQL(e);
        }


        // --- APARTADO D ---
        System.out.println("\n=== APARTADO D ===");
        String sql4 ="SELECT SUM(IMPORTE_LLAMADA) AS Importe_Total FROM llamadas_emitidas;";

        // Bloque try-with-resources: cierra automáticamente los recursos al terminar
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql4)) {

            // Recorre el resultado de la consulta
            if (resultSet.next()) {
                double total = resultSet.getDouble("Importe_Total");
                System.out.println("El importe total de la factura es: " + total + " €");
            }

        } catch (SQLException e) {
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
