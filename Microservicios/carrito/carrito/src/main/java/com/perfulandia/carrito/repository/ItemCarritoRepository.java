package com.perfulandia.carrito.repository;

import com.perfulandia.carrito.model.ItemCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Long> {
    List<ItemCarrito> findByUsuarioId(Long usuarioId);
}

