package com.example.panaderia_el_panshito.model;

public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto() {
    }

    public Producto(int id, String nombre,
                    double precio, int stock) {

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // getters y setters
}