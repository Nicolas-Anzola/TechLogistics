package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    // Método para insertar un cliente
    public void insertarCliente(String nombre, String telefono, String direccion) {
        String sql = "INSERT INTO clientes (nombre, telefono, direccion) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, direccion);
            ps.executeUpdate();
            System.out.println("✅ Cliente insertado correctamente");
        } catch (SQLException e) {
            System.out.println("❌ Error insertando cliente: " + e.getMessage());
        }
    }

    // Método para listar clientes
    public void listarClientes() {
        String sql = "SELECT * FROM clientes";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " +
                                   rs.getString("nombre") + " - " +
                                   rs.getString("telefono") + " - " +
                                   rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error listando clientes: " + e.getMessage());
        }
    }
}
