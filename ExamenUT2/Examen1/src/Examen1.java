import java.io.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

/**
 * @autor Pedro Martínez Herrero
 * @since 14/10/2025
 * @until 16/10/2025
 * Actividad: Escritura y lectura de archivos en Java
 */
public class Examen1 {

    private static final String DIRECTORIO_TXTS = "accesos/seguridad";
    private static final String NOMBRE_FICHERO = "accesos.txt";
    private static final String FICHERO_COPIA = "acessos_martinez_copia.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== Menú de gestión de txt ===");
            System.out.println("1. Crear directorio con fichero de txt");
            System.out.println("2. Renombrar fichero de txt");
            System.out.println("3. Escribir fichero de txt");
            System.out.println("4. Hacer copia de fichero de txt");
            System.out.println("5. Eliminar fichero de txt");
            System.out.println("6. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1 -> crearFichero();
                case 2 -> renombrarFichero();
                case 3 -> escribirFichero();
                case 4 -> copiarFichero();
                case 5 -> eliminarFichero();
                case 6 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 6);

        sc.close();
    }

    // Método 1: Crear directorio y fichero
    private static void crearFichero() {
        File fichero = null; // declarar antes del try

        try {
            File directorio = new File(DIRECTORIO_TXTS);
            if (!directorio.exists()) {
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado correctamente: " + DIRECTORIO_TXTS);
                } else {
                    System.out.println("Error al crear el directorio.");
                    return;
                }
            }

            fichero = new File(directorio, NOMBRE_FICHERO);
            if (fichero.createNewFile()) {
                System.out.println("Fichero creado correctamente: " + fichero.getName());
            } else {
                System.out.println("El fichero ya existía: " + fichero.getName());
            }

        } catch (IOException e) {
            System.out.println("Error al crear el fichero: " + e.getMessage());
        }

        // Ahora sí podemos usar fichero fuera del try
        if (fichero != null) {
            System.out.println("Ruta absoluta del fichero: " + fichero.getAbsolutePath());
        }
    }

    // Método 2: Renombrar fichero
    private static void renombrarFichero() {
        File fichero = new File(DIRECTORIO_TXTS, NOMBRE_FICHERO);
        if (!fichero.exists()) {
            System.out.println("No existe el fichero");
            return;
        }

        File ficheroRenombrado = new File(DIRECTORIO_TXTS, "accesos_martinez.txt");
        if (fichero.renameTo(ficheroRenombrado)) {
            System.out.println("Fichero renombrado a: " + ficheroRenombrado.getName());
        } else {
            System.out.println("Error al renombrar el fichero.");
        }
    }

    // Método 3: Escribir solo en el fichero renombrado
    private static void escribirFichero() {
        File ficheroRenombrado = new File(DIRECTORIO_TXTS, "accesos_martinez.txt");
        if (!ficheroRenombrado.exists()) {
            System.out.println("No existe el fichero renombrado o renómbralo primero");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroRenombrado, true))) {
            bw.write("[INFO] Inicio de registro de actividad.\n");
            bw.write("[INFO] Registro generado correctamente.\n");
            bw.write("Pedro Martínez Herrero - 14/10/2025 - AD - Examen\n");
            System.out.println("\nSe han añadido nuevas entradas al archivo: " + ficheroRenombrado.getName());
        } catch (IOException e) {
            System.out.println("Error al añadir entradas: " + e.getMessage());
        }
    }

    // Método 4: Copiar fichero que exista en ese momento
    private static void copiarFichero() {
        File directorio = new File(DIRECTORIO_TXTS);
        File ficheroOrigen = new File(directorio, "accesos_martinez.txt");

        // Si el renombrado no existe, intentamos con el original
        if (!ficheroOrigen.exists()) {
            ficheroOrigen = new File(directorio, NOMBRE_FICHERO);
        }

        if (!ficheroOrigen.exists()) {
            System.out.println("No hay fichero para copiar.");
            return;
        }

        File ficheroDestino = new File(directorio, FICHERO_COPIA);

        try (BufferedReader br = new BufferedReader(new FileReader(ficheroOrigen));
             BufferedWriter bw = new BufferedWriter(new FileWriter(ficheroDestino))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }

            System.out.println("\nArchivo copiado correctamente a: " + ficheroDestino.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error al copiar archivo: " + e.getMessage());
        }
    }

    // Método 5: Eliminar fichero
    private static void eliminarFichero() {
        File directorio = new File(DIRECTORIO_TXTS);
        File[] ficheros = directorio.listFiles((dir, name) -> name.startsWith("accesos_martinez") && name.endsWith(".txt"));

        if (ficheros != null && ficheros.length > 0) {
            for (File f : ficheros) {
                if (f.delete()) {
                    System.out.println("Fichero eliminado: " + f.getName());
                } else {
                    System.out.println("No se pudo eliminar: " + f.getName());
                }
            }
        } else {
            System.out.println("No hay ficheros de log para eliminar");
        }
    }
}