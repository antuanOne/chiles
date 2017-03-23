package com.app;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


@ManagedBean(name = "datosHeaderBean")
@ViewScoped
public class DatosHeaderBean extends GenericBean implements Serializable {

    public void salir() {
        try {

            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String context =  request.getContextPath();

            FacesContext.getCurrentInstance().getExternalContext().redirect(context + "/login.xhtml");

            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        } catch (IOException ex) {
            Logger.getLogger(DatosHeaderBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inicio() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoBusqueda", null);
            String urlInicio = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/inicio/home.xhtml";
            FacesContext.getCurrentInstance().getExternalContext().redirect(urlInicio);
        } catch (IOException ex) {
            Logger.getLogger(DatosHeaderBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
