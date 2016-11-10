package com.controller.compras;

import com.app.GenericBean;
import com.persistencia.AlmacenDAO;
import com.persistencia.ComprasDAO;
import com.persistencia.ProductoDAO;
import com.persistencia.ProveedorDAO;
import com.pojos.Almacen;
import com.pojos.ConceptosExtra;
import com.pojos.DetalleCompra;
import com.pojos.MasterCompra;
import com.pojos.Producto;
import com.pojos.Proveedor;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author antuan.yanez
 */
@ManagedBean(name = "ComprasBean")
@ViewScoped
public class ComprasBean extends GenericBean implements Serializable {

    final private String msgHeader = "Compras";

    private List<Almacen> listaAlmacen;
    private List<Producto> listaProductos;
    private List<Proveedor> listProveedores;

    private long id_producto;
    private int cantidad;
    private float iva = 0.16f;
    private float precio;
    private MasterCompra compra;
    private DetalleCompra prodABorrar;
    private String codigo;
    private Producto productoBusqueda;
    private Proveedor proveedorInfo;
    private ConceptosExtra conceptoExtra;
    private int indexConceptoExtra;

    final private ProveedorDAO proveedorDAO = new ProveedorDAO();
    final private ProductoDAO productoDAO = new ProductoDAO();
    final private AlmacenDAO almacenDAO = new AlmacenDAO();
    final private ComprasDAO comprasDAO = new ComprasDAO();

    public ComprasBean() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            nuevo();
            listProveedores = proveedorDAO.getProveedores();
        }
    }

    final public void nuevo() {
        compra = new MasterCompra();
        listaProductos = productoDAO.getProductos();
        try{
        listaAlmacen = almacenDAO.getAlmacenes();
        }catch(Exception e){
            listaAlmacen = new ArrayList<>();
        }
        precio = 0;
        cantidad = 0;
        iva = 0;
        id_producto = 0L;
        compra.calculaTotales();
        conceptoExtra = new ConceptosExtra();
    }

    public synchronized void guardarCompra() {
        try {
            comprasDAO.insertMasterCompra(compra);
        } catch (Exception ex) {
            Logger.getLogger(ComprasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void agregaExtra() {
        compra.getListaExtra().add(conceptoExtra);
        compra.calculaTotales();
        conceptoExtra = new ConceptosExtra();
    }

    public void seleccionaAlmacen() {
        compra.setListDetalle(new ArrayList<>());
    }

    public void seleccionaProveedor(ValueChangeEvent e) {
        Object obj = e.getNewValue();
        proveedorInfo = getProveedorFromList((long) obj);
    }

    private Proveedor getProveedorFromList(long idProv) {
        for (Proveedor prov : listProveedores) {
            if (prov.getIdProveedor() == idProv) {
                return prov;
            }
        }
        return null;
    }

    public void seleccionaIva() {
        System.out.println(iva);
    }

    public void seleccionaDolar() {
        System.out.println("");
    }

    public void addDetalle() {

        if (id_producto == 0) {
            showInfoMessage(msgHeader, "Porfavor seleccione un producto");
            return;
        }

        if (cantidad == 0) {
            showInfoMessage(msgHeader, "Porfavor NO deje la cantidad en ceros");
            return;
        }

        DetalleCompra dv = new DetalleCompra();
        dv.setProducto(productoBusqueda);
        boolean prodPresente = compra.getListDetalle().contains(dv);
        if (!prodPresente) {
            dv.setPrecio(precio);
            dv.setCantidad(cantidad);
            dv.setIva(iva);
            compra.getListDetalle().add(dv);
        } else {
            dv = compra.getListDetalle().get(compra.getListDetalle().indexOf(dv));
            dv.setPrecio(precio);
            cantidad = cantidad + dv.getCantidad();
            dv.setCantidad(cantidad);
            dv.setIva(iva);
        }
        codigo = "";
        compra.calculaTotales();
        cantidad = 0;
        precio = 0;
    }

    public void seleccionaProducto(ValueChangeEvent e) {
        Object obj = e.getNewValue();
        if (obj != null) {
            String valor = obj.toString();
            id_producto = ((Long) Long.parseLong(valor));
        } else {
            return;
        }

        try {
            productoBusqueda = productoDAO.getProductoById(id_producto);
            codigo = productoBusqueda.getCodigo();
        } catch (Exception ex) {
            Logger.getLogger(ComprasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getProductoXcodigo() {
        productoBusqueda = productoDAO.getProductoByCcodigo(codigo);
        if (productoBusqueda == null) {
            showErrorMessage(msgHeader, "El codigo no coincide con ningun producto, vuelva a intentarlo");
            return;
        }
        id_producto = productoBusqueda.getIdProducto();
    }

    public void borrarRenglon() {
        compra.getListDetalle().remove(prodABorrar);
        compra.calculaTotales();
    }
    
    public void borrarConceptoExtra() {
        compra.getListaExtra().remove(indexConceptoExtra);
        compra.calculaTotales();
        System.out.println("kmfkefmkefmkefmkemfke  "+ indexConceptoExtra);
    }

    public void regresar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../compras/histCompra.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ComprasBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @return the listaProductos
     */
    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    /**
     * @return the id_producto
     */
    public Long getId_producto() {
        return id_producto;
    }

    /**
     * @param id_producto the id_producto to set
     */
    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
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
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the iva
     */
    public float getIva() {
        return iva;
    }

    /**
     * @param iva the iva to set
     */
    public void setIva(float iva) {
        this.iva = iva;
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return the compra
     */
    public MasterCompra getCompra() {
        return compra;
    }

    /**
     * @param compra the compra to set
     */
    public void setCompra(MasterCompra compra) {
        this.compra = compra;
    }

    /**
     * @return the prodABorrar
     */
    public DetalleCompra getProdABorrar() {
        return prodABorrar;
    }

    /**
     * @param prodABorrar the prodABorrar to set
     */
    public void setProdABorrar(DetalleCompra prodABorrar) {
        this.prodABorrar = prodABorrar;
    }

    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
     * @return the proveedorInfo
     */
    public Proveedor getProveedorInfo() {
        return proveedorInfo;
    }

    /**
     * @param proveedorInfo the proveedorInfo to set
     */
    public void setProveedorInfo(Proveedor proveedorInfo) {
        this.proveedorInfo = proveedorInfo;
    }

    /**
     * @return the conceptoExtra
     */
    public ConceptosExtra getConceptoExtra() {
        return conceptoExtra;
    }

    /**
     * @param conceptoExtra the conceptoExtra to set
     */
    public void setConceptoExtra(ConceptosExtra conceptoExtra) {
        this.conceptoExtra = conceptoExtra;
    }

    /**
     * @return the indexConceptoExtra
     */
    public int getIndexConceptoExtra() {
        return indexConceptoExtra;
    }

    /**
     * @param indexConceptoExtra the indexConceptoExtra to set
     */
    public void setIndexConceptoExtra(int indexConceptoExtra) {
        this.indexConceptoExtra = indexConceptoExtra;
    }

}
