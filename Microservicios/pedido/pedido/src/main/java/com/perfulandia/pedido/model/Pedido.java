package com.perfulandia.pedido.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId; // quién hizo el pedido

    private String descripcion; // resumen de productos, opcional

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.GENERADO;
}
