package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.dao.UsuarioDAO;
import com.example.panaderia_el_panshito.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText().trim();

        if (correo.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Debes ingresar correo y contraseña.");
            return;
        }

        Usuario usuario = usuarioDAO.validarLogin(correo, password);

        if (usuario == null) {
            mostrarAlerta("Error de acceso", "Correo o contraseña incorrectos.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/com/example/panaderia_el_panshito/vista/dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setUsuario(usuario);

            Stage stage = (Stage) txtCorreo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Panadería El Panshito - Dashboard");
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();

        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar el dashboard: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}