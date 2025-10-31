import java.sql.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 30/10/2025
 * @until 30/10/2025
 * Actividad: Este programa llama al procedimiento almacenado 'CalcularPrecipitacionTotal'
 * para obtener la precipitación total en octubre de la estación 'Algemesi'
 */

public class EjecutarProcedimiento {

    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/ut3";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             CallableStatement cs = connection.prepareCall("{CALL CalcularPrecipitacionTotal(?, ?)}")) {

            cs.setString(1, "Algemesi"); // parámetro de entrada
            cs.registerOutParameter(2, Types.DECIMAL); // parámetro de salida

            cs.execute();

            double total = cs.getDouble(2);
            System.out.println("Precipitación total en octubre (Algemesi): " + total + " mm");

        } catch (SQLException e) {
            muestraErrorSQL(e);
        }
    }

    public static void muestraErrorSQL(SQLException e) {
        System.err.println("Mensaje: " + e.getMessage());
        System.err.println("Estado SQL: " + e.getSQLState());
        System.err.println("Código de error: " + e.getErrorCode());
    }
}