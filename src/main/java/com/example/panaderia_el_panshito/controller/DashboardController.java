package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardController {

    @FXML private Label lblNombre;
    @FXML private Label lblRol;

    @FXML private VBox panelAdmin;
    @FXML private VBox panelCajero;
    @FXML private VBox panelReportes;

    private Usuario usuario;

    // Este método lo llama el LoginController al entrar
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
    private void cerrarSesion() {
        // TODO: siguiente entrega -> volver a cargar login.fxml y cerrar sesión
    }
}