package com.controller.cuentasPorPagar;

import com.app.GenericBean;
import com.persistencia.AlmacenDAO;
import com.persistencia.ComprasDAO;
import com.persistencia.CuentasPorPagarDAO;
import com.pojos.Almacen;
import com.pojos.MasterCompra;
import com.pojos.PagoCompra;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "CuentasPorPagarBean")
@ViewScoped
public class CuentasPorPagarBean extends GenericBean{

    private List<MasterCompra> comprasList;
    private MasterCompra compra;
    private List<PagoCompra> pagoList;
    private PagoCompra pago;
    private PagoCompra pagoGral;
    private boolean allowAdd = false;
    private boolean allowLiquidar = false;
    private boolean allowReturn = false;
    private String msgHeader = "Cuentas por pagar";
    private float totalPagos;
    private int idAlmacen;
    private List<Almacen> listaAlmacen;
    final private CuentasPorPagarDAO cuentasPorPagarDAO = new CuentasPorPagarDAO();
    final private AlmacenDAO almacenDAO = new AlmacenDAO();
    final private ComprasDAO compraDAO = new ComprasDAO();

    public CuentasPorPagarBean(){
        initialize();
    }

    private void initialize(){
        try {
            listaAlmacen = almacenDAO.getAlmacenes();
            idAlmacen = getUsuario().getAlmacen().getIdAlmacen();
            comprasList = cuentasPorPagarDAO.getComprasPorPagar(idAlmacen);
            compra = new MasterCompra();
            pago = new PagoCompra();
            allowAdd = false;
            allowLiquidar=false;
            allowReturn = false;
            pagoList = new ArrayList<>();
        }catch(Exception ex){

        }
    }

    public void onRowSelect() {
        pagoList = cuentasPorPagarDAO.getPagosCompra(compra.getIdCompra());
        pago.setCompra(compra);
        calculaTotalPagos();
        if(compra.getEstatus().equals("A")){
            allowAdd = false;
            allowLiquidar=false;
            allowReturn = true;
        }else{
            allowAdd = true;
            allowReturn = false;
        }
    }

    public void onRowSelectPago() {
    }

    public void guardaPago(){
        if(pago.getMonto()<=0){
            showErrorMessage(msgHeader, "No puede hacer un pago en ceros");
            return ;
        }

        if(compra.getTotalGeneral()< (getTotalPagos() + pago.getMonto())){
            showErrorMessage(msgHeader,"No puede hacer un pago que exceda el tota de la compra");
            return;
        }
        cuentasPorPagarDAO.savePago(pago);
        pagoList = cuentasPorPagarDAO.getPagosCompra(compra.getIdCompra());
        calculaTotalPagos();
    }

    private void calculaTotalPagos(){
        totalPagos = 0 ;
        for(PagoCompra row : pagoList){
            if(row.getEstatus().toUpperCase().equals("A")){
                totalPagos =(getTotalPagos() + row.getMonto());
            }

        }

        if(totalPagos == compra.getTotalGeneral()){
            allowLiquidar = true;
        }
    }

    public void liquidaCompra(){

        try {
            compraDAO.liquidaCompra(compra);
            comprasList = cuentasPorPagarDAO.getComprasPorPagar(idAlmacen);
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void regresarCompra(){

        try {
            compraDAO.regresarCompra(compra);
            comprasList = cuentasPorPagarDAO.getComprasPorPagar(idAlmacen);
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void eliminaPago(){
        cuentasPorPagarDAO.eliminaPago(pagoGral);
        pagoList = cuentasPorPagarDAO.getPagosCompra(compra.getIdCompra());
        calculaTotalPagos();
    }


    public void seleccionaAlmacen() {
        try {
            comprasList = cuentasPorPagarDAO.getComprasPorPagar(idAlmacen);
        }catch(Exception ex){}
    }

    public List<MasterCompra> getComprasList() {
        return comprasList;
    }

    public void setComprasList(List<MasterCompra> comprasList) {
        this.comprasList = comprasList;
    }

    public MasterCompra getCompra() {
        return compra;
    }

    public void setCompra(MasterCompra compra) {
        this.compra = compra;
    }

    public List<PagoCompra> getPagoList() {
        return pagoList;
    }

    public void setPagoList(List<PagoCompra> pagoList) {
        this.pagoList = pagoList;
    }

    public PagoCompra getPago() {
        return pago;
    }

    public void setPago(PagoCompra pago) {
        this.pago = pago;
    }

    public PagoCompra getPagoGral() {
        return pagoGral;
    }

    public void setPagoGral(PagoCompra pagoGral) {
        this.pagoGral = pagoGral;
    }

    public boolean isAllowAdd() {
        return allowAdd;
    }

    public void setAllowAdd(boolean allowAdd) {
        this.allowAdd = allowAdd;
    }

    public float getTotalPagos() {
        return totalPagos;
    }

    public void setTotalPagos(float totalPagos) {
        this.totalPagos = totalPagos;
    }

    public boolean isAllowLiquidar() {
        return allowLiquidar;
    }

    public void setAllowLiquidar(boolean allowLiquidar) {
        this.allowLiquidar = allowLiquidar;
    }

    public int getIdAlmacen() {
        return idAlmacen;
    }

    public void setIdAlmacen(int idAlmacen) {
        this.idAlmacen = idAlmacen;
    }

    public List<Almacen> getListaAlmacen() {
        return listaAlmacen;
    }

    public void setListaAlmacen(List<Almacen> listaAlmacen) {
        this.listaAlmacen = listaAlmacen;
    }

    public boolean isAllowReturn() {
        return allowReturn;
    }

    public void setAllowReturn(boolean allowReturn) {
        this.allowReturn = allowReturn;
    }
}
