/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name = "COMPRA_DETALLE")
public class DetalleCompra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_COMPRA")
    private long idDetalle;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_COMPRA")
    private MasterCompra compra;
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
    @Column(name = "PRECIO")
    private float precio;
    @Column(name = "CANTIDAD")
    private int cantidad;
    @Transient
    private float subtotal;

    public DetalleCompra() {
        this.idDetalle = 0;
        producto = new Producto();
        compra = new MasterCompra();
    }

    public DetalleCompra(long idDetalle) {
        this.idDetalle = idDetalle;
        producto = new Producto();
        compra = new MasterCompra();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DetalleCompra) {
            DetalleCompra a = (DetalleCompra) obj;
            if (a.getIdDetalle() == this.getIdDetalle()
                    && a.getCompra().getIdCompra()== this.getCompra().getIdCompra()
                    && a.getProducto().getIdProducto() == this.getProducto().getIdProducto()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = (int) (getIdDetalle() * 17);
        hash = (int) (hash * getCompra().getIdCompra());
        hash = hash * getProducto().hashCode();
        return hash;
    }

    public long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        subtotal = cantidad * precio;
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public MasterCompra getCompra() {
        return compra;
    }

    public void setCompra(MasterCompra compra) {
        this.compra = compra;
    }
}
