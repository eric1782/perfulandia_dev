package com.perfulandia.producto.service;

import com.perfulandia.producto.model.Producto;
import com.perfulandia.producto.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Perfume Test");
        producto.setPrecio(50.0);
        producto.setStock(100);
    }

    // Test 1
    @Test
    @DisplayName("Test 1: Guardar un producto")
    void cuandoGuardarProducto_deberiaRetornarProducto() {
        // Given
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        // When
        Producto productoGuardado = productoService.guardar(producto);

        // Then
        assertNotNull(productoGuardado);
        assertEquals("Perfume Test", productoGuardado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    // Test 2
    @Test
    @DisplayName("Test 2: Listar todos los productos")
    void cuandoListarProductos_deberiaRetornarLista() {
        // Given
        Producto producto2 = new Producto();
        producto2.setId(2L);
        when(productoRepository.findAll()).thenReturn(List.of(producto, producto2));

        // When
        List<Producto> listaProductos = productoService.listar();

        // Then
        assertNotNull(listaProductos);
        assertEquals(2, listaProductos.size());
    }

    // Test 3
    @Test
    @DisplayName("Test 3: Actualizar stock de un producto")
    void cuandoActualizarStock_deberiaDescontarCantidad() {
        // Given
        int cantidadADescontar = 10;
        int stockEsperado = 90;
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        // Mockeamos la acción de guardar para que devuelva el producto con el stock ya modificado
        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            p.setStock(p.getStock()); // En una prueba real, el servicio hace esto.
            return p;
        });

        // When
        Producto productoActualizado = productoService.actualizarStock(1L, cantidadADescontar);

        // Then
        assertNotNull(productoActualizado);
        assertEquals(stockEsperado, productoActualizado.getStock());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(any(Producto.class));
    }
}