package com.example.panaderia_el_panshito.dao;

import com.example.panaderia_el_panshito.db.Conexion;
import com.example.panaderia_el_panshito.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements ICRUD<Usuario> {

    @Override
    public void guardar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, correo, password, rol) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Conexion.getInstancia().getConn().prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, password=?, rol=? WHERE id=?";
        try (PreparedStatement ps = Conexion.getInstancia().getConn().prepareStatement(sql)) {
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setString(3, usuario.getPassword());
            ps.setString(4, usuario.getRol());
            ps.setInt(5, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";
        try (PreparedStatement ps = Conexion.getInstancia().getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement st = Conexion.getInstancia().getConn().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("rol")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    // Método especial para el login: valida credenciales directamente contra la BD
    public Usuario validarLogin(String correo, String password) {
        String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
        try (PreparedStatement ps = Conexion.getInstancia().getConn().prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("password"),
                        rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al validar login: " + e.getMessage());
        }
        return null; // null = credenciales incorrectas
    }
}