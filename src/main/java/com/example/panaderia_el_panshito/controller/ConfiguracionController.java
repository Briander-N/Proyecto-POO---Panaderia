package com.example.panaderia_el_panshito.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

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

    private void alerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
