package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.util.Sesion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfiguracionController {

    @FXML private TextField txtNombreEmpresa;
    @FXML private TextField txtDireccion;

    // Datos guardados en memoria durante la ejecución (parámetros básicos del sistema)
    public static String nombreEmpresa = "Panadería El Panshito";
    public static String direccion = "";

    @FXML
    public void initialize() {
        txtNombreEmpresa.setText(nombreEmpresa);
        txtDireccion.setText(direccion);
    }

    @FXML
    private void guardar() {
        if (txtNombreEmpresa.getText().trim().isEmpty()) {
            alerta("El nombre de la empresa no puede estar vacío.");
            return;
        }

        nombreEmpresa = txtNombreEmpresa.getText().trim();
        direccion = txtDireccion.getText().trim();

        Alert ok = new Alert(Alert.AlertType.INFORMATION);
        ok.setContentText("Configuración guardada correctamente.");
        ok.showAndWait();
    }

    @FXML
    private void volverDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/example/panaderia_el_panshito/vista/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setUsuario(Sesion.getUsuarioActual());

            Stage stage = (Stage) txtNombreEmpresa.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            alerta("No se pudo volver al dashboard: " + e.getMessage());
        }
    }

    private void alerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}