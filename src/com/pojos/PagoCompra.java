package com.pojos;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity(name = "PAGOS_COMPRAS")
public class PagoCompra implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PAGO")
    private long idPago;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_COMPRA")
    private MasterCompra compra;
    @Column(name = "MONTO")
    private float monto;
    @Column(name = "ESTATUS")
    private String estatus;
    @Column(name = "FECHA_ALTA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAlta;

    public long getIdPago() {
        return idPago;
    }

    public void setIdPago(long idPago) {
        this.idPago = idPago;
    }

    public MasterCompra getCompra() {
        return compra;
    }

    public void setCompra(MasterCompra compra) {
        this.compra = compra;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
