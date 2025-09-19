package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

public class OrdenDAO {

    // Método para insertar una orden
    public void insertarOrden(int idCliente, Date fecha, String estado) {
        String sql = "INSERT INTO ordenes (id_cliente, fecha, estado) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idCliente);
            ps.setDate(2, fecha);
            ps.setString(3, estado);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idOrden = rs.getInt(1);
                    System.out.println("✅ Orden creada con ID: " + idOrden);
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error insertando orden: " + e.getMessage());
        }
    }

    // Método para listar todas las órdenes
    public void listarOrdenes() {
        String sql = "SELECT o.id, c.nombre AS cliente, o.fecha, o.estado " +
                     "FROM ordenes o JOIN clientes c ON o.id_cliente = c.id";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " +
                                   rs.getString("cliente") + " - " +
                                   rs.getDate("fecha") + " - " +
                                   rs.getString("estado"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error listando ordenes: " + e.getMessage());
        }
    }
}
