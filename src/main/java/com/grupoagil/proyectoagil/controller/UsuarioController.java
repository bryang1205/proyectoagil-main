package com.grupoagil.proyectoagil.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoagil.proyectoagil.model.Usuario;
import com.grupoagil.proyectoagil.service.UsuarioService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Iniciar sesión
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> datosLogin) {
        String user = datosLogin.get("user");
        String password = datosLogin.get("password");

        // Llamamos al servicio para verificar las credenciales
        Optional<Usuario> usuario = usuarioService.iniciarSesion(user, password);

        Map<String, String> respuesta = new HashMap<>();

        if (usuario.isPresent()) {
            // Si el usuario existe y la contraseña es correcta
            Usuario usuarioObj = usuario.get();
            respuesta.put("rol", usuarioObj.getRol().getRol());
            respuesta.put("mensaje", "Inicio de sesión exitoso. Bienvenido " + usuarioObj.getNombre());
        } else {
            // Si las credenciales son incorrectas
            respuesta.put("mensaje", "Credenciales inválidas");
        }

        return ResponseEntity.ok(respuesta);  // Devolvemos la respuesta con el mensaje
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.createUsuario(usuario);
        return ResponseEntity.status(201).body(nuevoUsuario); // Retorna el usuario creado con código 201
    }

}
