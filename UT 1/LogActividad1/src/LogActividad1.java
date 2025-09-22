//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogActividad1 {
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

        try {
            File logFile = new File(directorio, "seguridad_actividad1.log");
            if (logFile.createNewFile()) {  // Crea el fichero si no existía
                System.out.println("Fichero de log creado correctamente: " + logFile.getName());
            } else {
                System.out.println("El fichero ya existía: " + logFile.getName());
            }
        } catch (Exception e) {
            System.out.println("Error al crear el fichero: " + e.getMessage());
        }
    }
}