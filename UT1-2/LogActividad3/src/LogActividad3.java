import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @autor Pedro Martínez Herrero
 * @since 24/09/2025
 * @until 25/09/2025
 * Actividad: Escritura y lectura de archivos en Java
 */

public class LogActividad3 {

    // Nombre del archivo de log usado en toda la clase (constante)
    private static final String NOMBRE_ARCHIVO = "seguridad_actividad3.log";

    public static void main(String[] args) {
        // Paso 1: escribir los mensajes en el archivo (si algo falla, se captura la excepción)
        escribirMensajes();

        // Paso 2: leer los mensajes del mismo archivo y mostrarlos por consola
        leerMensajes();
    }

    // Método para escribir mensajes en el archivo
    private static void escribirMensajes() {
        // try-with-resources abre FileWriter y garantiza que se cierre al finalizar el bloque
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO)) {
            // Escribimos la primera línea y añadimos un salto de línea
            fw.write("Intento de acceso fallido\n");

            // Escribimos la segunda línea y añadimos un salto de línea
            fw.write("Usuario autenticado correctamente\n");

            // Mensaje informativo para evidenciar en consola que la escritura fue correcta
            System.out.println("Mensajes escritos correctamente en el archivo: " + NOMBRE_ARCHIVO);
        } catch (IOException e) {
            // Si se produce un error, lo mostramos en consola
            System.out.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Método para leer mensajes del archivo
    private static void leerMensajes() {
        // try-with-resources abre FileReader y BufferedReader y asegura su cierre automático
        try (FileReader fr = new FileReader(NOMBRE_ARCHIVO);
             BufferedReader br = new BufferedReader(fr)) {

            System.out.println("\nContenido del archivo de log:");

            // Variable temporal para almacenar cada línea leída
            String linea;

            // Leemos línea a línea hasta que readLine() devuelva null (fin de archivo)
            while ((linea = br.readLine()) != null) {
                // Imprimimos cada línea por consola
                System.out.println(linea);
            }

        } catch (IOException e) {
            // Si hay error (por ejemplo, archivo no encontrado) lo mostramos en consola
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}