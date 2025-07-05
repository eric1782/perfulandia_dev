package com.perfulandia.carrito.service;

import com.perfulandia.carrito.model.ItemCarrito;
import com.perfulandia.carrito.repository.ItemCarritoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarritoServiceTest {

    @Mock
    private ItemCarritoRepository itemCarritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private ItemCarrito item;
    private Long usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = 1L;

        item = new ItemCarrito();
        item.setId(101L);
        item.setUsuarioId(usuarioId);
        item.setProductoId(202L);
        item.setNombre("Perfume de Carrito");
        item.setCantidad(2);
    }

    // Test 1
    @Test
    @DisplayName("Test 1: Guardar un ítem en el carrito")
    void cuandoGuardarItem_deberiaRetornarItem() {
        // Given
        when(itemCarritoRepository.save(any(ItemCarrito.class))).thenReturn(item);

        // When
        ItemCarrito itemGuardado = carritoService.guardar(item);

        // Then
        assertNotNull(itemGuardado);
        assertEquals(101L, itemGuardado.getId());
        verify(itemCarritoRepository, times(1)).save(any(ItemCarrito.class));
    }

    // Test 2
    @Test
    @DisplayName("Test 2: Listar ítems por usuario")
    void cuandoListarPorUsuario_deberiaRetornarListaCorrecta() {
        // Given
        ItemCarrito item2 = new ItemCarrito();
        item2.setUsuarioId(usuarioId);
        when(itemCarritoRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(item, item2));

        // When
        List<ItemCarrito> listaItems = carritoService.listarPorUsuario(usuarioId);

        // Then
        assertNotNull(listaItems);
        assertEquals(2, listaItems.size());
        // Verificamos que todos los ítems de la lista pertenecen al usuario correcto
        assertTrue(listaItems.stream().allMatch(i -> i.getUsuarioId().equals(usuarioId)));
    }

    // Test 3
    @Test
    @DisplayName("Test 3: Vaciar el carrito de un usuario")
    void cuandoVaciarCarrito_deberiaLlamarDelete() {
        // Given
        // Simulamos que el repositorio encuentra estos ítems para el usuario
        when(itemCarritoRepository.findByUsuarioId(usuarioId)).thenReturn(List.of(item));

        // When
        carritoService.vaciar(usuarioId);

        // Then
        // Verificamos que se llamó al método para borrar los ítems encontrados
        verify(itemCarritoRepository, times(1)).deleteAll(anyList());
    }
}