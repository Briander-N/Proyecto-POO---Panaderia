package com.example.panaderia_el_panshito.dao;

import com.example.panaderia_el_panshito.db.Conexion;
import com.example.panaderia_el_panshito.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAO {

    public void registrar(Venta venta) {
        String sql = "INSERT INTO venta (id_usuario, id_producto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement ps = Conexion.getInstancia().getConn().prepareStatement(sql)) {
            ps.setInt(1, venta.getIdUsuario());
            ps.setInt(2, venta.getIdProducto());
            ps.setInt(3, venta.getCantidad());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar venta: " + e.getMessage());
        }
    }

    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT v.id, v.id_usuario, v.id_producto, v.cantidad, p.nombre AS nombre_producto " +
                "FROM venta v JOIN producto p ON v.id_producto = p.id";
        try (Statement st = Conexion.getInstancia().getConn().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Venta venta = new Venta(
                        rs.getInt("id"),
                        rs.getInt("id_usuario"),
                        rs.getInt("id_producto"),
                        rs.getInt("cantidad")
                );
                venta.setNombreProducto(rs.getString("nombre_producto"));
                lista.add(venta);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    public int totalVentas() {
        String sql = "SELECT COUNT(*) FROM venta";
        try (Statement st = Conexion.getInstancia().getConn().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error al contar ventas: " + e.getMessage());
        }
        return 0;
    }
}