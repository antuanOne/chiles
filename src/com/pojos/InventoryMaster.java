package com.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "INVENTARIO_MASTER")
public class InventoryMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INVENTARIO")
    private long idInventory;
    @OneToOne
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
    @OneToOne
    @JoinColumn(name = "ID_ALMACEN")
    private Almacen almacen;
    @Column(name = "CANTIDAD")
    private int cantidad;

    public InventoryMaster(){
        almacen = new Almacen();
        producto = new Producto();
    }

    /**
     * @return the idInventory
     */
    public long getIdInventory() {
        return idInventory;
    }

    /**
     * @param idInventory the idInventory to set
     */
    public void setIdInventory(long idInventory) {
        this.idInventory = idInventory;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the almacen
     */
    public Almacen getAlmacen() {
        return almacen;
    }

    /**
     * @param almacen the almacen to set
     */
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }


}
