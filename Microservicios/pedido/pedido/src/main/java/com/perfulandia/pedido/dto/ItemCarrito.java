package com.perfulandia.pedido.dto;

import lombok.Data;
@Data
public class ItemCarrito {
    private Long productoId;
    private String nombre;
    private int cantidad;
    private double precio;
}
