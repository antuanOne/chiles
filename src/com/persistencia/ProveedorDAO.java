/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.persistencia;

import com.pojos.Proveedor;
import com.persistencia.utility.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Usuario
 */
public class ProveedorDAO implements Serializable{
  
    public List<Proveedor> getProveedores()  {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Proveedor> listaProveedores = session.createCriteria(Proveedor.class).list();
        session.close();
        return listaProveedores;
    }

    public void guardaProveedor(Proveedor prov) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            if (prov.getIdProveedor() == 0) {
                session.save(prov.getContacto());
                session.save(prov.getDireccion());
                session.save(prov);
            } else {
                Proveedor provTmp = (Proveedor) session.merge(prov);
                session.update(provTmp);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ProveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }

    public void eliminarProveedor(Proveedor prov) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            Proveedor provTmp = (Proveedor) session.merge(prov);
            session.delete(provTmp);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ProveedorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }

    public Proveedor getProveedor(long idProveedor)  {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Proveedor proveedor = (Proveedor) session.get(Proveedor.class, idProveedor);
        return proveedor;
    }

}
