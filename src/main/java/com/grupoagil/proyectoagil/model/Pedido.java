package com.grupoagil.proyectoagil.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Column(name = "tipo", nullable = false, length = 12)
    private String tipo;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private Usuario usuario;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mesa", referencedColumnName ="id_mesa", nullable = false)
    private Mesa mesa;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DetallePedido> detalles = new LinkedHashSet<>();


    public Pedido() {}

    public Pedido(Long idPedido, String tipo, LocalDateTime fecha, String estado, Usuario usuario, Mesa mesa) {
        this.idPedido = idPedido;
        this.tipo = tipo;
        this.fecha = fecha;
        this.estado = estado;
        this.usuario = usuario;
        this.mesa = mesa;
    }


    public Long getIdPedido() {return idPedido;}
    public void setIdPedido(Long idPedido) {this.idPedido = idPedido;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}

    public LocalDateTime getFecha() {return fecha;}
    public void setFecha(LocalDateTime fecha) {this.fecha = fecha;}

    public String getEstado() {return estado;}
    public void setEstado(String estado) {this.estado = estado;}

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public Mesa getMesa() {return mesa;}
    public void setMesa(Mesa mesa) {this.mesa = mesa;}

    public Set<DetallePedido> getDetalles() {return detalles;}
    public void setDetalles(Set<DetallePedido> detalles) {this.detalles = detalles;}
}