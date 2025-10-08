package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Clase que realiza la deserialización (unmarshalling) de un objeto {@link Usuario}
 * desde un archivo XML llamado <b>usuario.xml</b>
 *
 * <p>Esta clase utiliza la librería <b>JAXB</b> (Jakarta XML Binding)
 *    para convertir un documento XML en un objeto Java de tipo {@link Usuario}</p>
 *
 * <p>El proceso incluye:</p>
 * <ol>
 *     <li>Comprobar si el archivo XML existe en el directorio del proyecto</li>
 *     <li>Crear un contexto JAXB asociado a la clase {@code Usuario}</li>
 *     <li>Usar un {@link Unmarshaller} para transformar el XML en un objeto Java</li>
 *     <li>Mostrar los datos deserializados por consola</li>
 * </ol>
 *
 * @author Pedro Martínez Herrero
 * @since 07/10/2025
 */
public class DeserializarUsuario {

    /**
     * Método principal que ejecuta el proceso de deserialización.
     *
     * <p>Comprueba si el archivo <b>usuario.xml</b> existe y,
     *    si es así, lo convierte en un objeto {@link Usuario} utilizando JAXB.
     *    Finalmente, muestra los datos del usuario en consola.</p>
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Comprobamos si existe el fichero usuario (el fichero xml donde está la información que quiero deserializar)
        File xmlFile = new File("usuario.xml");
        if (!xmlFile.exists()) {
            System.out.println("El archivo no existe en el directorio del proyecto");
            return;
        }

        try {
            // 1º Creamos el contexto para la clase Usuario
            JAXBContext context = JAXBContext.newInstance(Usuario.class);

            // 2º Creamos el unmarshaller (convierte XML -> objeto)
            Unmarshaller um = context.createUnmarshaller();

            // 3º Deserializar el archivo XML: leer el archivo XML
            Usuario usuario = (Usuario) um.unmarshal(xmlFile);

            // 4º Mostrar los datos por consola
            System.out.println("* DATOS DEL USUARIO *");

            System.out.println("ID: " + usuario.getId());
            System.out.println("NOMBRE: " + usuario.getNombre());
            System.out.println("EMAIL: " + usuario.getEmail());
            System.out.println("ROL: " + usuario.getRol());

        } catch (JAXBException e) {
            System.err.println("ERROR. Fallo haciendo unmarshallin: " + e.getMessage());

        }
    }
}