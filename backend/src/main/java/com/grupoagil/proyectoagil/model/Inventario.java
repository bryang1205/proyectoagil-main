package com.grupoagil.proyectoagil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    private Long idProducto;  // Relación con Producto

    @Column(name = "cant_dispo", nullable = false)
    private Integer cantDispo;  // Cantidad disponible

    @OneToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    private Producto producto;

    // Constructor vacío
    public Inventario() {}

    // Constructor con parámetros
    public Inventario(Long idProducto, Integer cantDispo) {
        this.idProducto = idProducto;
        this.cantDispo = cantDispo;
    }

    // Getters y Setters
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantDispo() {
        return cantDispo;
    }

    public void setCantDispo(Integer cantDispo) {
        this.cantDispo = cantDispo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
