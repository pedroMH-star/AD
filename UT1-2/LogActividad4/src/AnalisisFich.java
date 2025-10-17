import java.io.*;

/**
 * @autor Pedro Martínez Herrero
 * @since 25/09/2025
 * Actividad: Escritura, lectura y análisis de ficheros en Java
 *
 * Esta clase realiza varias operaciones sobre un archivo de log de seguridad:
 * 1. Lectura y muestra del contenido
 * 2. Añadir nuevas líneas
 * 3. Conteo de líneas, palabras y caracteres
 * 4. Creación de copia de seguridad
 * 5. Limpieza de palabras sensibles ("contraseña")
 */
public class AnalisisFich {

    private static final String ARCHIVO_ORIGINAL = "seguridad.txt";
    private static final String ARCHIVO_COPIA = "seguridad_copia.txt";

    public static void main(String[] args) {
        // 1. Mostrar el contenido original
        mostrarContenido(ARCHIVO_ORIGINAL);

        // 2. Añadir nuevas entradas
        anadirEntradas(ARCHIVO_ORIGINAL);

        // 3. Mostrar contenido tras añadir entradas
        mostrarContenido(ARCHIVO_ORIGINAL);

        // 4. Contar líneas, palabras y caracteres
        contarArchivo(ARCHIVO_ORIGINAL);

        // 5. Crear copia de seguridad
        copiarArchivo(ARCHIVO_ORIGINAL, ARCHIVO_COPIA);

        // 6. Limpiar palabras sensibles en la copia
        limpiarArchivo(ARCHIVO_COPIA, "contraseña");

        // 7. Mostrar contenido final de la copia
        mostrarContenido(ARCHIVO_COPIA);
    }

    /** Método para mostrar el contenido de un archivo por consola */
    private static void mostrarContenido(String nombreArchivo) {
        System.out.println("\nContenido del archivo: " + nombreArchivo);
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo " + nombreArchivo + ": " + e.getMessage());
        }
    }

    /** Método para añadir nuevas líneas a un archivo */
    private static void anadirEntradas(String nombreArchivo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            bw.write("Intento de acceso fallido\n");
            bw.write("Usuario autenticado correctamente\n");
            bw.write("Cambio de configuración realizado\n");
            bw.write("Pedro Martínez Herrero - 25/09/2025 - AD - Act 4\n");
            System.out.println("\nSe han añadido nuevas entradas al archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al añadir entradas: " + e.getMessage());
        }
    }

    /** Método para contar líneas, palabras y caracteres de un archivo */
    private static void contarArchivo(String nombreArchivo) {
        int lineas = 0, palabras = 0, caracteres = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas++;
                palabras += linea.split("\\s+").length; // separa por espacios
                caracteres += linea.length();
            }
            System.out.println("\nEstadísticas del archivo: " + nombreArchivo);
            System.out.println("Líneas: " + lineas);
            System.out.println("Palabras: " + palabras);
            System.out.println("Caracteres: " + caracteres);
        } catch (IOException e) {
            System.out.println("Error al contar el archivo: " + e.getMessage());
        }
    }

    /** Método para copiar un archivo a otro */
    private static void copiarArchivo(String origen, String destino) {
        try (BufferedReader br = new BufferedReader(new FileReader(origen));
             BufferedWriter bw = new BufferedWriter(new FileWriter(destino))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("\nArchivo copiado correctamente a: " + destino);

        } catch (IOException e) {
            System.out.println("Error al copiar archivo: " + e.getMessage());
        }
    }

    /** Método para eliminar todas las apariciones de una palabra en un archivo */
    private static void limpiarArchivo(String nombreArchivo, String palabra) {
        File tempFile = new File("temp.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                // Reemplaza todas las apariciones de la palabra por vacío
                String lineaLimpia = linea.replaceAll("\\b" + palabra + "\\b", "");
                bw.write(lineaLimpia.trim());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al limpiar el archivo: " + e.getMessage());
            return;
        }

        // Reemplaza el archivo original por el archivo limpio
        File archivoOriginal = new File(nombreArchivo);
        if (archivoOriginal.delete()) {
            tempFile.renameTo(archivoOriginal);
            System.out.println("\nArchivo " + nombreArchivo + " limpiado correctamente de la palabra: " + palabra);
        } else {
            System.out.println("Error al reemplazar el archivo original tras limpieza.");
        }
    }
}