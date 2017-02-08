/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author virtual.user
 */
@ManagedBean(name = "SinAccesoBean")
@ViewScoped
public class SinAccesoBean {
    
    public void irLogin(){
        try {
            String urlSalir = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "";
            FacesContext.getCurrentInstance().getExternalContext().redirect(urlSalir);
        } catch (IOException ex) {
            Logger.getLogger(SinAccesoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
