package com.juanpablo.sena.crud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Entidad que representa un estudiante en la BD.Cada instancia de esta clase = una fila en la tabla 'estudiante'.//

@Entity //Entidad JPA - la clase que se va a mapear
@Table(name = "estudiante") //Nombre de la tabla en la BD
public class Estudiante {

    @Id //Marca el atributo como la PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Le dice a Hibernate que el valor se genera automáticamente
    @Column(name = "id_estudiante") //Especifica el nombre exacto de la columna en la BD
    private int idEstudiante;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 100)
    private String correo;

    @Column(name = "celular", length = 20)
    private String celular;

    @Column(name = "direccion", length = 200)
    private String direccion;

    // Constructor vacío: OBLIGATORIO para JPA
    
    public Estudiante() {
    }

    // Constructor con parámetros sin ID (CREATE)
    public Estudiante(String nombre, String correo, String celular, String direccion) {
        this.nombre = nombre;
        this.correo = correo;
        this.celular = celular;
        this.direccion = direccion;
    }

    // Constructor para EDITAR (con id)
    public Estudiante(int idEstudiante, String nombre, String correo, String celular, String direccion) {
    this.idEstudiante = idEstudiante;
    this.nombre = nombre;
    this.correo = correo;
    this.celular = celular;
    this.direccion = direccion;
    }

    // Getters y Setters
    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "idEstudiante=" + idEstudiante +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", celular='" + celular + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
