package com.controller.catalogos;

import com.app.GenericBean;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.persistencia.AlmacenDAO;
import com.pojos.Almacen;

@ManagedBean(name = "AlmacenBean")
@ViewScoped
public class AlmacenBean extends GenericBean implements Serializable {

    final private String msgHeader = "Catalogo de Almacenes";
    private List<Almacen> listaAlmacen;
    private Almacen almacen;
    private Almacen almacenGral;
    final private AlmacenDAO almacenModule = new AlmacenDAO();

    public AlmacenBean() {
        nuevo();
    }

    final public void nuevo() {
        almacen = new Almacen();
        almacenGral = new Almacen();
        try {
            listaAlmacen = almacenModule.getAlmacenes();
        } catch (Exception ex) {
            Logger.getLogger(AlmacenBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al cargar los almacenes " + ex.getMessage());
        }
    }

    public void guardar() {
        try {
            almacenModule.guardaAlmacen(almacen);
            nuevo();
            showInfoMessage(msgHeader, "El almacén se ha guardado con éxito.");
        } catch (Exception ex) {
            Logger.getLogger(AlmacenBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al guardar el almacén " + ex.getMessage());
        }
    }

    public void eliminar() {
        try {
            almacenModule.eliminar(almacen);
            nuevo();
            showInfoMessage(msgHeader, "El almacén se ha eliminado con éxito.");
        } catch (Exception ex) {
            Logger.getLogger(AlmacenBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al eliminar el almacén " + ex.getMessage());
        }
    }

    public void onRowSelect() {
        almacen = almacenGral;
    }

    /**
     * @return the listaAlmacen
     */
    public List<Almacen> getListaAlmacen() {
        return listaAlmacen;
    }

    /**
     * @param listaAlmacen the listaAlmacen to set
     */
    public void setListaAlmacen(List<Almacen> listaAlmacen) {
        this.listaAlmacen = listaAlmacen;
    }

    /**
     * @return the almacen
     */
    public Almacen getAlmacen() {
        return almacen;
    }

    /**
     * @param almacen the almacen to set
     */
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    /**
     * @return the almacenGral
     */
    public Almacen getAlmacenGral() {
        return almacenGral;
    }

    /**
     * @param almacenGral the almacenGral to set
     */
    public void setAlmacenGral(Almacen almacenGral) {
        this.almacenGral = almacenGral;
    }

}
