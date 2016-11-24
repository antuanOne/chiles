package com.controller.compras;

import com.app.GenericBean;
import com.persistencia.AlmacenDAO;
import com.persistencia.ComprasDAO;
import com.persistencia.ProductoDAO;
import com.persistencia.ProveedorDAO;
import com.pojos.*;
import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antuan.yanez
 */
@ManagedBean(name = "HistorialComprasBean")
@ViewScoped
public class HistorialComprasBean extends GenericBean implements Serializable {


    final private AlmacenDAO almacenDAO = new AlmacenDAO();
    private List<Almacen> listaAlmacen;
    private ObjetoBusqueda objBusqueda = new ObjetoBusqueda();
    private List<MasterCompra> listCompra;
    private MasterCompra compra;


    final private String msgHeader = "Historial compras";

    final private ComprasDAO comprasDAO = new ComprasDAO();

    public HistorialComprasBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {

            try {
                setListaAlmacen(getAlmacenDAO().getAlmacenes());
            } catch (Exception e) {
                e.printStackTrace();
            }

            setObjBusqueda(((ObjetoBusqueda) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("objetoBusqueda")));
            if (getObjBusqueda() == null) {
                setObjBusqueda(new ObjetoBusqueda());
                getObjBusqueda().setFecha1(Calendar.getInstance().getTime());
                getObjBusqueda().setFecha2(Calendar.getInstance().getTime());
            } else {
                buscar();
            }
            setCompra(new MasterCompra());

        }
    }

    public void fecha1Select(SelectEvent event) {
        getObjBusqueda().setFecha1((Date) event.getObject());
    }

    public void fecha2Select(SelectEvent event) {
        getObjBusqueda().setFecha2((Date) event.getObject());
    }

    final public void buscar() {
        if (getObjBusqueda().getAlmacen() == 0) {
            showWarningMessage(msgHeader, "Seleccione un almacen");
            return;
        }
        try {
            setListCompra(comprasDAO.trarComprasPorAlmacenFechas(getObjBusqueda().getFecha1(),
                    getObjBusqueda().getFecha2(), getObjBusqueda().getAlmacen()));
        } catch (Exception ex) {
            Logger.getLogger(HistorialComprasBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al traer las compras Error " + ex.getMessage());
        }
    }

    public void verCompra() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("objetoBusqueda", getObjBusqueda());
            FacesContext.getCurrentInstance().getExternalContext().redirect("../compras/comprasDetalle.xhtml?id=" + getCompra().getIdCompra());
        } catch (IOException ex) {
            Logger.getLogger(HistorialComprasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public AlmacenDAO getAlmacenDAO() {
        return almacenDAO;
    }

    public List<Almacen> getListaAlmacen() {
        return listaAlmacen;
    }

    public void setListaAlmacen(List<Almacen> listaAlmacen) {
        this.listaAlmacen = listaAlmacen;
    }

    public ObjetoBusqueda getObjBusqueda() {
        return objBusqueda;
    }

    public void setObjBusqueda(ObjetoBusqueda objBusqueda) {
        this.objBusqueda = objBusqueda;
    }

    public List<MasterCompra> getListCompra() {
        return listCompra;
    }

    public void setListCompra(List<MasterCompra> listCompra) {
        this.listCompra = listCompra;
    }

    public MasterCompra getCompra() {
        return compra;
    }

    public void setCompra(MasterCompra compra) {
        this.compra = compra;
    }
}
