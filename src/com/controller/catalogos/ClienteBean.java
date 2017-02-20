package com.controller.catalogos;

import com.persistencia.ClienteDAO;
import com.persistencia.ProveedorDAO;
import com.pojos.Cliente;
import com.pojos.Proveedor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author virtual.user
 */
@ManagedBean(name = "ClienteBean")
@ViewScoped
public class ClienteBean implements Serializable {

    final private String msgHeader = "Catalogo de Clientes";
    private List<Cliente> listClientes;
    private Cliente cliente;
    private Cliente clienteGral;
    final private ClienteDAO clienteDAO = new ClienteDAO();

    public ClienteBean() {

        nuevo();
    }

    final public void nuevo() {
        try {
            listClientes = clienteDAO.getClientes();
            cliente = new Cliente();
            clienteGral = new Cliente();
        } catch (Exception ex) {
            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardar() {
        try {
            clienteDAO.guardaCliente(cliente);
            nuevo();
        } catch (Exception ex) {
            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminar() {
        try {
            clienteDAO.eliminarCliente(cliente);
            nuevo();
        } catch (Exception ex) {
            Logger.getLogger(ClienteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect() {
        cliente = clienteGral;
    }


    public List<Cliente> getListClientes() {
        return listClientes;
    }

    public void setListClientes(List<Cliente> listClientes) {
        this.listClientes = listClientes;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getClienteGral() {
        return clienteGral;
    }

    public void setClienteGral(Cliente clienteGral) {
        this.clienteGral = clienteGral;
    }
}
