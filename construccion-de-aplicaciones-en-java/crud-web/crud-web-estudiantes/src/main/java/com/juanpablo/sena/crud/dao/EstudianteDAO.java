package com.juanpablo.sena.crud.dao;

import com.juanpablo.sena.crud.model.Estudiante;
import com.juanpablo.sena.crud.util.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
  
  //Método CREATE-----
  public void insertar(Estudiante e) {
    String sql = "INSERT INTO estudiante (nombre, correo, celular, direccion) VALUES (?, ?, ?, ?)";

    try (Connection con = Conexion.getConnection();
    PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setString(1, e.getNombre());
      ps.setString(2, e.getCorreo());
      ps.setString(3, e.getCelular());
      ps.setString(4, e.getDireccion());

      int filasAfetadas = ps.executeUpdate();
      System.out.println("Estudiante insertado. Se afectaron " + filasAfetadas + " filas.");
    } catch (SQLException ex) {
      System.out.println("Error al insetar el estudiante: " + ex.getMessage());
    }
  }

  //Método READ sin ID (trae tódos los estudiantes)-----
  public List<Estudiante> listarTodos() {
    List<Estudiante> estudiantes = new ArrayList<>();
    String sql = "SELECT id_estudiante, nombre, correo, celular, direccion FROM estudiante";

    try(Connection con = Conexion.getConnection();
    PreparedStatement ps = con.prepareStatement(sql);
    ResultSet rs = ps.executeQuery()) {

    while(rs.next()) {
      Estudiante e = new Estudiante(
        rs.getInt("id_estudiante"),
        rs.getString("nombre"),
        rs.getString("correo"),
        rs.getString("celular"),
        rs.getString("direccion")
      );
      estudiantes.add(e);
    }
  } catch(SQLException ex) {
    System.out.println("Error al listar los estudiantes: " + ex.getMessage());
  }
  return estudiantes;
  }

  //Método READ con ID-----
  public Estudiante buscarPorId(int id) {
    String sql = "SELECT id_estudiante, nombre, correo, celular, direccion FROM estudiante WHERE id_estudiante = ?";

    try(Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, id);

      try(ResultSet rs = ps.executeQuery()) {
        if(rs.next()) {
          return new Estudiante(
            rs.getInt("id_estudiante"),
            rs.getString("nombre"),
            rs.getString("correo"),
            rs.getString("celular"),
            rs.getString("direccion")
          );
        }
      } 

      } catch (SQLException ex) {
        System.out.println("Error al consultar al estudiante: " + ex.getMessage());
    }
    return null;
  }

  //Método UPDATE-----
  public boolean actualizar(Estudiante e) {
    String sql = "UPDATE estudiante SET nombre = ?, correo = ?, celular = ?, direccion = ? WHERE id_estudiante = ?";

    try (Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, e.getNombre());
        ps.setString(2, e.getCorreo());
        ps.setString(3, e.getCelular());
        ps.setString(4, e.getDireccion());
        ps.setInt(5, e.getIdEstudiante());

        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Estudiante actualizado correctamente.");
            return true;
        } else {
            System.out.println("No se encontró al estudiante con ID " + e.getIdEstudiante());
            return false;
        }

    } catch (SQLException ex) {
        System.out.println("Error al actualizar al estudiante: " + ex.getMessage());
        return false;
    }
  }

  //Método DELETE-----
  public boolean eliminar(int id) {
    String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";

    try (Connection con = Conexion.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);

        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            System.out.println("Estudiante eliminado correctamente.");
            return true;
        } else {
            System.out.println("No se encontró un estudiante con ID " + id);
            return false;
        }

    } catch (SQLException ex) {
        System.out.println("Error al eliminar estudiante: " + ex.getMessage());
        return false;
    }
}
}
