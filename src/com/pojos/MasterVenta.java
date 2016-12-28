package com.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Usuario on 24/11/2016.
 */
public class MasterVenta {
    private int idVenta;
    private Date fechaAlta;
    private Cliente cliente;
    private Almacen almacen;
    private List<DetalleVenta> listaDetalle;

    public MasterVenta() {
        almacen = new Almacen();
        cliente = new Cliente();
        listaDetalle = new ArrayList<>();
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

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
}
