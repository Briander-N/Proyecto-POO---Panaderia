package com.example.panaderia_el_panshito.model;

public class Usuario extends Persona {

    private String password;
    private String rol;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String correo,
                   String password, String rol) {

        super(id, nombre, correo);

        this.password = password;
        this.rol = rol;
    }

    @Override
    public String obtenerRol() {
        return rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}