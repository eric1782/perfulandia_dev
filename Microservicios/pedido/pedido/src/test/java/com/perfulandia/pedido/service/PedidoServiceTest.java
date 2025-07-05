package com.perfulandia.pedido.service;

import com.perfulandia.pedido.dto.ItemCarrito;
import com.perfulandia.pedido.model.EstadoPedido;
import com.perfulandia.pedido.model.Pedido;
import com.perfulandia.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private RestTemplate restTemplate; // Mockeamos RestTemplate para no hacer llamadas reales

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private Long usuarioId;

    @BeforeEach
    void setUp() {
        usuarioId = 1L;
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setUsuarioId(usuarioId);
        pedido.setDescripcion("Pedido de prueba");
        pedido.setEstado(EstadoPedido.GENERADO);
    }

    // Test 1
    @Test
    @DisplayName("Test 1: Crear un pedido manualmente")
    void cuandoCrearPedido_deberiaGuardarYRetornarPedido() {
        // Given
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // When
        Pedido pedidoCreado = pedidoService.crearPedido(pedido);

        // Then
        assertNotNull(pedidoCreado);
        assertEquals(1L, pedidoCreado.getId());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    // Test 2
    @Test
    @DisplayName("Test 2: Cambiar estado de un pedido")
    void cuandoCambiarEstado_deberiaActualizarYGuardar() {
        // Given
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        // When
        Pedido pedidoActualizado = pedidoService.cambiarEstado(1L, EstadoPedido.ENVIADO);

        // Then
        assertNotNull(pedidoActualizado);
        assertEquals(EstadoPedido.ENVIADO, pedidoActualizado.getEstado());
        verify(pedidoRepository, times(1)).findById(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    // Test 3
    @Test
    @DisplayName("Test 3: Confirmar pedido con éxito")
    void cuandoConfirmarPedido_deberiaOrquestarCorrectamente() {
        // Given
        // 1. Simular la respuesta del servicio de Carrito
        ItemCarrito item1 = new ItemCarrito();
        item1.setProductoId(101L);
        item1.setNombre("Perfume A");
        item1.setCantidad(1);

        ItemCarrito item2 = new ItemCarrito();
        item2.setProductoId(102L);
        item2.setNombre("Crema B");
        item2.setCantidad(2);

        ItemCarrito[] items = {item1, item2};
        String urlCarrito = "http://localhost:8081/api/carritos/usuario/" + usuarioId;
        when(restTemplate.getForObject(urlCarrito, ItemCarrito[].class)).thenReturn(items);

        // 2. Simular que el guardado del pedido funciona
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // 3. Simular las llamadas a los otros servicios (no devuelven nada)
        String urlProducto1 = "http://localhost:8084/api/productos/101/stock?cantidad=1";
        String urlProducto2 = "http://localhost:8084/api/productos/102/stock?cantidad=2";
        String urlVaciarCarrito = "http://localhost:8081/api/carritos/usuario/" + usuarioId;

        doNothing().when(restTemplate).put(eq(urlProducto1), any());
        doNothing().when(restTemplate).put(eq(urlProducto2), any());
        doNothing().when(restTemplate).delete(eq(urlVaciarCarrito));

        // When
        Pedido pedidoConfirmado = pedidoService.confirmarPedido(usuarioId);

        // Then
        assertNotNull(pedidoConfirmado);
        assertEquals(usuarioId, pedidoConfirmado.getUsuarioId());
        assertTrue(pedidoConfirmado.getDescripcion().contains("Perfume A x1"));
        assertTrue(pedidoConfirmado.getDescripcion().contains("Crema B x2"));

        // Verify que todos los pasos de la orquestación fueron llamados
        verify(restTemplate, times(1)).getForObject(urlCarrito, ItemCarrito[].class);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
        verify(restTemplate, times(1)).put(eq(urlProducto1), any());
        verify(restTemplate, times(1)).put(eq(urlProducto2), any());
        verify(restTemplate, times(1)).delete(urlVaciarCarrito);
    }
}
