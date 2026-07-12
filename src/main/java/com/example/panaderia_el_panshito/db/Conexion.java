package com.example.panaderia_el_panshito.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private Connection conn;

    // Ajusta estos datos a tu MySQL local
    private static final String URL = "jdbc:mysql://localhost:3306/panaderia_db";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "123456"; // tu password de MySQL

    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConn() {
        return conn;
    }
}