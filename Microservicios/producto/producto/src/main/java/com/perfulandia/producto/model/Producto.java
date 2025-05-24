package com.perfulandia.producto.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
}
