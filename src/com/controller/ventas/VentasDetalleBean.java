package com.controller.ventas;

import com.persistencia.TipoPagoDAO;
import com.persistencia.VentasDAO;
import com.pojos.MasterVenta;
import com.pojos.TipoPago;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "VentasDetalleBean")
@ViewScoped
public class VentasDetalleBean {
    final private String msgHeader = "Compras Detalle";

    private MasterVenta venta;
    private TipoPago tipoPago;
    private boolean cancelaEtiquta = false;

    final private VentasDAO ventasDAO = new VentasDAO();
    private final TipoPagoDAO tipoPagoDAO = new TipoPagoDAO();

    public VentasDetalleBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {

            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            long id = Long.parseLong(request.getParameter("id"));
            System.out.println(id);
            try {
                venta = ventasDAO.getVentaById(id);
                if(venta.getEstatus().toUpperCase().equals('C')){
                    setCancelaEtiquta(true);
                }
                venta.calculaTotales();
                tipoPago = tipoPagoDAO.getTipoPago(venta.getTipoPago());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void nuevaVenta(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../ventas/ventas.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(VentasDetalleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cancelaVenta() {
        try {
            ventasDAO.cancelaVenta(venta);
            setCancelaEtiquta(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MasterVenta getVenta() {
        return venta;
    }

    public void setVenta(MasterVenta venta) {
        this.venta = venta;
    }

    public boolean isCancelaEtiquta() {
        return cancelaEtiquta;
    }

    public void setCancelaEtiquta(boolean cancelaEtiquta) {
        this.cancelaEtiquta = cancelaEtiquta;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }
}
