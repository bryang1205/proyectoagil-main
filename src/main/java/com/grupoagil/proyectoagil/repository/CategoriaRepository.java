package com.grupoagil.proyectoagil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupoagil.proyectoagil.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public Optional<Categoria> findByNombre(String nombre);
}
