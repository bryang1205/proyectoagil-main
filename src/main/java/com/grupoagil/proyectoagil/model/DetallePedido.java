package com.grupoagil.proyectoagil.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_pedido")
@IdClass(DetallePedido.DetallePedidoPK.class)
public class DetallePedido{
    @Id
    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Id
    @Column(name = "id_producto", nullable = false)
    private Long idProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", insertable = false, updatable= false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", insertable = false, updatable= false)
    private Producto producto;

    @Column(name = "cant", nullable = false)
    private Integer cant;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;


    @Column(name = "nota", length =50)
    private String nota;

    public DetallePedido() {}

    public DetallePedido(Long idPedido, Long idProducto, Integer cant, Double precioUnitario, String nota) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cant = cant;
        this.precioUnitario = precioUnitario;
        this.nota = nota;
    }

    public static class DetallePedidoPK implements Serializable {
        private Long idPedido;
        private Long idProducto;

        public DetallePedidoPK() {}
        public DetallePedidoPK(Long idPedido, Long idProducto) {
            this.idPedido = idPedido;
            this.idProducto = idProducto;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof DetallePedidoPK that)) return false;
            return Objects.equals(idPedido, that.idPedido) &&
                   Objects.equals(idProducto, that.idProducto);
        }

        @Override
        public int hashCode() {
            return Objects.hash(idPedido, idProducto);
        }

        public Long getIdPedido() {return idPedido;}
        public void setIdPedido(Long idPedido) {this.idPedido = idPedido;}
        public Long getIdProducto() {return idProducto;}
        public void setIdProducto(Long idProducto) {this.idProducto = idProducto;}
    }

    public Long getIdPedido() {return idPedido;}
    public void setIdPedido(Long idPedido) {this.idPedido = idPedido;}

    public Long getIdProducto() {return idProducto;}
    public void setIdProducto(Long idProducto) {this.idProducto = idProducto;}

    public Pedido getPedido() {return pedido;}
    public void setPedido(Pedido pedido) {this.pedido = pedido;}

    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {this.producto = producto;}

    public Integer getCant() {return cant;}
    public void setCant(Integer cant) {this.cant = cant;}

    public Double getPrecioUnitario() {return precioUnitario;}
    public void setPrecioUnitario(Double precioUnitario) {this.precioUnitario = precioUnitario;}

    public String getNota() {return nota;}
    public void setNota(String nota) {this.nota = nota;}
}