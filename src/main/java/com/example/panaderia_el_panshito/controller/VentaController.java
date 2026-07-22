package com.example.panaderia_el_panshito.controller;

import com.example.panaderia_el_panshito.dao.ProductoDAO;
import com.example.panaderia_el_panshito.dao.VentaDAO;
import com.example.panaderia_el_panshito.model.Producto;
import com.example.panaderia_el_panshito.model.Venta;
import com.example.panaderia_el_panshito.util.Sesion;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class VentaController {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, Integer> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;

    @FXML private TextField txtCantidad;
    @FXML private Label lblProductoSeleccionado;

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
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
                lblProductoSeleccionado.setText("Seleccionado: " + seleccionado.getNombre()
                        + " (Stock disponible: " + seleccionado.getStock() + ")");
            }
        });
    }

    private void cargarTabla() {
        lista.setAll(productoDAO.listar());
        tablaProductos.setItems(lista);
    }

    @FXML
    private void registrarVenta() {
        if (productoSeleccionado == null) {
            alerta(Alert.AlertType.WARNING, "Sin selección", "Selecciona un producto de la tabla para vender.");
            return;
        }

        String textoCantidad = txtCantidad.getText().trim();
        if (textoCantidad.isEmpty()) {
            alerta(Alert.AlertType.WARNING, "Campo vacío", "Ingresa la cantidad a vender.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(textoCantidad);
        } catch (NumberFormatException e) {
            alerta(Alert.AlertType.ERROR, "Tipo de dato inválido", "La cantidad debe ser un número entero.");
            return;
        }

        if (cantidad <= 0) {
            alerta(Alert.AlertType.WARNING, "Valor inválido", "La cantidad debe ser mayor a cero.");
            return;
        }

        if (cantidad > productoSeleccionado.getStock()) {
            alerta(Alert.AlertType.WARNING, "Stock insuficiente",
                    "Solo hay " + productoSeleccionado.getStock() + " unidades disponibles.");
            return;
        }

        Venta venta = new Venta(0, Sesion.getUsuarioActual().getId(), productoSeleccionado.getId(), cantidad);
        ventaDAO.registrar(venta);
        productoDAO.reducirStock(productoSeleccionado.getId(), cantidad);

        alerta(Alert.AlertType.INFORMATION, "Venta registrada",
                "Se vendieron " + cantidad + " unidades de " + productoSeleccionado.getNombre() + ".");

        limpiar();
        cargarTabla();
    }

    @FXML
    private void limpiar() {
        txtCantidad.clear();
        lblProductoSeleccionado.setText("Ningún producto seleccionado");
        productoSeleccionado = null;
        tablaProductos.getSelectionModel().clearSelection();
    }

    private void alerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
