//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @autor Pedro Martínez Herrero
 * @since 22/09/2025
 * @until 23/09/2025
 */

public class LogActividad1O2 {
    public static void main(String[] args) {
        // Ruta del directorio
        File directorio = new File("logs/seguridad");

        // Comprobar si existe, si no existe lo creamos
        if (!directorio.exists()) {
            if (directorio.mkdirs()) { // mkdirs() crea todos los directorios intermedios
                System.out.println("Directorio 'logs/seguridad' creado correctamente.");
            } else {
                System.out.println("Error al crear el directorio.");
            }
        } else {
            System.out.println("El directorio ya existe.");
        }

        // Crear fichero
        try {
            File logFile = new File(directorio, "seguridad_actividad1.log");
            if (logFile.createNewFile()) {  // Crea el fichero si no existía
                System.out.println("Fichero de log creado correctamente: " + logFile.getName());
            } else {
                System.out.println("El fichero ya existía: " + logFile.getName());
            }

            // Rotación por renombrado
            String fecha = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
            File logRenombrado = new File(directorio, "seguridad_actividad1_" + fecha + ".log");
            if (logFile.renameTo(logRenombrado)) {
                System.out.println("Fichero renombrado correctamente a: " + logRenombrado.getName());
            } else {
                System.out.println("Error al renombrar el fichero.");
            }

            // Eliminación
            if (logRenombrado.delete()) {
                System.out.println("Fichero eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar el fichero.");
            }

        } catch (Exception e) {
            System.out.println("Error al crear el fichero: " + e.getMessage());
        }
    }
}