package com.juanpablo.sena.crud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

  //Datos para la conexión-----
  private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ejemplo_colegio;encrypt=true;trustServerCertificate=true;";
  private static final String USUARIO = "sa";
  private static final String PASSWORD = "LA_CONTRASEÑA_AQUI";

  static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se pudo cargar el driver JDBC de SQL Server", e);
        }
    }

  //Método encargado de realizar la conexión-----
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USUARIO, PASSWORD);
  }
}