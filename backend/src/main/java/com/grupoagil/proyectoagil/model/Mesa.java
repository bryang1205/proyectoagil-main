package com.grupoagil.proyectoagil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mesa")
public class Mesa {

    @Id
    @Column(name = "id_mesa", nullable = false)
    private Long idMesa;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    // Constructor vacío
    public Mesa() {}

    // Constructor con parámetros
    public Mesa(Long idMesa, String estado) {
        this.idMesa = idMesa;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
