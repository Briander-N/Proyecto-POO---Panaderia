package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.dao.ProductoDAO;
import com.example.panaderia_el_panshito.dao.UsuarioDAO;
import com.example.panaderia_el_panshito.dao.VentaDAO;
import com.example.panaderia_el_panshito.model.Producto;
import com.example.panaderia_el_panshito.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

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

    @FXML
    private void volverDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/example/panaderia_el_panshito/vista/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setUsuario(Sesion.getUsuarioActual());

            Stage stage = (Stage) lblTotalProductos.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo volver al dashboard: " + e.getMessage());
            alert.showAndWait();
        }
    }
}