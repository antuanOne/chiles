package com.controller.compras;

import com.app.GenericBean;
import com.persistencia.ComprasDAO;
import com.pojos.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antuan.yanez
 */
@ManagedBean(name = "ComprasDetalleBean")
@ViewScoped
public class ComprasDetalleBean extends GenericBean implements Serializable {

    final private String msgHeader = "Compras Detalle";

    private MasterCompra compra;
    private boolean cancelaEtiquta = false;

    final private ComprasDAO comprasDAO = new ComprasDAO();

    public ComprasDetalleBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            long id = Long.parseLong(request.getParameter("id"));
            System.out.println(id);
            try {
                compra = comprasDAO.getCompraById(id);
                if(compra.getEstatus().toUpperCase().equals('C')){
                    cancelaEtiquta = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void nuevaCompra(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../compras/compras.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(ComprasDetalleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelaCompra() {
        try {
            comprasDAO.cancelaCompra(compra);
            setCancelaEtiquta(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public MasterCompra getCompra() {
        return compra;
    }

    public void setCompra(MasterCompra compra) {
        this.compra = compra;
    }

    public boolean isCancelaEtiquta() {
        return cancelaEtiquta;
    }

    public void setCancelaEtiquta(boolean cancelaEtiquta) {
        this.cancelaEtiquta = cancelaEtiquta;
    }
}
