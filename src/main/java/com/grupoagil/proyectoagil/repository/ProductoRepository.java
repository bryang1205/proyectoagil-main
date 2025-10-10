package com.grupoagil.proyectoagil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoagil.proyectoagil.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}