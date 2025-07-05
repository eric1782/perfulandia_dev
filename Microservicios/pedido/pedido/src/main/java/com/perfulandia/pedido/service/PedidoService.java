package com.perfulandia.pedido.service;

import com.perfulandia.pedido.model.Pedido;
import com.perfulandia.pedido.model.EstadoPedido;
import com.perfulandia.pedido.dto.ItemCarrito;
import com.perfulandia.pedido.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PedidoService {
    private final PedidoRepository repository;
    private final RestTemplate restTemplate;

    public PedidoService(PedidoRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public Pedido confirmarPedido(Long usuarioId) {
            // 1. Obtener carrito
            String urlCarrito = "http://localhost:8081/api/carritos/usuario/" + usuarioId;
            ItemCarrito[] items = restTemplate.getForObject(urlCarrito, ItemCarrito[].class);

            if (items == null || items.length == 0) {
                throw new RuntimeException("El carrito está vacío");
            }

            // 2. Crear descripción
            String descripcion = Arrays.stream(items)
                    .map(item -> item.getNombre() + " x" + item.getCantidad())
                    .collect(Collectors.joining(", "));

            // 3. Crear pedido y guardar
            Pedido pedido = new Pedido();
            pedido.setUsuarioId(usuarioId);
            pedido.setDescripcion(descripcion);
            pedido.setEstado(EstadoPedido.GENERADO);

            Pedido guardado = repository.save(pedido);
            System.out.println(" Pedido guardado: ID=" + guardado.getId() + ", Desc=" + guardado.getDescripcion());

            // 4. Descontar stock
            for (ItemCarrito item : items) {
                String urlProducto = "http://localhost:8084/api/productos/" + item.getProductoId()
                        + "/stock?cantidad=" + item.getCantidad();

                try {
                    restTemplate.put(urlProducto, null);
                    System.out.println(" Stock descontado producto ID " + item.getProductoId());
                } catch (Exception e) {
                    System.out.println(" ERROR descontando stock producto ID " + item.getProductoId());
                    e.printStackTrace();
                    throw new RuntimeException("Error al descontar stock de producto ID: " + item.getProductoId());
                }
            }

            // 5. Vaciar carrito
            try {
                restTemplate.delete("http://localhost:8081/api/carritos/usuario/" + usuarioId);
                System.out.println(" Carrito vaciado para usuario " + usuarioId);
            } catch (Exception e) {
                System.out.println(" ERROR al vaciar carrito: " + e.getMessage());
            }

            return guardado;
        }

        public Pedido crearPedido(Pedido pedido) {
        return repository.save(pedido);
    }
    public List<Pedido> listarPedidos() {
        return repository.findAll();
    }
    public Pedido cambiarEstado(Long id, EstadoPedido estado) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedido.setEstado(estado);
        return repository.save(pedido);
    }

}
