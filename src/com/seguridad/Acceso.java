/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad;

/**
 *
 * @author antuan.yanez
 */
public class Acceso {
    private Long acceso;
    private String nombreAcceso;
    private Long sucursal;
    private String nombreSucursal;
    private String privilegio;
    private String nombreprivilegio;

    /**
     * @return the Acceso
     */
    public Long getAcceso() {
        return acceso;
    }

    /**
     * @param acceso the Acceso to set
     */
    public void setAcceso(Long acceso) {
        this.acceso = acceso;
    }

    /**
     * @return the nombreAcceso
     */
    public String getNombreAcceso() {
        return nombreAcceso;
    }

    /**
     * @param nombreAcceso the nombreAcceso to set
     */
    public void setNombreAcceso(String nombreAcceso) {
        this.nombreAcceso = nombreAcceso;
    }

    /**
     * @return the sucursal
     */
    public Long getSucursal() {
        return sucursal;
    }

    /**
     * @param sucursal the sucursal to set
     */
    public void setSucursal(Long sucursal) {
        this.sucursal = sucursal;
    }

    /**
     * @return the privilegio
     */
    public String getPrivilegio() {
        return privilegio;
    }

    /**
     * @param privilegio the privilegio to set
     */
    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    /**
     * @return the nombreSucursal
     */
    public String getNombreSucursal() {
        return nombreSucursal;
    }

    /**
     * @param nombreSucursal the nombreSucursal to set
     */
    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    /**
     * @return the nombreprivilegio
     */
    public String getNombreprivilegio() {
        return nombreprivilegio;
    }

    /**
     * @param nombreprivilegio the nombreprivilegio to set
     */
    public void setNombreprivilegio(String nombreprivilegio) {
        this.nombreprivilegio = nombreprivilegio;
    }
}
