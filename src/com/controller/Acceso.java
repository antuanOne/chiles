package com.controller;

import java.io.Serializable;

public class Acceso implements Serializable {

    private String acceso;
    private String modulo;
    private String url;
    private boolean visible;

    /**
     * @return the Acceso
     */
    public String getAcceso() {
        return acceso;
    }

    /**
     * @param acceso the Acceso to set
     */
    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    /**
     * @return the modulo
     */
    public String getModulo() {
        return modulo;
    }

    /**
     * @param modulo the modulo to set
     */
    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
