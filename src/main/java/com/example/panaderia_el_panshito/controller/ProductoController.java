package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.dao.ProductoDAO;
import com.example.panaderia_el_panshito.model.Producto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class ProductoController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ObservableList<Producto> lista = FXCollections.observableArrayList();

    private Producto productoSeleccionado = null;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getId()).asObject());
        colNombre.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNombre()));
        colPrecio.setCellValueFactory(d -> new SimpleDoubleProperty(d.getValue().getPrecio()).asObject());
        colStock.setCellValueFactory(d -> new SimpleIntegerProperty(d.getValue().getStock()).asObject());

        cargarTabla();

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, anterior, seleccionado) -> {
            if (seleccionado != null) {
                productoSeleccionado = seleccionado;
                txtNombre.setText(seleccionado.getNombre());
                txtPrecio.setText(String.valueOf(seleccionado.getPrecio()));
                txtStock.setText(String.valueOf(seleccionado.getStock()));
            }
        });
    }

    private void cargarTabla() {
        lista.setAll(productoDAO.listar());
        tablaProductos.setItems(lista);
    }

    @FXML
    private void guardar() {
        if (!validarCampos()) return;

        String nombre = txtNombre.getText().trim();
        double precio = Double.parseDouble(txtPrecio.getText().trim());
        int stock = Integer.parseInt(txtStock.getText().trim());

        int idExcluir = (productoSeleccionado != null) ? productoSeleccionado.getId() : -1;
        if (productoDAO.existeNombre(nombre, idExcluir)) {
            alerta(Alert.AlertType.WARNING, "Duplicado", "Ya existe un producto con ese nombre.");
            return;
        }

        if (productoSeleccionado == null) {
            productoDAO.guardar(new Producto(0, nombre, precio, stock));
            alerta(Alert.AlertType.INFORMATION, "Éxito", "Producto guardado correctamente.");
        } else {
            productoSeleccionado.setNombre(nombre);
            productoSeleccionado.setPrecio(precio);
            productoSeleccionado.setStock(stock);
            productoDAO.actualizar(productoSeleccionado);
            alerta(Alert.AlertType.INFORMATION, "Éxito", "Producto actualizado correctamente.");
        }

        limpiar();
        cargarTabla();
    }

    @FXML
    private void eliminar() {
        if (productoSeleccionado == null) {
            alerta(Alert.AlertType.WARNING, "Sin selección", "Selecciona un producto de la tabla para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar \"" + productoSeleccionado.getNombre() + "\"?");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            productoDAO.eliminar(productoSeleccionado.getId());
            alerta(Alert.AlertType.INFORMATION, "Eliminado", "Producto eliminado correctamente.");
            limpiar();
            cargarTabla();
        }
    }

    @FXML
    private void limpiar() {
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        productoSeleccionado = null;
        tablaProductos.getSelectionModel().clearSelection();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty()
                || txtPrecio.getText().trim().isEmpty()
                || txtStock.getText().trim().isEmpty()) {
            alerta(Alert.AlertType.WARNING, "Campos vacíos", "Todos los campos son obligatorios.");
            return false;
        }

        double precio;
        int stock;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Tipo de dato inválido", "El precio debe ser un número (ej: 1.50).");
            return false;
        }
        try {
            stock = Integer.parseInt(txtStock.getText().trim());
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Tipo de dato inválido", "El stock debe ser un número entero.");
            return false;
        }

        if (precio <= 0) {
            alerta(Alert.AlertType.WARNING, "Valor inválido", "El precio debe ser mayor a cero.");
            return false;
        }
        if (stock < 0) {
            alerta(Alert.AlertType.WARNING, "Valor inválido", "El stock no puede ser negativo.");
            return false;
        }
        return true;
    }

    private void alerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
