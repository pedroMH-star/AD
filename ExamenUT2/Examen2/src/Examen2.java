import java.io.*;

public class Examen2 {

    private static final String FICHERO_ORIGINAL = "alertas.txt";
    private static final String FICHERO_LIMPIO = "alertas_limpio.txt";

    public static void main(String[] args) {

        // a. Lectura del fichero
        System.out.println("=== Contenido completo de alertas.txt ===");
        leerFichero(FICHERO_ORIGINAL);

        // b. Conteo de caracteres
        int totalCaracteres = contarCaracteres(FICHERO_ORIGINAL);
        System.out.println("\nNúmero total de caracteres en el fichero: " + totalCaracteres);

        // c. Limpieza de contenido (eliminar la palabra "virus")
        limpiarFichero(FICHERO_ORIGINAL, FICHERO_LIMPIO, "virus");

        // d. Acceso aleatorio: mostrar 50 caracteres a partir de posición 11
        accesoAleatorio(FICHERO_LIMPIO, 11, 50);
    }

    // --- Método a: Lectura línea a línea ---
    private static void leerFichero(String nombreFichero) {
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    // --- Método b: Contar caracteres ---
    private static int contarCaracteres(String nombreFichero) {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            int c;
            while ((c = br.read()) != -1) {
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error al contar caracteres: " + e.getMessage());
        }
        return contador;
    }

    // --- Método c: Limpiar fichero eliminando palabra específica ---
    private static void limpiarFichero(String ficheroOrigen, String ficheroDestino, String palabra) {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheroOrigen));
             BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroDestino))) {

            String linea;
            System.out.println("\n=== Contenido de " + ficheroDestino + " sin la palabra '" + palabra + "' ===");
            while ((linea = br.readLine()) != null) {
                // Reemplazar todas las apariciones de la palabra
                String lineaLimpia = linea.replaceAll("(?i)" + palabra, ""); // (?i) para ignorar mayúsculas/minúsculas
                bw.write(lineaLimpia);
                bw.newLine();
                System.out.println(lineaLimpia); // mostrar en consola
            }

        } catch (IOException e) {
            System.out.println("Error al limpiar fichero: " + e.getMessage());
        }
    }

    // --- Método d: Acceso aleatorio ---
    private static void accesoAleatorio(String nombreFichero, long posicionInicio, int longitud) {
        try (RandomAccessFile raf = new RandomAccessFile(nombreFichero, "r")) {
            raf.seek(posicionInicio); // mover el puntero a la posición 11
            byte[] buffer = new byte[longitud];
            int leidos = raf.read(buffer, 0, longitud);
            if (leidos > 0) {
                String contenido = new String(buffer, 0, leidos);
                System.out.println("\n=== Primeros " + leidos + " caracteres desde la posición " + posicionInicio + " ===");
                System.out.println(contenido);
            }
        } catch (IOException e) {
            System.out.println("Error en acceso aleatorio: " + e.getMessage());
        }
    }
}