package com.perfulandia.pedido.controller;

import com.perfulandia.pedido.model.EstadoPedido;
import com.perfulandia.pedido.model.Pedido;
import com.perfulandia.pedido.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin
@Tag(name = "Gestión de Pedidos", description = "API para la creación y seguimiento de pedidos")
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @Operation(summary = "Crear un nuevo pedido manualmente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos del pedido inválidos")
    })
    @PostMapping
    public Pedido crear(@RequestBody Pedido pedido) {
        return service.crearPedido(pedido);
    }

    @Operation(summary = "Listar todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente")
    @GetMapping
    public List<Pedido> listar() {
        return service.listarPedidos();
    }

    @Operation(summary = "Cambiar el estado de un pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado del pedido actualizado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @PutMapping("/{id}/estado")
    public Pedido cambiarEstado(
            @Parameter(description = "ID del pedido a modificar") @PathVariable Long id,
            @Parameter(description = "Nuevo estado del pedido. Valores posibles: GENERADO, ENVIADO, ENTREGADO") @RequestParam EstadoPedido estado) {
        return service.cambiarEstado(id, estado);
    }

    @Operation(summary = "Confirmar un pedido a partir del carrito de un usuario",
            description = "Orquesta el proceso completo: lee el carrito, crea el pedido, descuenta el stock y vacía el carrito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido confirmado y creado exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno durante la orquestación (ej: el carrito está vacío, no se puede descontar stock)")
    })
    @PutMapping("/confirmar/{usuarioId}")
    public Pedido confirmar(
            @Parameter(description = "ID del usuario que confirma su pedido") @PathVariable Long usuarioId) {
        return service.confirmarPedido(usuarioId);
    }
}