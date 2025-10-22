package com.grupoagil.proyectoagil.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.grupoagil.proyectoagil.model.DetallePedido;
import com.grupoagil.proyectoagil.model.DetallePedido.DetallePedidoPK;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, DetallePedidoPK> {

    // Listar todos los productos de un pedido
    List<DetallePedido> findByPedido_IdPedido(Long idPedido);

    // Buscar un producto específico dentro de un pedido
    Optional<DetallePedido> findByPedido_IdPedidoAndProducto_IdProducto(Long idPedido, Long idProducto);

    // Eliminar todo el detalle de un pedido
    void deleteByPedido_IdPedido(Long idPedido);

    // Calcular el total del pedido
    @Query("SELECT SUM(d.cant * d.precioUnitario) FROM DetallePedido d WHERE d.pedido.idPedido = :idPedido")
    Double calcularTotalPorPedido(Long idPedido);
}
