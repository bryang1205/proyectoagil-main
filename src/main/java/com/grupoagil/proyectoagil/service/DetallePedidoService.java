package com.grupoagil.proyectoagil.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.grupoagil.proyectoagil.model.DetallePedido;
import com.grupoagil.proyectoagil.model.Pedido;
import com.grupoagil.proyectoagil.model.Producto;
import com.grupoagil.proyectoagil.repository.DetallePedidoRepository;
import com.grupoagil.proyectoagil.repository.PedidoRepository;
import com.grupoagil.proyectoagil.repository.ProductoRepository;

@Service
public class DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public DetallePedidoService(DetallePedidoRepository detallePedidoRepository,
                                PedidoRepository pedidoRepository,
                                ProductoRepository productoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }

    public List<DetallePedido> listarPorPedido(Long idPedido) {
        return detallePedidoRepository.findByPedido_IdPedido(idPedido);
    }

    @Transactional
    public DetallePedido registrarDetalle(Long idPedido, Long idProducto, Integer cant, Double precioUnitario, String nota) {
        if (cant == null || cant <= 0) throw new RuntimeException("La cantidad debe ser > 0");
        if (precioUnitario == null || precioUnitario < 0) throw new RuntimeException("Precio unitario inválido");

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + idPedido));
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + idProducto));

        DetallePedido d = new DetallePedido();
        d.setPedido(pedido);
        d.setProducto(producto);
        d.setCant(cant);
        d.setPrecioUnitario(precioUnitario);
        d.setNota(nota);

        // Si ya existiera el mismo producto en el pedido, podrías sumar cantidades.
        // Para MV P lo dejamos como “insertar/override” y JPA manejará la PK compuesta.

        return detallePedidoRepository.save(d);
    }
}
