package com.example.panaderia_el_panshito.dao;

import java.util.List;

public interface ICRUD<T> {
    void guardar(T objeto);
    void actualizar(T objeto);
    void eliminar(int id);
    List<T> listar();
}
