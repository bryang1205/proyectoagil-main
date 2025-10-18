package com.grupoagil.proyectoagil.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupoagil.proyectoagil.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByUserAndPassword(String user, String password);
}