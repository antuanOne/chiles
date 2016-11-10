/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author virtual.user
 */
public class GenericBean {

    public void showInfoMessage(String encabezado, String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, encabezado, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showWarningMessage(String encabezado, String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, encabezado, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void showErrorMessage(String encabezado, String mensaje) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, encabezado, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
