package com.perfulandia.carrito.service;

import com.perfulandia.carrito.model.ItemCarrito;
import com.perfulandia.carrito.repository.ItemCarritoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarritoService {

    private final ItemCarritoRepository repository;

    public CarritoService(ItemCarritoRepository repository) {
        this.repository = repository;
    }

    public List<ItemCarrito> listar() {
        return repository.findAll();
    }

    public ItemCarrito guardar(ItemCarrito item) {
        return repository.save(item);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public void vaciar() {
        repository.deleteAll();
    }
    public List<ItemCarrito> listarPorUsuario(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    // En CarritoService.java
    public void vaciar(Long usuarioId) {
        List<ItemCarrito> items = repository.findByUsuarioId(usuarioId);
        if (items != null && !items.isEmpty()) {
            repository.deleteAll(items);
        }
    }

}
