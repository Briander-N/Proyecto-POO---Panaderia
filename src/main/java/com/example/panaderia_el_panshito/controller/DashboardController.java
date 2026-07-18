package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.model.Usuario;
import com.example.panaderia_el_panshito.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML private Label lblNombre;
    @FXML private Label lblRol;

    @FXML private VBox panelAdmin;
    @FXML private VBox panelCajero;
    @FXML private VBox panelReportes;

    private Usuario usuario;

    // Este método lo llama el LoginController (o cualquier pantalla al volver) al entrar
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblNombre.setText("Usuario: " + usuario.getNombre());
        lblRol.setText("Rol: " + usuario.getRol());
        adaptarMenuPorRol();
    }

    private void adaptarMenuPorRol() {
        String rol = usuario.getRol();

        ocultar(panelAdmin);
        ocultar(panelCajero);
        ocultar(panelReportes);

        switch (rol) {
            case "ADMIN" -> mostrar(panelAdmin);
            case "CAJERO" -> mostrar(panelCajero);
            case "REPORTES" -> mostrar(panelReportes);
        }
    }

    private void mostrar(VBox panel) {
        panel.setVisible(true);
        panel.setManaged(true);
    }

    private void ocultar(VBox panel) {
        panel.setVisible(false);
        panel.setManaged(false);
    }

    @FXML
    private void irProductos() {
        navegarA("/com/example/panaderia_el_panshito/vista/producto.fxml", "Gestión de Productos");
    }

    @FXML
    private void irVenta() {
        navegarA("/com/example/panaderia_el_panshito/vista/venta.fxml", "Registrar Venta");
    }

    @FXML
    private void irUsuarios() {
        navegarA("/com/example/panaderia_el_panshito/vista/usuario.fxml", "Gestión de Usuarios");
    }

    @FXML
    private void irReportes() {
        navegarA("/com/example/panaderia_el_panshito/vista/reportes.fxml", "Módulo de Reportes");
    }

    @FXML
    private void irConfiguracion() {
        navegarA("/com/example/panaderia_el_panshito/vista/configuracion.fxml", "Configuración");
    }

    private void navegarA(String rutaFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent root = loader.load();

            Stage stage = (Stage) lblNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Panadería El Panshito - " + titulo);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo abrir la pantalla: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cerrarSesion() {
        Sesion.cerrar();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/example/panaderia_el_panshito/vista/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) lblNombre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Panadería El Panshito - Login");
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo cerrar sesión: " + e.getMessage());
            alert.showAndWait();
        }
    }
}