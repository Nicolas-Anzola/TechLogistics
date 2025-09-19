package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {

    // Método para insertar producto
    public void insertarProducto(String nombre, String descripcion, double precio, int stock) {
        String sql = "INSERT INTO productos(nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, precio);
            ps.setInt(4, stock);

            ps.executeUpdate();
            System.out.println("✅ Producto insertado correctamente");

        } catch (SQLException e) {
            System.out.println("❌ Error insertando producto: " + e.getMessage());
        }
    }

    // Nuevo método para listar productos
    public void listarProductos() {
        String sql = "SELECT * FROM productos";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                    rs.getInt("id") + " - " +
                    rs.getString("nombre") + " - " +
                    rs.getString("descripcion") + " - " +
                    rs.getDouble("precio") + " - " +
                    rs.getInt("stock")
                );
            }

        } catch (SQLException e) {
            System.out.println("❌ Error listando productos: " + e.getMessage());
        }
    }
}
