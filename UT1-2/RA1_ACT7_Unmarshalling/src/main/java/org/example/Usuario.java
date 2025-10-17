package org.example;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

// Representa el nodo <usuario> del XML
@XmlRootElement(name = "usuario")
public class Usuario {

    private String id;
    private String nombre;
    private String email;
    private String rol;

    // Constructor vacío obligatorio para JAXB
    public Usuario() {}

    // Getters y setters

    @XmlAttribute(name = "id")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @XmlElement
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @XmlElement
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @XmlElement
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}

