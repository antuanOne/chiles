package com.controller;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Usuario on 07/02/2017.
 */
public class Menu implements Serializable {
    private String aplicacion;
    private String noAplicacion;
    private List<Acceso> accesos;

    /**
     * @return the aplicacion
     */
    public String getAplicacion() {
        return aplicacion;
    }

    /**
     * @param aplicacion the aplicacion to set
     */
    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    /**
     * @return the noAplicacion
     */
    public String getNoAplicacion() {
        return noAplicacion;
    }

    /**
     * @param noAplicacion the noAplicacion to set
     */
    public void setNoAplicacion(String noAplicacion) {
        this.noAplicacion = noAplicacion;
    }

    /**
     * @return the accesos
     */
    public List<Acceso> getAccesos() {
        return accesos;
    }

    /**
     * @param accesos the accesos to set
     */
    public void setAccesos(List<Acceso> accesos) {
        this.accesos = accesos;
    }


}

