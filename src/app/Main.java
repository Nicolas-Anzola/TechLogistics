package app;

import dao.*;
import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        OrdenDAO ordenDAO = new OrdenDAO();
        DetalleOrdenDAO detalleDAO = new DetalleOrdenDAO();

        // Insertar cliente
        clienteDAO.insertarCliente("Pedro López", "3112233445", "Calle 50 #10-20");

        // Insertar productos
        productoDAO.insertarProducto("Monitor", "Monitor LED 24 pulgadas", 600000, 15);
        productoDAO.insertarProducto("Teclado", "Teclado inalámbrico", 90000, 30);

        // Listar clientes y productos
        System.out.println("\nClientes:");
        clienteDAO.listarClientes();
        System.out.println("\nProductos:");
        productoDAO.listarProductos();

        // Insertar orden
        ordenDAO.insertarOrden(1, new Date(System.currentTimeMillis()), "pendiente");

        // Insertar detalle de orden
        detalleDAO.insertarDetalle(1, 1, 2, 600000);
        detalleDAO.insertarDetalle(1, 2, 1, 90000);

        // Listar ordenes y detalles
        System.out.println("\nÓrdenes:");
        ordenDAO.listarOrdenes();
        System.out.println("\nDetalle de Órdenes:");
        detalleDAO.listarDetalles();
    }
}
