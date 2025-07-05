package com.perfulandia.producto.controller;

import com.perfulandia.producto.model.Producto;
import com.perfulandia.producto.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin
@Tag(name = "Gestión de Productos", description = "API para las operaciones de inventario de productos")
public class ProductoController {
    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    // 1. Obtener todos los productos
    @Operation(summary = "1. Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // 2. Obtener producto por ID
    @Operation(summary = "2. Obtener un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@Parameter(description = "ID del producto a buscar") @PathVariable Long id) {
        Producto producto = service.obtenerPorId(id);
        return (producto != null) ? ResponseEntity.ok(producto) : ResponseEntity.notFound().build();
    }

    // 3. Crear nuevo producto
    @Operation(summary = "3. Crear un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del producto inválidos")
    })
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto creado = service.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // 4. Actualizar producto
    @Operation(summary = "4. Actualizar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@Parameter(description = "ID del producto a actualizar") @PathVariable Long id, @RequestBody Producto producto) {
        if (service.obtenerPorId(id) == null) return ResponseEntity.notFound().build();
        producto.setId(id);
        Producto productoActualizado = service.guardar(producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // 5. Eliminar producto
    @Operation(summary = "5. Eliminar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del producto a eliminar") @PathVariable Long id) {
        if (service.obtenerPorId(id) == null) return ResponseEntity.notFound().build();
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Descontar stock
    @Operation(summary = "6. Actualizar el stock de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}/stock")
    public ResponseEntity<Producto> descontarStock(
            @Parameter(description = "ID del producto a modificar") @PathVariable Long id,
            @Parameter(description = "Cantidad a descontar del stock") @RequestParam int cantidad) {
        Producto productoActualizado = service.actualizarStock(id, cantidad);
        return ResponseEntity.ok(productoActualizado);
    }
}