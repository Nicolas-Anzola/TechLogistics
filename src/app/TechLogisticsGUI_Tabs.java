package app;

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.OrdenDAO;
import dao.DetalleOrdenDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class TechLogisticsGUI_Tabs extends JFrame {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private OrdenDAO ordenDAO = new OrdenDAO();
    private DetalleOrdenDAO detalleDAO = new DetalleOrdenDAO();

    private JTable tableClientes;
    private JTable tableProductos;
    private JTable tableOrdenes;

    public TechLogisticsGUI_Tabs() {
        setTitle("TechLogistics - Sistema de Gestión");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 248, 255)); // fondo suave

        // Título principal
        JLabel titulo = new JLabel("TechLogistics S.A. - Gestión de Pedidos y Envíos", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 144, 255));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tabbedPane.setBackground(new Color(220, 230, 241));

        // --- Clientes ---
        JPanel panelClientes = new JPanel(new BorderLayout());
        panelClientes.setBackground(new Color(240, 248, 255));
        tableClientes = new JTable();
        JScrollPane scrollClientes = new JScrollPane(tableClientes);
        JButton btnAgregarCliente = new JButton("Agregar Cliente");
        btnAgregarCliente.setBackground(new Color(30, 144, 255));
        btnAgregarCliente.setForeground(Color.WHITE);
        btnAgregarCliente.setFocusPainted(false);
        btnAgregarCliente.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregarCliente.addActionListener(e -> agregarCliente());
        JPanel panelBotonCliente = new JPanel();
        panelBotonCliente.setBackground(new Color(240, 248, 255));
        panelBotonCliente.add(btnAgregarCliente);

        panelClientes.add(scrollClientes, BorderLayout.CENTER);
        panelClientes.add(panelBotonCliente, BorderLayout.SOUTH);
        tabbedPane.addTab("Clientes", panelClientes);

        // --- Productos ---
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBackground(new Color(240, 248, 255));
        tableProductos = new JTable();
        JScrollPane scrollProductos = new JScrollPane(tableProductos);
        JButton btnAgregarProducto = new JButton("Agregar Producto");
        btnAgregarProducto.setBackground(new Color(34, 139, 34));
        btnAgregarProducto.setForeground(Color.WHITE);
        btnAgregarProducto.setFocusPainted(false);
        btnAgregarProducto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAgregarProducto.addActionListener(e -> agregarProducto());
        JPanel panelBotonProducto = new JPanel();
        panelBotonProducto.setBackground(new Color(240, 248, 255));
        panelBotonProducto.add(btnAgregarProducto);

        panelProductos.add(scrollProductos, BorderLayout.CENTER);
        panelProductos.add(panelBotonProducto, BorderLayout.SOUTH);
        tabbedPane.addTab("Productos", panelProductos);

        // --- Órdenes ---
        JPanel panelOrdenes = new JPanel(new BorderLayout());
        panelOrdenes.setBackground(new Color(240, 248, 255));
        tableOrdenes = new JTable();
        JScrollPane scrollOrdenes = new JScrollPane(tableOrdenes);
        JButton btnCrearOrden = new JButton("Crear Orden");
        btnCrearOrden.setBackground(new Color(255, 140, 0));
        btnCrearOrden.setForeground(Color.WHITE);
        btnCrearOrden.setFocusPainted(false);
        btnCrearOrden.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCrearOrden.addActionListener(e -> crearOrden());
        JPanel panelBotonOrden = new JPanel();
        panelBotonOrden.setBackground(new Color(240, 248, 255));
        panelBotonOrden.add(btnCrearOrden);

        panelOrdenes.add(scrollOrdenes, BorderLayout.CENTER);
        panelOrdenes.add(panelBotonOrden, BorderLayout.SOUTH);
        tabbedPane.addTab("Órdenes", panelOrdenes);

        add(tabbedPane, BorderLayout.CENTER);

        // Cargar datos iniciales
        listarClientes();
        listarProductos();
        listarOrdenes();

        // Centramos celdas de las tablas
        centrarColumnas(tableClientes);
        centrarColumnas(tableProductos);
        centrarColumnas(tableOrdenes);
    }

    private void centrarColumnas(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    // --- Métodos existentes ---
    private void agregarCliente() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del Cliente:");
        String telefono = JOptionPane.showInputDialog(this, "Teléfono:");
        String direccion = JOptionPane.showInputDialog(this, "Dirección:");
        if (nombre != null && !nombre.isEmpty()) {
            clienteDAO.insertarCliente(nombre, telefono, direccion);
            listarClientes();
        }
    }

    private void listarClientes() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Teléfono", "Dirección"}, 0);
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            System.setOut(ps);
            clienteDAO.listarClientes();
            String[] lines = baos.toString().split("\n");
            for (String line : lines) {
                String[] parts = line.split(" - ");
                if (parts.length == 4) {
                    model.addRow(parts);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tableClientes.setModel(model);
    }

    private void agregarProducto() {
        String nombre = JOptionPane.showInputDialog(this, "Nombre del Producto:");
        String descripcion = JOptionPane.showInputDialog(this, "Descripción:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio:"));
        int stock = Integer.parseInt(JOptionPane.showInputDialog(this, "Stock:"));
        productoDAO.insertarProducto(nombre, descripcion, precio, stock);
        listarProductos();
    }

    private void listarProductos() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock"}, 0);
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            System.setOut(ps);
            productoDAO.listarProductos();
            String[] lines = baos.toString().split("\n");
            for (String line : lines) {
                String[] parts = line.split(" - ");
                if (parts.length == 5) {
                    model.addRow(parts);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tableProductos.setModel(model);
    }

    private void crearOrden() {
        try {
            int idCliente = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del Cliente:"));
            Date fecha = new Date(System.currentTimeMillis());
            String estado = "pendiente";
            ordenDAO.insertarOrden(idCliente, fecha, estado);

            int idOrden = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el ID de la Orden creada:"));
            int continuar = 0;
            do {
                int idProducto = Integer.parseInt(JOptionPane.showInputDialog(this, "ID del Producto:"));
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Cantidad:"));
                double precio = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio Unitario:"));
                detalleDAO.insertarDetalle(idOrden, idProducto, cantidad, precio);
                continuar = JOptionPane.showConfirmDialog(this, "Agregar otro producto?", "Continuar", JOptionPane.YES_NO_OPTION);
            } while (continuar == JOptionPane.YES_OPTION);

            listarOrdenes();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creando orden: " + ex.getMessage());
        }
    }

    private void listarOrdenes() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Cliente", "Fecha", "Estado"}, 0);
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.PrintStream ps = new java.io.PrintStream(baos);
            System.setOut(ps);
            ordenDAO.listarOrdenes();
            String[] lines = baos.toString().split("\n");
            for (String line : lines) {
                String[] parts = line.split(" - ");
                if (parts.length == 4) {
                    model.addRow(parts);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tableOrdenes.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TechLogisticsGUI_Tabs().setVisible(true));
    }
}
