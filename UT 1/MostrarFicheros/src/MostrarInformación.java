// Uso de la clase File para mostrar información de ficheros y directorios
import java.io.File;
import java.io.IOException;

class ListadoDirectorio {
    public static void main(String[] args) {
        String ruta = ".";
        if (args.length >= 1) {
            ruta = args[0];
        }

        File fich = new File(ruta);
        if (!fich.exists()) {
            System.out.println("No existe el fichero o directorio (" + ruta + ").");
        } else {
            if (fich.isFile()) {
            System.out.println(ruta + " es un fichero.");
            } else {
                System.out.println(ruta + " es un directorio. Contenidos: ");
                File[] ficheros = fich.listFiles(); // Ojo, ficheros o directorios
                for (File f : ficheros) {
                    String textoDescr = f.isDirectory() ? "/" : "";
                    if (f.isFile()) {
                        textoDescr += " ";
                    }
                    System.out.println(textoDescr + f.getName());
                }
            }
        }
    }
}