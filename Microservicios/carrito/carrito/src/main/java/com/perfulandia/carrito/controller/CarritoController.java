package com.perfulandia.carrito.controller;

import com.perfulandia.carrito.model.ItemCarrito;
import com.perfulandia.carrito.service.CarritoService;
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
@RequestMapping("/api/carritos")
@CrossOrigin
@Tag(name = "Gestión de Carritos de Compra", description = "API para gestionar los ítems en el carrito de un usuario")
public class CarritoController {
    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    // 1. Agregar ítem al carrito
    @Operation(summary = "1. Agregar un ítem al carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ítem agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del ítem inválidos")
    })
    @PostMapping
    public ResponseEntity<ItemCarrito> agregar(@RequestBody ItemCarrito item) {
        ItemCarrito nuevo = service.guardar(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // 2. Listar ítems por usuario
    @Operation(summary = "2. Listar todos los ítems del carrito de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítems del carrito listados exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado o carrito vacío")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ItemCarrito>> listarPorUsuario(
            @Parameter(description = "ID del usuario cuyo carrito se quiere consultar") @PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    // 3. Eliminar ítem por ID
    @Operation(summary = "3. Eliminar un ítem específico del carrito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ítem eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado en el carrito")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del ítem del carrito a eliminar") @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // 4. Vaciar carrito de un usuario
    @Operation(summary = "4. Vaciar todos los ítems del carrito de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrito vaciado exitosamente")
    })
    @DeleteMapping("/usuario/{usuarioId}")
    public ResponseEntity<Void> vaciar(
            @Parameter(description = "ID del usuario cuyo carrito se vaciará") @PathVariable Long usuarioId) {
        service.vaciar(usuarioId);
        return ResponseEntity.noContent().build();
    }
}