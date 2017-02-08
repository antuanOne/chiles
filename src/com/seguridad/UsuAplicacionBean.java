/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad;


import com.app.GenericBean;
import com.persistencia.AlmacenDAO;
import com.persistencia.UsuarioDAO;
import com.pojos.Almacen;
import com.pojos.Usuario;
import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ViewScoped
@ManagedBean(name = "UsuAplicacionBean")
public class UsuAplicacionBean extends GenericBean implements Serializable {

    private List<Almacen> listaAlmacen;
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSeg;
    private Usuario usuarioGral;
  //  private List<Impresora> listaImpresoras;
    final private String msgHeader = "Catalogo de Usuarios";
    final private AlmacenDAO almacenModule = new AlmacenDAO();
    final private UsuarioDAO usuarioModule = new UsuarioDAO();
   // final private ImpresoraDAO impresoraModule = new ImpresoraDAO();

    public UsuAplicacionBean() {
        try {
            listaAlmacen = almacenModule.getAlmacenes();
            usuarioSeg = new Usuario();
            listaUsuarios = usuarioModule.getUsuarios();
        //    listaImpresoras = impresoraModule.llenaImpresoras();
        } catch (Exception ex) {
            Logger.getLogger(UsuAplicacionBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    final public void nuevo() {
        usuarioSeg = new Usuario();
    }

    public void eliminar() {
        try {
            usuarioModule.eliminar(usuarioSeg);
            listaUsuarios = usuarioModule.getUsuarios();
            usuarioSeg = new Usuario();
            showInfoMessage(msgHeader, "El usuario se ha eliminado con exito");
        } catch (Exception ex) {
            Logger.getLogger(UsuAplicacionBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al eliminar el usuario " + ex.getMessage());
        }
    }


    public void seleccionaAlmacen(ValueChangeEvent e) {
        usuarioSeg.setAlmacen(new Almacen((int) Integer.parseInt(e.getNewValue().toString())));

    }

    public void onRowSelect(SelectEvent event) {
        usuarioGral = (Usuario) event.getObject();
//        if(usuarioGral.getImpresora() == null){
//        usuarioGral.setImpresora(new Impresora());
//        }
        usuarioSeg = usuarioGral;
    }

    public void guardar() {
        try {
            if (usuarioSeg.getAlmacen().getIdAlmacen() == 0) {
                showInfoMessage(msgHeader, "Seleccione un Almacen");
                return;
            }

            if (usuarioSeg.getUser_name().equals("")) {
                showInfoMessage(msgHeader, "Agrege el User Name");
                return;
            }

            usuarioModule.guardar(usuarioSeg);

            listaUsuarios = usuarioModule.getUsuarios();
            usuarioSeg = new Usuario();
            showInfoMessage(msgHeader, "El Ususario se ha guardado con exito");
        } catch (Exception ex) {
            Logger.getLogger(UsuAplicacionBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al guardar el usuario " + ex.getMessage());
        }

    }

    /**
     * @return the listaAlmacen
     */
    public List<com.pojos.Almacen> getListaAlmacen() {
        return listaAlmacen;
    }

    /**
     * @param listaAlmacen the listaAlmacen to set
     */
    public void setListaAlmacen(List<com.pojos.Almacen> listaAlmacen) {
        this.setListaAlmacen(listaAlmacen);
    }

    /**
     * @return the listaUsuarios
     */
    public List<com.pojos.Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios the listaUsuarios to set
     */
    public void setListaUsuarios(List<com.pojos.Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * @return the usuarioGral
     */
    public com.pojos.Usuario getUsuarioGral() {
        return usuarioGral;
    }

    /**
     * @param usuarioGral the usuarioGral to set
     */
    public void setUsuarioGral(com.pojos.Usuario usuarioGral) {
        this.usuarioGral = usuarioGral;
    }

    /**
     * @return the usuarioSeg
     */
    public Usuario getUsuarioSeg() {
        return usuarioSeg;
    }

    /**
     * @param usuarioSeg the usuarioSeg to set
     */
    public void setUsuarioSeg(Usuario usuarioSeg) {
        this.usuarioSeg = usuarioSeg;
    }
}
