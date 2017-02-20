package com.persistencia;

import com.persistencia.utility.HibernateUtil;
import com.pojos.Cliente;
import com.pojos.DireccionCliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Usuario on 24/11/2016.
 */
public class ClienteDAO {

    public List<Cliente> getClientes(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Cliente> lista = session.createCriteria(Cliente.class).list();
        return lista;
    }

    public Cliente getCliente(int idCliente){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Cliente cliente = (Cliente) session.get(Cliente.class, idCliente);
        return cliente;
    }

    public void guardaCliente(Cliente cliente) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            if (cliente.getIdCliente() == 0) {
                session.save(cliente.getDireccion());
                session.save(cliente);
            } else {
                Cliente clienteTmp = (Cliente) session.merge(cliente);
                DireccionCliente dirTmp  = (DireccionCliente) session.merge(cliente.getDireccion());
                session.update(dirTmp);
                session.update(clienteTmp);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ProveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }

    public void eliminarCliente(Cliente cliente) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            Cliente clientetmp = (Cliente) session.merge(cliente);
            session.delete(clientetmp);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ProveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }

}
