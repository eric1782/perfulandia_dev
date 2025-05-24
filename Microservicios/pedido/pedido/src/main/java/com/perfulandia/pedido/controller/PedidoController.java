package com.perfulandia.pedido.controller;

import com.perfulandia.pedido.model.EstadoPedido;
import com.perfulandia.pedido.model.Pedido;
import com.perfulandia.pedido.service.PedidoService;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/pedido")
@CrossOrigin
public class PedidoController {
    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public Pedido crear(@RequestBody Pedido pedido) {
        return service.crearPedido(pedido);
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listarPedidos();
    }

    @PutMapping("/{id}/estado")
    public Pedido cambiarEstado(@PathVariable Long id, @RequestParam EstadoPedido estado) {
        return service.cambiarEstado(id, estado);
    }
    @PutMapping("/confirmar/{usuarioId}")
    public Pedido confirmar(@PathVariable Long usuarioId) {
        return service.confirmarPedido(usuarioId);
    }


}
