package com.example.panaderia_el_panshito.dao;
import java.util.List;


public interface ICRUD<T> {

    void guardar(T obj);

    void actualizar(T obj);

    void eliminar(int id);

    List<T> listar();
}
