import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase que simula el acceso aleatorio a un archivo de usuarios utilizando {@link RandomAccessFile}.
 *
 * <p>El archivo {@code usuarios.dat} almacena registros de usuarios con un tamaño fijo.
 * Cada registro está formado por un identificador (ID de 3 cifras) y un nombre,
 * rellenado con espacios hasta alcanzar un tamaño uniforme de {@link #TAMANIO_REGISTRO} caracteres.</p>
 *
 * <p>Permite realizar operaciones típicas de gestión de usuarios:
 * <ul>
 *   <li>Lectura de un usuario en una posición concreta mediante {@link #leerUsuario(RandomAccessFile, int)}</li>
 *   <li>Modificación de datos sobrescribiendo un registro con {@link #escribirUsuario(RandomAccessFile, int, String)}</li>
 *   <li>Añadir nuevos usuarios al final del archivo</li>
 *   <li>Simular la eliminación de un usuario sobrescribiéndolo con espacios</li>
 * </ul>
 * </p>
 *
 * @author Pedro Martínez Herrero
 * @since 28/09/2025
 */

public class LogActividad5 { // Acceso a Usuarios

    /**
     * Tamaño fijo de cada registro de usuario en el archivo, en bytes.
     * <p>Se calcula considerando el ID (3 caracteres), el nombre y los espacios de relleno.
     * Garantiza que cada usuario ocupa siempre la misma cantidad de espacio en el archivo,
     * lo que permite el acceso directo mediante {@code seek()}.</p>
     */
    private static final int TAMANIO_REGISTRO = 15; // bytes (1 char = 1 byte si usamos writeBytes)

    /**
     * Método principal del programa.
     *
     * <p>Realiza las siguientes operaciones sobre el archivo {@code usuarios.dat}:</p>
     * <ol>
     *     <li>Crea un archivo con cinco usuarios iniciales si aún no existe.</li>
     *     <li>Muestra el contenido inicial del archivo.</li>
     *     <li>Lee el usuario con ID 003 mediante acceso aleatorio.</li>
     *     <li>Modifica el usuario con ID 002 cambiando su nombre a "Pedro".</li>
     *     <li>Añade un nuevo usuario con ID 006 ("Ana") al final del archivo.</li>
     *     <li>Simula la eliminación del usuario con ID 004 sobrescribiéndolo con espacios.</li>
     * </ol>
     *
     * @param args argumentos de la línea de comandos (no utilizados en este programa)
     */
    public static void main(String[] args) {
        String archivo = "usuarios.dat";

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw")) {

            // 1. Crear archivo con 5 usuarios iniciales (solo la primera vez)
            if (raf.length() == 0) {
                escribirUsuario(raf, 0, "001 Juan");
                escribirUsuario(raf, 1, "002 María");
                escribirUsuario(raf, 2, "003 Mila");
                escribirUsuario(raf, 3, "004 Abraham");
                escribirUsuario(raf, 4, "005 Carlos");
            }

            System.out.println("Contenido inicial:");
            mostrarUsuarios(raf);

            // 2. Lectura aleatoria: leer usuario con ID 003 (posición 2)
            System.out.println("\nLectura del usuario con ID 003:");
            String usuario003 = leerUsuario(raf, 2);
            System.out.println(usuario003);

            // 3. Modificación de datos: cambiar usuario 002 por Pedro (posición 1)
            System.out.println("\nModificando usuario 002 por '002 Pedro'...");
            escribirUsuario(raf, 1, "002 Pedro");
            mostrarUsuarios(raf);

            // 4. Añadir un nuevo usuario al final (006 Ana)
            System.out.println("\nAñadiendo nuevo usuario 006 Ana...");
            long numRegistros = raf.length() / TAMANIO_REGISTRO;
            escribirUsuario(raf, (int) numRegistros, "006 Ana");
            mostrarUsuarios(raf);

            // 5. Eliminar (simular) usuario 004 -> sobrescribir con espacios
            System.out.println("\nEliminando usuario 004 (sobrescribir con espacios)...");
            escribirUsuario(raf, 3, "004            "); // ID visible pero nombre vacío
            mostrarUsuarios(raf);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escribe un registro de usuario en una posición concreta del archivo.
     *
     * @param raf       objeto {@link RandomAccessFile} que gestiona el acceso al archivo
     * @param posicion  número de registro (empezando en 0) donde se escribirá el usuario
     * @param datos     cadena con los datos del usuario (ID + nombre)
     * @throws IOException si ocurre un error de entrada/salida
     */
    private static void escribirUsuario(RandomAccessFile raf, int posicion, String datos) throws IOException {
        raf.seek(posicion * TAMANIO_REGISTRO);
        String registro = String.format("%-" + TAMANIO_REGISTRO + "s", datos); // rellena con espacios
        raf.writeBytes(registro);
    }

    /**
     * Lee un registro de usuario desde una posición concreta del archivo.
     *
     * @param raf       objeto {@link RandomAccessFile} que gestiona el acceso al archivo
     * @param posicion  número de registro (empezando en 0) a leer
     * @return una cadena con los datos del usuario leídos (sin espacios extra al final)
     * @throws IOException si ocurre un error de entrada/salida
     */
    private static String leerUsuario(RandomAccessFile raf, int posicion) throws IOException {
        byte[] buffer = new byte[TAMANIO_REGISTRO];
        raf.seek(posicion * TAMANIO_REGISTRO);
        raf.readFully(buffer);
        return new String(buffer).trim();
    }

    /**
     * Muestra en consola todos los usuarios almacenados en el archivo.
     *
     * @param raf objeto {@link RandomAccessFile} que gestiona el acceso al archivo
     * @throws IOException si ocurre un error de entrada/salida
     */
    private static void mostrarUsuarios(RandomAccessFile raf) throws IOException {
        long numRegistros = raf.length() / TAMANIO_REGISTRO;
        for (int i = 0; i < numRegistros; i++) {
            System.out.println(leerUsuario(raf, i));
        }
    }
}