package com.controller.ventas;

import com.persistencia.AlmacenDAO;
import com.persistencia.ClienteDAO;
import com.persistencia.ProductoDAO;
import com.pojos.Almacen;
import com.pojos.Cliente;
import com.pojos.Producto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;

@ManagedBean(name = "VentasBean")
@ViewScoped
public class VentasBean {
    private List<Almacen> almacenList;
    private List<Cliente> clienteList;
    private List<Producto> productoList;
    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private AlmacenDAO almacenDAO = new AlmacenDAO();

    public VentasBean() {
        try {
            setClienteList(clienteDAO.getClientes());
            setProductoList(productoDAO.getProductos());
            setAlmacenList(almacenDAO.getAlmacenes());
        } catch (Exception e) {
            e.printStackTrace();
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
}
