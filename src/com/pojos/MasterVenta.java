package com.pojos;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "VENTA_MASTER")
public class MasterVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private long idVenta;
    @Column(name = "FECHA_ALTA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;
    @OneToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;
    @OneToOne
    @JoinColumn(name = "ID_ALMACEN")
    private Almacen almacen;
    @Column(name = "ESTATUS")
    private String estatus = "A";
    @OneToMany(mappedBy="venta",fetch=FetchType.EAGER)//,cascade = CascadeType.ALL)
    private List<DetalleVenta> listaDetalle;
    @Transient
    private float totalGeneral;
    @Transient
    private float subtotalTotal;
    @Transient
    private float ivaTotal;

    public MasterVenta() {
        almacen = new Almacen();
        cliente = new Cliente();
        listaDetalle = new ArrayList<>();
    }

    public MasterVenta(int idVenta) {
        this();
        this.idVenta = idVenta;
    }

    public void calculaTotales() {
        setSubtotalTotal(0);
        setTotalGeneral(0);
        setIvaTotal(0);
        for (DetalleVenta d : getListaDetalle()) {
            d.setSubtotal(d.getCantidad()*d.getPrecio());
            setSubtotalTotal(d.getSubtotal() + getSubtotalTotal());
            setIvaTotal((getSubtotalTotal() * d.getIva()) + getIvaTotal());
        }

        setTotalGeneral(getIvaTotal() + getSubtotalTotal());
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public List<DetalleVenta> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetalleVenta> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public float getTotalGeneral() {
        return totalGeneral;
    }

    public void setTotalGeneral(float totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    public float getSubtotalTotal() {
        return subtotalTotal;
    }

    public void setSubtotalTotal(float subtotalTotal) {
        this.subtotalTotal = subtotalTotal;
    }

    public float getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(float ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
