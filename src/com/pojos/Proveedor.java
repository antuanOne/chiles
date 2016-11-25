/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "PROVEEDOR")
public class Proveedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROVEEDOR")
    private long idProveedor;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "NOMBRE_PROVEEDOR")
    private String nombreProveedor;
    @OneToOne
    @JoinColumn(name = "ID_DIRECCION_PROVEEDOR")
    private DireccionProveedor direccion;
    @OneToOne
    @JoinColumn(name = "ID_PROVEEDOR_CONTACTO")
    private Contacto contacto;

    public Proveedor() {
        this.idProveedor = 0;
        direccion = new DireccionProveedor();
        contacto = new Contacto();
    }

    public Proveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Proveedor) {
            Proveedor a = (Proveedor) obj;
            if (a.getIdProveedor() == this.getIdProveedor()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = (int) (getIdProveedor() * 17);
        return hash;
    }

    /**
     * @return the idProveedor
     */
    public long getIdProveedor() {
        return idProveedor;
    }

    /**
     * @param idProveedor the idProveedor to set
     */
    public void setIdProveedor(long idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the nombreProveedor
     */
    public String getNombreProveedor() {
        return nombreProveedor;
    }

    /**
     * @param nombreProveedor the nombreProveedor to set
     */
    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    /**
     * @return the direccion
     */
    public DireccionProveedor getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(DireccionProveedor direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the contacto
     */
    public Contacto getContacto() {
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    
}
