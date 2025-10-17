import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Lectura de un documento XML de registros de seguridad usando DOM
 *
 * <p>Esta clase carga el archivo {@code logs.xml} y recorre su estructura para mostrar información de cada registro (log) en consola.
 * Además, al finalizar muestra estadísticas de cuántos registros hay de nivel INFO, WARNING y ERROR</p>
 *
 * <p><b>Objetivos:</b></p>
 * <ul>
 *   <li>Practicar la lectura de XML con DOM en Java</li>
 *   <li>Acceder a elementos y atributos del documento</li>
 *   <li>Entender cómo recorrer el árbol DOM</li>
 *   <li>Mostrar estadísticas simples sobre los registros</li>
 * </ul>
 *
 * @author Pedro Martínez Herrero
 * @since 01/10/2025
 */
public class LeerLogs {

    /**
     * Método principal que ejecuta la lectura del fichero XML {@code logs.xml}
     * <p>Realiza los siguientes pasos:</p>
     * <ol>
     *   <li>Crea el parser DOM con {@link DocumentBuilderFactory} y {@link DocumentBuilder}</li>
     *   <li>Carga y analiza el archivo XML en un objeto {@link Document}</li>
     *   <li>Normaliza el documento y obtiene el elemento raíz</li>
     *   <li>Recorre todos los elementos {@code <log>} del XML</li>
     *   <li>Extrae y muestra el atributo {@code id} y los valores de los elementos {@code nivel}, {@code mensaje} y {@code usuario}</li>
     *   <li>Cuenta el número de registros de tipo INFO, WARNING y ERROR</li>
     *   <li>Muestra estadísticas finales en consola</li>
     * </ol>
     *
     * @param args Argumentos de línea de comandos (no se utilizan en esta aplicación)
     */
    public static void main(String[] args) {
        try {
            // 1. Crear instancia de DocumentBuilderFactory y DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 2. Cargar el archivo XML en un objeto Document
            Document document = builder.parse("logs.xml");

            // 3. Normalizar el documento (opcional, buena práctica)
            document.getDocumentElement().normalize();

            // 4. Obtener el elemento raíz
            Element root = document.getDocumentElement();
            System.out.println("Elemento raíz: " + root.getNodeName());

            // 5. Obtener lista de todos los <log>
            NodeList listaLogs = document.getElementsByTagName("log");

            System.out.println("\n=== REGISTROS DE SEGURIDAD ===");

            // Contadores de estadísticas
            int countInfo = 0, countWarning = 0, countError = 0;

            // 6. Recorrer los nodos <log>
            for (int i = 0; i < listaLogs.getLength(); i++) {
                Node nodo = listaLogs.item(i);

                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element log = (Element) nodo;

                    // Atributo id
                    String id = log.getAttribute("id");

                    // Elementos hijo
                    String nivel = log.getElementsByTagName("nivel").item(0).getTextContent();
                    String mensaje = log.getElementsByTagName("mensaje").item(0).getTextContent();
                    String usuario = log.getElementsByTagName("usuario").item(0).getTextContent();

                    // Mostrar en consola
                    System.out.println("Log ID: " + id);
                    System.out.println("  Nivel: " + nivel);
                    System.out.println("  Mensaje: " + mensaje);
                    System.out.println("  Usuario: " + usuario);
                    System.out.println("--------------------------------");

                    // Contabilizar por nivel
                    if (nivel.equalsIgnoreCase("INFO")) countInfo++;
                    if (nivel.equalsIgnoreCase("WARNING")) countWarning++;
                    if (nivel.equalsIgnoreCase("ERROR")) countError++;
                }
            }

            // 7. Mostrar estadísticas finales
            System.out.println("\n* ESTADÍSTICAS *");
            System.out.println("Total INFO: " + countInfo);
            System.out.println("Total WARNING: " + countWarning);
            System.out.println("Total ERROR: " + countError);

        } catch (Exception e) {
            System.out.println("Error al leer el archivo XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}