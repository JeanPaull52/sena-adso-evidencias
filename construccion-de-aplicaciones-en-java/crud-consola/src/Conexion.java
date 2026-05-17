import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

  //Datos para la conexión-----
  private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=ejemplo_colegio;encrypt=true;trustServerCertificate=true;";
  private static final String USUARIO = "sa";
  private static final String PASSWORD = "CONTRASEÑA";

  //Método encargado de realizar la conexión-----
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USUARIO, PASSWORD);
  }
}