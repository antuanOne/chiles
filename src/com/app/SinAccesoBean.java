package com.app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "SinAccesoBean")
@ViewScoped
public class SinAccesoBean {

    public void irLogin(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(SinAccesoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
