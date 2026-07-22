package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.dao.UsuarioDAO;
import com.example.panaderia_el_panshito.model.Usuario;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class UsuarioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<String> comboRol;

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Integer> colId;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colCorreo;
    @FXML private TableColumn<Usuario, String> colRol;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ObservableList<Usuario> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        comboRol.setItems(FXCollections.observableArrayList("ADMIN", "CAJERO", "REPORTES"));

        colId.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colCorreo.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCorreo()));
        colRol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRol()));

        cargarTabla();
    }

    private void cargarTabla() {
        lista.setAll(usuarioDAO.listar());
        tablaUsuarios.setItems(lista);
    }

    @FXML
    private void guardar() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String password = txtPassword.getText().trim();
        String rol = comboRol.getValue();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty() || rol == null) {
            alerta(Alert.AlertType.WARNING, "Campos vacíos", "Todos los campos son obligatorios, incluido el rol.");
            return;
        }

        if (password.length() < 6) {
            alerta(Alert.AlertType.WARNING, "Contraseña débil", "La contraseña debe tener al menos 6 caracteres.");
            return;
        }

        boolean correoRepetido = usuarioDAO.listar().stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));
        if (correoRepetido) {
            alerta(Alert.AlertType.WARNING, "Duplicado", "Ya existe un usuario con ese correo.");
            return;
        }

        usuarioDAO.guardar(new Usuario(0, nombre, correo, password, rol));
        alerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario creado correctamente.");

        limpiar();
        cargarTabla();
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtCorreo.clear();
        txtPassword.clear();
        comboRol.setValue(null);
    }

    private void alerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
