package com.perfulandia.carrito.controller;

import com.perfulandia.carrito.model.ItemCarrito;
import com.perfulandia.carrito.service.CarritoService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/carritos")
@CrossOrigin
public class CarritoController {
    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    // 1. Agregar ítem al carrito
    @PostMapping
    public ResponseEntity<ItemCarrito> agregar(@RequestBody ItemCarrito item) {
        ItemCarrito nuevo = service.guardar(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // 2. Listar ítems por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemCarrito>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    // 3. Eliminar ítem por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 4. Vaciar carrito de un usuario
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> vaciar(@PathVariable Long usuarioId) {
        service.vaciar(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
