package com.controller.catalogos;

import com.persistencia.ProveedorDAO;
import com.pojos.Proveedor;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author virtual.user
 */
@ManagedBean(name = "ProveedorBean")
@ViewScoped
public class ProveedorBean implements Serializable {

    final private String msgHeader = "Catalogo de proveedores";
    private List<Proveedor> listProveedores;
    private Proveedor proveedor;
    private Proveedor proveedorGral;
    final private ProveedorDAO proveedorDAO = new ProveedorDAO();

    public ProveedorBean() {

        nuevo();
    }

    final public void nuevo() {
        try {
            listProveedores = proveedorDAO.getProveedores();
            proveedor = new Proveedor();
            proveedorGral = new Proveedor();
        } catch (Exception ex) {
            Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardar() {
        try {
            proveedorDAO.guardaProveedor(proveedor);
            nuevo();
        } catch (Exception ex) {
            Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminar() {
        try {
            proveedorDAO.eliminarProveedor(proveedor);
            nuevo();
        } catch (Exception ex) {
            Logger.getLogger(ProveedorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect() {
        proveedor = proveedorGral;
    }

    /**
     * @return the listProveedores
     */
    public List<Proveedor> getListProveedores() {
        return listProveedores;
    }

    /**
     * @param listProveedores the listProveedores to set
     */
    public void setListProveedores(List<Proveedor> listProveedores) {
        this.listProveedores = listProveedores;
    }

    /**
     * @return the proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the proveedorGral
     */
    public Proveedor getProveedorGral() {
        return proveedorGral;
    }

    /**
     * @param proveedorGral the proveedorGral to set
     */
    public void setProveedorGral(Proveedor proveedorGral) {
        this.proveedorGral = proveedorGral;
    }
}
