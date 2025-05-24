package com.perfulandia.producto.service;

import com.perfulandia.producto.model.Producto;
import com.perfulandia.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Producto actualizarStock(Long id, int cantidad) {
        Producto producto = repository.findById(id).orElseThrow();
        producto.setStock(producto.getStock() - cantidad);
        return repository.save(producto);
    }
    public Producto obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

}
