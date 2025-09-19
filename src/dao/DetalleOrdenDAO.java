package dao;

import conexion.Conexion;
import java.sql.*;

public class DetalleOrdenDAO {

    public void insertarDetalle(int idOrden, int idProducto, int cantidad, double precioUnitario) {
        String sql = "INSERT INTO detalle_orden (id_orden, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            ps.setInt(2, idProducto);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precioUnitario);
            ps.executeUpdate();
            System.out.println("✅ Detalle de orden insertado");
        } catch (SQLException e) {
            System.out.println("❌ Error insertando detalle: " + e.getMessage());
        }
    }

    public void listarDetalles() {
        String sql = "SELECT d.id, o.id AS orden, p.nombre AS producto, d.cantidad, d.precio_unitario " +
                     "FROM detalle_orden d " +
                     "JOIN ordenes o ON d.id_orden = o.id " +
                     "JOIN productos p ON d.id_producto = p.id";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - Orden: " + rs.getInt("orden") +
                                   " - Producto: " + rs.getString("producto") +
                                   " - Cantidad: " + rs.getInt("cantidad") +
                                   " - Precio Unitario: " + rs.getDouble("precio_unitario"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Error listando detalles: " + e.getMessage());
        }
    }

    public void insertarDetalle(int i, int i0, int i1, int i2) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
