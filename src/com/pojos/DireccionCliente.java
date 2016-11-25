package com.pojos;

import javax.persistence.*;

@Entity(name = "CLIENTE_DIRECCION")
public class DireccionCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIRECCION_CLIENTE")
    private int id;
    @Column(name = "CALLE")
    private String calle;
    @Column(name = "COLONIA")
    private String colonia;
    @Column(name = "CP")
    private String cp;
    @Column(name = "NO_EXTERIOR")
    private String noExterior;
    @Column(name = "NO_INTERIOR")
    private String noInterior;
    @Column(name = "REFERENCIA")
    private String referencia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getNoExterior() {
        return noExterior;
    }

    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    public String getNoInterior() {
        return noInterior;
    }

    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
}
