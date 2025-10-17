import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//ImportLibraries
public class Conector {
    // Datos de conexión
    private static final String URL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Establecer conexión con la base de datos MySQL
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null) {
                System.out.println("Conexión a la base de datos establecida correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}