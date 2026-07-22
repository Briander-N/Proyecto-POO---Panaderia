package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.dao.ProductoDAO;
import com.example.panaderia_el_panshito.dao.UsuarioDAO;
import com.example.panaderia_el_panshito.dao.VentaDAO;
import com.example.panaderia_el_panshito.model.Producto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class ReportesController {

    @FXML private Label lblTotalProductos;
    @FXML private Label lblTotalUsuarios;
    @FXML private Label lblTotalVentas;
    @FXML private Label lblStockBajo;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final VentaDAO ventaDAO = new VentaDAO();

    @FXML
    public void initialize() {
        var productos = productoDAO.listar();

        lblTotalProductos.setText("Total de productos: " + productos.size());
        lblTotalUsuarios.setText("Total de usuarios: " + usuarioDAO.listar().size());
        lblTotalVentas.setText("Total de ventas registradas: " + ventaDAO.totalVentas());

        long bajoStock = productos.stream().filter(p -> p.getStock() < 10).count();
        lblStockBajo.setText("Productos con stock bajo (<10): " + bajoStock);
    }

}
