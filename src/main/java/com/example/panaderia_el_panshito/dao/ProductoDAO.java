package com.example.panaderia_el_panshito.dao;

import com.example.panaderia_el_panshito.model.Producto;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements ICRUD<Producto> {

    @Override
    public void guardar(Producto obj) {
    }

    @Override
    public void actualizar(Producto obj) {
    }

    @Override
    public void eliminar(int id) {
    }

    @Override
    public List<Producto> listar() {
        return new ArrayList<>();
    }
}