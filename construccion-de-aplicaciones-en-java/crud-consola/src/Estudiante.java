import java.text.DateFormat;

public class Estudiante {
  //Atributos privados (respresentan las columnas de la tabla en SQL Server)
  private int idEstudiante;
  private String nombre;
  private String correo;
  private String celular;
  private String direccion;

// --Constructores-------
//Constructor vacio

public Estudiante() {}

//Constructor con parámetros
public Estudiante(int idEstudiante, String nombre, String correo, String celular, String direccion) {
  this.idEstudiante = idEstudiante;
  this.nombre = nombre;
  this.correo = correo;
  this.celular = celular;
  this.direccion = direccion;
}

//Constructor sin ID para los insert
public Estudiante(String nombre, String correo, String celular, String direccion) {
  this.nombre = nombre;
  this.correo = correo;
  this.celular = celular;
  this.direccion = direccion;
}

//Creación de Getters y Setters
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
  return "Estudiante [idEstudiante=" + idEstudiante + ", nombre=" + nombre + ", correo=" + correo + ", celular="
      + celular + ", direccion=" + direccion + "]";
}

}