package com.controller.ventas;

import com.app.GenericBean;
import com.persistencia.AlmacenDAO;
import com.persistencia.ClienteDAO;
import com.persistencia.InventarioDAO;
import com.persistencia.ProductoDAO;
import com.pojos.Almacen;
import com.pojos.Cliente;
import com.pojos.MasterVenta;
import com.pojos.Producto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;
import java.util.ArrayList;
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

    private final String msgHeader= "Ventas";
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private AlmacenDAO almacenDAO = new AlmacenDAO();
    private InventarioDAO inventarioDAO = new InventarioDAO();

    public VentasBean() {
        venta = new MasterVenta();
        try {
            setClienteList(clienteDAO.getClientes());
            setProductoList(productoDAO.getProductos());
            setAlmacenList(almacenDAO.getAlmacenes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seleccionaAlmacen() {
        getVenta().setListaDetalle(new ArrayList<>());
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
}
