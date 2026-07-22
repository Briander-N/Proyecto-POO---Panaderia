package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.model.Usuario;
import com.example.panaderia_el_panshito.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML private Label lblNombre;
    @FXML private Label lblRol;
    @FXML private StackPane panelContenido;

    @FXML private Button btnInicio;
    @FXML private Button btnProductos;
    @FXML private Button btnUsuarios;
    @FXML private Button btnReportes;
    @FXML private Button btnConfiguracion;
    @FXML private Button btnVenta;

    private Usuario usuario;

    // Lo llama el LoginController al entrar
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        lblNombre.setText("Usuario: " + usuario.getNombre());
        lblRol.setText("Rol: " + usuario.getRol());
        adaptarMenuPorRol();
        irInicio();
    }

    private void adaptarMenuPorRol() {
        String rol = usuario.getRol();

        ocultar(btnProductos);
        ocultar(btnUsuarios);
        ocultar(btnReportes);
        ocultar(btnConfiguracion);
        ocultar(btnVenta);

        switch (rol) {
            case "ADMIN" -> {
                mostrar(btnProductos);
                mostrar(btnUsuarios);
                mostrar(btnReportes);
                mostrar(btnConfiguracion);
            }
            case "CAJERO" -> mostrar(btnVenta);
            case "REPORTES" -> mostrar(btnReportes);
        }
    }

    private void mostrar(Button boton) {
        boton.setVisible(true);
        boton.setManaged(true);
    }

    private void ocultar(Button boton) {
        boton.setVisible(false);
        boton.setManaged(false);
    }

    @FXML
    private void irInicio() {
        panelContenido.getChildren().clear();
        Label bienvenida = new Label("Bienvenido/a, " + usuario.getNombre() + ".");
        bienvenida.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        panelContenido.getChildren().add(bienvenida);
    }

    @FXML
    private void irProductos() {
        cargarContenido("/com/example/panaderia_el_panshito/vista/producto.fxml");
    }

    @FXML
    private void irUsuarios() {
        cargarContenido("/com/example/panaderia_el_panshito/vista/usuario.fxml");
    }

    @FXML
    private void irReportes() {
        cargarContenido("/com/example/panaderia_el_panshito/vista/reportes.fxml");
    }

    @FXML
    private void irConfiguracion() {
        cargarContenido("/com/example/panaderia_el_panshito/vista/configuracion.fxml");
    }

    @FXML
    private void irVenta() {
        cargarContenido("/com/example/panaderia_el_panshito/vista/venta.fxml");
    }

    // Carga la pantalla pedida DENTRO del panel de contenido, sin tocar el sidebar
    private void cargarContenido(String rutaFxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent vista = loader.load();
            panelContenido.getChildren().clear();
            panelContenido.getChildren().add(vista);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No se pudo cargar la pantalla: " + e.getMessage());
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
