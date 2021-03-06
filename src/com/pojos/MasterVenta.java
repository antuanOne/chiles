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
    @Column(name = "CONCECUTIVO")
    private long concecutivo;
    @Column(name = "FECHA_ALTA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;
    @Column(name = "FECHA_ALTA_SYS")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAltaSys;
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
    @Column(name = "FAC_CLIENTE")
    private String facCliente;
    @Column(name = "TIPO_PAGO")
    private String tipoPago;
    @Transient
    private float totalGeneral;
    @Transient
    private float subtotalTotal;

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
        for (DetalleVenta d : getListaDetalle()) {
            d.setSubtotal(d.getCantidad()*d.getPrecio());
            setSubtotalTotal(d.getSubtotal() + getSubtotalTotal());
        }

        setTotalGeneral( getSubtotalTotal());
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public long getConcecutivo() {
        return concecutivo;
    }

    public void setConcecutivo(long concecutivo) {
        this.concecutivo = concecutivo;
    }

    public Date getFechaAltaSys() {
        return fechaAltaSys;
    }

    public void setFechaAltaSys(Date fechaAltaSys) {
        this.fechaAltaSys = fechaAltaSys;
    }

    public String getFacCliente() {
        return facCliente;
    }

    public void setFacCliente(String facCliente) {
        this.facCliente = facCliente;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
}
