import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @autor Pedro Martínez Herrero
 * @since 22/09/2025
 * @until 23/09/2025
 * Actividad: Creación, renombrado y eliminación de ficheros en Java
 */

public class LogActividad1 {

    private static final String DIRECTORIO_LOGS = "logs/seguridad";
    private static final String NOMBRE_FICHERO = "seguridad_actividad1.log";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Menú de gestión de logs ===");
            System.out.println("1. Crear fichero de log");
            System.out.println("2. Verificar existencia del fichero");
            System.out.println("3. Rotación del log (renombrar con timestamp)");
            System.out.println("4. Eliminar fichero de log");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> crearFichero();
                case 2 -> verificarFichero();
                case 3 -> rotarFichero();
                case 4 -> eliminarFichero();
                case 5 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 5);

        sc.close();
    }

    // Método 1: Crear directorio y fichero
    private static void crearFichero() {
        try {
            File directorio = new File(DIRECTORIO_LOGS);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado correctamente: " + DIRECTORIO_LOGS);
                } else {
                    System.out.println("Error al crear el directorio.");
                    return;
                }
            }

            File fichero = new File(directorio, NOMBRE_FICHERO);
            if (fichero.createNewFile()) {
                System.out.println("Fichero creado correctamente: " + fichero.getName());
            } else {
                System.out.println("El fichero ya existía: " + fichero.getName());
            }
        } catch (IOException e) {
            System.out.println("Error al crear el fichero: " + e.getMessage());
        }
    }

    // Método 2: Verificar existencia del fichero
    private static void verificarFichero() {
        File fichero = new File(DIRECTORIO_LOGS, NOMBRE_FICHERO);
        if (fichero.exists()) {
            System.out.println("El fichero existe: " + fichero.getAbsolutePath());
        } else {
            System.out.println("El fichero no existe.");
        }
    }

    // Método 3: Rotación del log
    private static void rotarFichero() {
        File fichero = new File(DIRECTORIO_LOGS, NOMBRE_FICHERO);
        if (!fichero.exists()) {
            System.out.println("No existe el fichero para rotar.");
            return;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        File ficheroRenombrado = new File(DIRECTORIO_LOGS, "seguridad_actividad1_" + timestamp + ".log");

        if (fichero.renameTo(ficheroRenombrado)) {
            System.out.println("Fichero renombrado a: " + ficheroRenombrado.getName());
        } else {
            System.out.println("Error al renombrar el fichero.");
        }
    }

    // Método 4: Eliminar fichero
    private static void eliminarFichero() {
        File directorio = new File(DIRECTORIO_LOGS);
        File[] ficheros = directorio.listFiles((dir, name) -> name.startsWith("seguridad_actividad1") && name.endsWith(".log"));

        if (ficheros != null && ficheros.length > 0) {
            for (File f : ficheros) {
                if (f.delete()) {
                    System.out.println("Fichero eliminado: " + f.getName());
                } else {
                    System.out.println("No se pudo eliminar: " + f.getName());
                }
            }
        } else {
            System.out.println("No hay ficheros de log para eliminar.");
        }
    }
}