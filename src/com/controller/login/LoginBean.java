/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.login;

import com.app.GenericBean;
import com.persistencia.UsuarioDAO;
import com.pojos.Usuario;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author antuan.yanez
 */
@ManagedBean(name = "LoginBean")
@ViewScoped
public class LoginBean extends GenericBean implements Serializable {

    final private String msgHeader = "Login";
    private String usuarioLogin;
    private String password;

    public LoginBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            usuarioLogin = "";
            password = "";
        }
    }

    public void entrar() {
        UsuarioDAO usuarioM = new UsuarioDAO();
        if (usuarioM.isUsuarioValido(usuarioLogin, password)) {
            Usuario usuarioLog = usuarioM.getUsuario(usuarioLogin);
            try {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", usuarioLog);
                FacesContext.getCurrentInstance().getExternalContext().redirect("inicio/home.xhtml");
                // FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            showErrorMessage("Informacion incorrecta", "Verifique su usuario y password");
        }
    }

    /**
     * @return the usuarioLogin
     */
    public String getUsuarioLogin() {
        return usuarioLogin;
    }

    /**
     * @param usuarioLogin the usuarioLogin to set
     */
    public void setUsuarioLogin(String usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
