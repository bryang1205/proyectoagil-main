package com.grupoagil.proyectoagil.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoagil.proyectoagil.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByMesa_IdMesaOrderByFechaDesc(Long idMesa);

    boolean existsByMesa_IdMesaAndEstadoIn(Long idMesa, List<String> estados);

    Optional<Pedido> findFirstByMesa_IdMesaAndEstadoInOrderByFechaDesc(Long idMesa, List<String> estados);
}
