package com.grupoagil.proyectoagil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoagil.proyectoagil.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Inventario findByIdProducto(Long idProducto);
}