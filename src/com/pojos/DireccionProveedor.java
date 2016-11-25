package com.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name = "PROVEEDOR_DIRECCION")
public class DireccionProveedor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DIRECCION_PROVEEDOR")
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

    
    @Override
    public String toString() {
        return  "Calle.- " + getCalle() + " Colonia.- " + getColonia() + " CP.- " + getCp() + ", No exterior.- " + getNoExterior() + " No interior .-" + getNoInterior() + ", Referencia .-" + getReferencia();
    }
    
    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * @param cp the cp to set
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * @return the noExterior
     */
    public String getNoExterior() {
        return noExterior;
    }

    /**
     * @param noExterior the noExterior to set
     */
    public void setNoExterior(String noExterior) {
        this.noExterior = noExterior;
    }

    /**
     * @return the noInterior
     */
    public String getNoInterior() {
        return noInterior;
    }

    /**
     * @param noInterior the noInterior to set
     */
    public void setNoInterior(String noInterior) {
        this.noInterior = noInterior;
    }

    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
