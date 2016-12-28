package com.pojos;

import javax.persistence.*;

@Entity(name = "VENTA_DETALLE")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA")
    private long idDetalle;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_VENTA")
    private MasterVenta venta;
    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;
    @Column(name = "PRECIO")
    private float precio;
    @Column(name = "CANTIDAD")
    private int cantidad;
    @Transient
    private float subtotal;
    @Transient
    private float iva;

    public DetalleVenta() {
        this.setIdDetalle(0);
        setProducto(new Producto());
        venta = new MasterVenta();
    }

    public DetalleVenta(long idDetalle) {
        this.setIdDetalle(idDetalle);
        setProducto(new Producto());
        venta = new MasterVenta();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DetalleCompra) {
            DetalleCompra a = (DetalleCompra) obj;
            if (a.getIdDetalle() == this.getIdDetalle()
                    && a.getCompra().getIdCompra()== this.getVenta().getIdVenta()
                    && a.getProducto().getIdProducto() == this.getProducto().getIdProducto()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = (int) (getIdDetalle() * 17);
        hash = (int) (hash * getVenta().getIdVenta());
        hash = hash * getProducto().hashCode();
        return hash;
    }

    public long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public MasterVenta getVenta() {
        return venta;
    }

    public void setVenta(MasterVenta venta) {
        this.venta = venta;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }
}
