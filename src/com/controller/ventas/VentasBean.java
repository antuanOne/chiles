package com.controller.ventas;

import com.app.GenericBean;
import com.persistencia.*;
import com.pojos.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean(name = "VentasBean")
@ViewScoped
public class VentasBean  extends GenericBean implements Serializable {
    private List<Almacen> almacenList;
    private List<Cliente> clienteList;
    private List<Producto> productoList;
    private MasterVenta venta;
    private Cliente clienteInfo;
    private String codigo;
    private Producto productoBusqueda;
    private long id_producto;
    private int cantidad;
    private int existencia;
    private float iva = 0.16f;
    private float precio;
    private DetalleVenta prodABorrar;

    private final String msgHeader= "Ventas";
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final AlmacenDAO almacenDAO = new AlmacenDAO();
    private final InventarioDAO inventarioDAO = new InventarioDAO();
    private final VentasDAO ventasDAO = new VentasDAO();

    public VentasBean() {
        nuevo();
    }

    final public void nuevo(){
        venta = new MasterVenta();
        venta.getAlmacen().setIdAlmacen(getUsuario().getAlmacen().getIdAlmacen());
        try {
            setClienteList(clienteDAO.getClientes());
            setProductoList(productoDAO.getProductos());
            setAlmacenList(almacenDAO.getAlmacenes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        precio = 0;
        cantidad = 0;
        iva = 0;
        id_producto = 0L;
        venta.calculaTotales();
    }

    public void seleccionaAlmacen() {
        getVenta().setListaDetalle(new ArrayList<>());
    }

    public void selectConcecutivo() {
        System.out.println("");
    }

    public void seleccionaCliente(ValueChangeEvent e) {
        Object obj = e.getNewValue();
        clienteInfo = clienteDAO.getCliente((int) obj);
    }

    public void getProductoXcodigo() {
        productoBusqueda = (productoDAO.getProductoByCcodigo(codigo));
        existencia = inventarioDAO.getExistenciaProducto(venta.getAlmacen().getIdAlmacen(),id_producto);

        if (productoBusqueda == null) {
            showErrorMessage(msgHeader, "El codigo no coincide con ningun producto, vuelva a intentarlo");
            return;
        }
        setId_producto(productoBusqueda.getIdProducto());
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

        DetalleVenta detalleVenta= new DetalleVenta();
        detalleVenta.setProducto(productoBusqueda);

        boolean prodPresente = venta.getListaDetalle().contains(detalleVenta);
        if (!prodPresente) {
            detalleVenta.setPrecio(precio);
            detalleVenta.setCantidad(cantidad);
            venta.getListaDetalle().add(detalleVenta);
        } else {
            detalleVenta = venta.getListaDetalle().get(venta.getListaDetalle().indexOf(detalleVenta));
            detalleVenta.setPrecio(precio);
            cantidad = cantidad + detalleVenta.getCantidad();
            detalleVenta.setCantidad(cantidad);
        }

        codigo = "";
        venta.calculaTotales();
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
            existencia = inventarioDAO.getExistenciaProducto(venta.getAlmacen().getIdAlmacen(),id_producto);
            codigo = productoBusqueda.getCodigo();
        } catch (Exception ex) {
            Logger.getLogger(VentasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarRenglon() {
        venta.getListaDetalle().remove(prodABorrar);
        venta.calculaTotales();
    }

    public void guardarVenta(){
        try {
            venta = ventasDAO.insertMasterVenta(venta);
            FacesContext.getCurrentInstance().getExternalContext().redirect("../ventas/ventasDetalle.xhtml?id=" + venta.getIdVenta());

        } catch (Exception ex) {
            Logger.getLogger(VentasBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Almacen> getAlmacenList() {
        return almacenList;
    }

    public void setAlmacenList(List<Almacen> almacenList) {
        this.almacenList = almacenList;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public void setProductoList(List<Producto> productoList) {
        this.productoList = productoList;
    }

    public MasterVenta getVenta() {
        return venta;
    }

    public void setVenta(MasterVenta venta) {
        this.venta = venta;
    }

    public Cliente getClienteInfo() {
        return clienteInfo;
    }

    public void setClienteInfo(Cliente clienteInfo) {
        this.clienteInfo = clienteInfo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public long getId_producto() {
        return id_producto;
    }

    public void setId_producto(long id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public DetalleVenta getProdABorrar() {
        return prodABorrar;
    }

    public void setProdABorrar(DetalleVenta prodABorrar) {
        this.prodABorrar = prodABorrar;
    }
}
