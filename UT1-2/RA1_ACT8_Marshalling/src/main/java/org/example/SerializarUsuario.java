package org.example;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;

/**
 * Clase principal que realiza el proceso de <b>marshalling</b>
 * (serialización de objeto Java a XML) utilizando la API JAXB
 *
 * <p>Este programa crea un objeto {@link Usuario} con datos de ejemplo y lo convierte
 *    en un archivo XML llamado <b>usuario.xml</b> dentro del directorio del proyecto.
 *    También muestra el resultado por consola</p>
 *
 * <h2>Clases utilizadas</h2>
 * <ul>
 *   <li>{@link Usuario} - Modelo de datos del usuario.</li>
 *   <li>{@link JAXBContext} - Gestiona el contexto JAXB para la clase.</li>
 *   <li>{@link Marshaller} - Convierte el objeto Java a formato XML.</li>
 * </ul>
 *
 * @author Pedro Martínez Herrero
 * @since 08/10/2025
 */
public class SerializarUsuario {

    /**
     * Método principal del programa.
     * <p>Realiza los siguientes pasos: </p>
     *
     * <ol>
     *   <li>Crea un objeto {@link Usuario} con datos de ejemplo</li>
     *   <li>Inicializa un {@link JAXBContext} para la clase Usuario</li>
     *   <li>Crea un {@link Marshaller} para generar el XML</li>
     *   <li>Guarda el resultado en un archivo y lo muestra en consola</li>
     * </ol>
     *
     * @param args Argumentos de la línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            // 1. Crear el objeto Usuario con datos de ejemplo
            Usuario usuario = new Usuario("1", "Pedro Martínez", "pedro.martinez@example.com", "Administrador");

            // 2. Crear el contexto JAXB indicando la clase que se va a procesar
            JAXBContext context = JAXBContext.newInstance(Usuario.class);

            // 3. Crear el objeto Marshaller, encargado de convertir el objeto a XML
            Marshaller marshaller = context.createMarshaller();

            // 4. Formatear el XML con sangrías para mejor lectura
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // 5. Guardar el archivo XML en el directorio del proyecto
            File archivo = new File("usuario.xml");
            marshaller.marshal(usuario, archivo);

            // 6. Mostrar el XML por consola también
            System.out.println("* DATOS DEL USUARIO *");
            System.out.println("ID: " + usuario.getId());
            System.out.println("NOMBRE: " + usuario.getNombre());
            System.out.println("EMAIL: " + usuario.getEmail());
            System.out.println("ROL: " + usuario.getRol());

            System.out.println("\nArchivo 'usuario.xml' creado correctamente en el directorio del proyecto");

        } catch (JAXBException e) {
            System.out.println("Error al serializar el objeto: " + e.getMessage());
        }
    }
}