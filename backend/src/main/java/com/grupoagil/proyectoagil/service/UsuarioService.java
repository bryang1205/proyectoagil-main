package com.grupoagil.proyectoagil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupoagil.proyectoagil.model.Usuario;
import com.grupoagil.proyectoagil.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para validar inicio de sesión
    public Optional<Usuario> iniciarSesion(String user, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByUserAndPassword(user, password);

        if (usuario.isPresent()) {
            return usuario;  // Devuelve el usuario con su rol
        }
        return Optional.empty();
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Crear un nuevo usuario
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}