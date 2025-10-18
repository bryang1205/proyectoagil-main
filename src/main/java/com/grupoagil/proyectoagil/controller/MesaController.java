package com.grupoagil.proyectoagil.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupoagil.proyectoagil.model.Mesa;
import com.grupoagil.proyectoagil.service.MesaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    // Obtener todas las mesas
    @GetMapping
    public List<Mesa> getAllMesas() {
        return mesaService.getAllMesas();
    }

    // Obtener una mesa por su ID
    @GetMapping("/{idMesa}")
    public ResponseEntity<Mesa> getMesaById(@PathVariable Long idMesa) {
        Optional<Mesa> mesaOpt = mesaService.getMesaById(idMesa);
        return mesaOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva mesa
    @PostMapping
    public ResponseEntity<Mesa> createMesa(@RequestBody Mesa mesa) {
        Mesa createdMesa = mesaService.createMesa(mesa.getIdMesa(), mesa.getEstado());
        return ResponseEntity.ok(createdMesa);
    }

    // Actualizar el estado de una mesa (sin búsqueda extra por ID)
    @PutMapping("/{idMesa}")
    public ResponseEntity<Mesa> updateMesa(@PathVariable Long idMesa, @RequestBody Mesa mesa) {
        Mesa updatedMesa = mesaService.updateMesa(idMesa, mesa.getEstado());
        return ResponseEntity.ok(updatedMesa);
    }
}
