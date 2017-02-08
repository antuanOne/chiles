/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity(name = "COMPRA_MASTER")
public class MasterCompra implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_COMPRA")
    private long idCompra;
    @Column(name = "ORDEN_COMPRA")
    private long ordenDeCompra;
    @Column(name = "REF_PROVEEDOR")
    private long refProveedor;
    @Column(name = "FECHA_ALTA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;
    @Column(name = "FECHA_ORDEN")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaOrden;
    @Column(name = "FECHA_RECEPCION")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRecepcion;
    @Column(name = "ESTATUS")
    private String estatus;
    @OneToOne
    @JoinColumn(name = "ID_ALMACEN")
    private Almacen almacen;
    @OneToOne
    @JoinColumn(name = "ID_PROVEEDOR")
    private Proveedor proveedor;
    @OneToMany(mappedBy="compra",fetch=FetchType.EAGER)//,cascade = CascadeType.ALL)
    private List<DetalleCompra> listDetalle;
    @OneToMany(mappedBy="compra",fetch=FetchType.EAGER)//,cascade = CascadeType.ALL)
    private List<ConceptosExtra> listaExtra;
    @Transient
    private float totalGeneral;
    @Transient
    private float subtotalTotal;
    @Transient
    private float ivaTotal;

    public MasterCompra() {
        this.idCompra = 0;
        estatus="A";
        almacen = new Almacen();
        listDetalle = new ArrayList<>();
        listaExtra = new ArrayList<>();
        proveedor = new Proveedor();
    }

    public MasterCompra(long idCompra) {
        this();
        this.idCompra = idCompra;
    }

    public void calculaTotales() {
        setSubtotalTotal(0);
        setTotalGeneral(0);
        setIvaTotal(0);
        for (DetalleCompra d : getListDetalle()) {
            setSubtotalTotal(d.getSubtotal() + getSubtotalTotal());
            setIvaTotal((getSubtotalTotal() * d.getIva()) + getIvaTotal());
        }

        for (ConceptosExtra extr : listaExtra) {
            subtotalTotal = subtotalTotal + extr.getPrecio();
        }

        setTotalGeneral(getIvaTotal() + getSubtotalTotal());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MasterCompra) {
            MasterCompra p = (MasterCompra) obj;
            if (p.getIdCompra() == this.getIdCompra()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = (int) (getIdCompra() * 17);
        return hash;
    }

    /**
     * @return the estatus
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * @param estatus the estatus to set
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Almacen getAlmacen() {
        return almacen;
    }

    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    public List<DetalleCompra> getListDetalle() {
        return listDetalle;
    }

    public void setListDetalle(List<DetalleCompra> listDetalle) {
        this.listDetalle = listDetalle;
    }

    public float getTotalGeneral() {
        return totalGeneral;
    }

    public float getSubtotalTotal() {
        return subtotalTotal;
    }

    public float getIvaTotal() {
        return ivaTotal;
    }

    public long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(long idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the ordenDeCompra
     */
    public long getOrdenDeCompra() {
        return ordenDeCompra;
    }

    /**
     * @param ordenDeCompra the ordenDeCompra to set
     */
    public void setOrdenDeCompra(long ordenDeCompra) {
        this.ordenDeCompra = ordenDeCompra;
    }

    /**
     * @return the refProveedor
     */
    public long getRefProveedor() {
        return refProveedor;
    }

    /**
     * @param refProveedor the refProveedor to set
     */
    public void setRefProveedor(long refProveedor) {
        this.refProveedor = refProveedor;
    }

    /**
     * @return the fechaOrde
     */
    public Date getFechaOrden() {
        return fechaOrden;
    }

    /**
     * @param fechaOrden the fechaOrde to set
     */
    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    /**
     * @return the fechaRecepcion
     */
    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    /**
     * @param fechaRecepcion the fechaRecepcion to set
     */
    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    /**
     * @param totalGeneral the totalGeneral to set
     */
    public void setTotalGeneral(float totalGeneral) {
        this.totalGeneral = totalGeneral;
    }

    /**
     * @param subtotalTotal the subtotalTotal to set
     */
    public void setSubtotalTotal(float subtotalTotal) {
        this.subtotalTotal = subtotalTotal;
    }

    /**
     * @param ivaTotal the ivaTotal to set
     */
    public void setIvaTotal(float ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    /**
     * @return the listaExtra
     */
    public List<ConceptosExtra> getListaExtra() {
        return listaExtra;
    }

    /**
     * @param listaExtra the listaExtra to set
     */
    public void setListaExtra(List<ConceptosExtra> listaExtra) {
        this.listaExtra = listaExtra;
    }

}
