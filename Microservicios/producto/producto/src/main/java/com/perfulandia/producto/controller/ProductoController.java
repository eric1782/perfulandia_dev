package com.perfulandia.producto.controller;

import com.perfulandia.producto.model.Producto;
import com.perfulandia.producto.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // 1. Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // 2. Obtener producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto producto = service.obtenerPorId(id);
        return (producto != null) ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }

    // 3. Crear nuevo producto
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto creado = service.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // 4. Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        Producto existente = service.obtenerPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        producto.setId(id); // asegurar ID
        return ResponseEntity.ok(service.guardar(producto));
    }

    // 5. Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Producto existente = service.obtenerPorId(id);
        if (existente == null) return ResponseEntity.notFound().build();

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Descontar stock
    @PutMapping("/{id}/stock")
    public ResponseEntity<Producto> descontarStock(@PathVariable Long id, @RequestParam int cantidad) {
        Producto actualizado = service.actualizarStock(id, cantidad);
        return ResponseEntity.ok(actualizado);
    }
}
