package com.seguridad;

import com.app.GenericBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpSession;

/**
 *
 * @author virtual.user
 */
public class AuthorizationListener extends GenericBean implements PhaseListener {

    public void afterPhase(PhaseEvent event) {

        FacesContext facesContext = event.getFacesContext();
        String currentPage = facesContext.getViewRoot().getViewId();

        int isLoginPage = currentPage.lastIndexOf("/login.xhtml");
        int isSinAccesoPage = currentPage.lastIndexOf("/sinAcceso.xhtml");
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        //Object currentUser = session.getAttribute("username");
        if ((isLoginPage != 0) && (isSinAccesoPage != 0)) {
            try {
                String usu = getUsuario().getNombre();
                if(usu == null){
                    throw new Exception();
                }
            } catch (Exception e) {
                try {
                    String urlSalir = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/faces/sinAcceso.xhtml";
                    FacesContext.getCurrentInstance().getExternalContext().redirect(urlSalir);
                } catch (IOException ex) {
                    Logger.getLogger(AuthorizationListener.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public void beforePhase(PhaseEvent event) {
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}
