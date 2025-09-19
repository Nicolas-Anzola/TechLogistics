package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/techlogistics?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "luna1311#"; 

    // Método para obtener la conexión a la base de datos
    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexión establecida con la BD");
            return con;
        } catch (SQLException e) {
            System.out.println("❌ Error en la conexión: " + e.getMessage());
            return null;
        }
    }
}
