package com.controller.catalogos;

import com.persistencia.ProductoDAO;
import com.pojos.Producto;
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
@ManagedBean(name = "ProductoBean")
@ViewScoped
public class ProductorBean implements Serializable {

    final private String msgHeader = "Catalogo de proveedores";
    private List<Producto> listProductos;
    private Producto producto;
    private Producto productorGral;
    final private ProductoDAO productoDAO = new ProductoDAO();

    public ProductorBean() {

        nuevo();
    }

    final public void nuevo() {
        try {
            listProductos = productoDAO.getProductos();
            producto = new Producto();
            productorGral = new Producto();
        } catch (Exception ex) {
            Logger.getLogger(ProductorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardar() {
        try {
            productoDAO.guardar(producto);
            nuevo();
        } catch (Exception ex) {
            Logger.getLogger(ProductorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminar() {
        try {
            productoDAO.eliminar(producto);
            nuevo();
        } catch (Exception ex) {
            Logger.getLogger(ProductorBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect() {
        producto = productorGral;
    }

    /**
     * @return the listProductos
     */
    public List<Producto> getListProductos() {
        return listProductos;
    }

    /**
     * @param listProductos the listProductos to set
     */
    public void setListProductos(List<Producto> listProductos) {
        this.listProductos = listProductos;
    }

    /**
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * @return the productorGral
     */
    public Producto getProductorGral() {
        return productorGral;
    }

    /**
     * @param productorGral the productorGral to set
     */
    public void setProductorGral(Producto productorGral) {
        this.productorGral = productorGral;
    }

   
}
