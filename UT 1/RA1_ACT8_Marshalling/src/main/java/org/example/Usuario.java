package org.example;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

/**
 * Representa un usuario dentro del sistema.
 * <p>Esta clase se utiliza para demostrar el proceso de <b>marshalling</b> con JAXB,
 * convirtiendo un objeto Java en un documento XML estructurado.</p>
 *
 * <h2>Anotaciones JAXB</h2>
 * <ul>
 *   <li>{@code @XmlRootElement}: Define el elemento raíz del XML.</li>
 *   <li>{@code @XmlElement}: Indica que cada atributo será un elemento XML.</li>
 * </ul>
 *
 * @author Pedro Martínez Herrero
 * @since 07/10/2025
 */
@XmlRootElement(name = "usuario")
public class Usuario {

    // Identificador único del usuario
    private String id;

    // Nombre completo del usuario
    private String nombre;

    // Correo electrónico del usuario
    private String email;

    // Rol o tipo de usuario dentro del sistema (por ejemplo, Administrador)
    private String rol;

    /**
     * Constructor vacío requerido por JAXB
     * <p>JAXB necesita un constructor sin parámetros para poder crear
     *    instancias de la clase durante el proceso de serialización y deserialización</p>
     */
    public Usuario() {
    }

    /**
     * Constructor principal que permite inicializar un objeto Usuario con todos sus datos
     *
     * @param id     Identificador único del usuario
     * @param nombre Nombre completo del usuario
     * @param email  Dirección de correo electrónico
     * @param rol    Rol o tipo de usuario en el sistema
     */
    public Usuario(String id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    /**
     * Obtiene el identificador del usuario
     * @return Cadena que representa el ID del usuario
     */
    @XmlElement
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario
     * @param id Nuevo identificador a asignar
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre completo del usuario
     * @return Nombre completo del usuario
     */
    @XmlElement
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del usuario
     * @param nombre Nombre a asignar al usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario
     * @return Dirección de correo electrónico
     */
    @XmlElement
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario
     * @param email Nuevo correo electrónico a asignar
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el rol del usuario en el sistema
     * @return Rol actual del usuario
     */
    @XmlElement
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario
     * @param rol Nuevo rol a asignar
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}