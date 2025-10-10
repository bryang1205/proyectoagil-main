package com.grupoagil.proyectoagil.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupoagil.proyectoagil.model.Mesa;
import com.grupoagil.proyectoagil.repository.MesaRepository;

@Service
public class MesaService {

    @Autowired
    private MesaRepository mesaRepository;

    // Obtener todas las mesas
    public List<Mesa> getAllMesas() {
        return mesaRepository.findAll();
    }

    // Obtener una mesa por su ID
    public Optional<Mesa> getMesaById(Long idMesa) {
        return mesaRepository.findById(idMesa);
    }

    // Crear una nueva mesa
    public Mesa createMesa(Long idMesa, String estado) {
        Mesa mesa = new Mesa(idMesa, estado);
        return mesaRepository.save(mesa);
    }

    // Actualizar el estado de una mesa
    public Mesa updateMesa(Long idMesa, String estado) {
        // Obtener la mesa por ID
        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));

        // Actualizar el estado de la mesa
        mesa.setEstado(estado);

        // Guardar los cambios
        return mesaRepository.save(mesa);
    }
}
