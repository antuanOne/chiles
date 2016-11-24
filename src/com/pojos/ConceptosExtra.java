/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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

@Entity(name = "COMPRA_EXTRA")
public class ConceptosExtra implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EXTRA_COMPRA")
    private long idExtra;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="ID_COMPRA")
    private MasterCompra compra;
    @Column(name = "CONCEPTO")
    private String concepto;
    @Column(name = "PRECIO")
    private float precio;

    /**
     * @return the idExtra
     */
    public long getIdExtra() {
        return idExtra;
    }

    /**
     * @param idExtra the idExtra to set
     */
    public void setIdExtra(long idExtra) {
        this.idExtra = idExtra;
    }

    /**
     * @return the compra
     */
    public MasterCompra getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(MasterCompra compra) {
        this.compra = compra;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
}
