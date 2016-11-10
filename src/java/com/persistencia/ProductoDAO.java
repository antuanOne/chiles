/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.persistencia;

import com.pojos.Producto;
import com.tsi.persistence.dao.utility.HibernateUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;

/**
 *
 * @author Usuario
 */
public class ProductoDAO {


    public List<Producto> getProductos() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Producto> lista = session.createCriteria(Producto.class).list();
        session.close();
        return lista;
    }

    public Producto getProductoById(long idProd) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Producto producto = (Producto) session.get(Producto.class, idProd);
        return producto;

    }

    public Producto getProductoByCcodigo(String idCod) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("From PRODUCTO where CODIGO = :param1");
        query.setParameter("param1", idCod);
        List<Producto> tmpList = query.list();
        if (tmpList.isEmpty()) {
            return null;
        } else {
            return tmpList.get(0);
        }
    }

    public void guardar(Producto prod) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            if (prod.getIdProducto()== 0) {
                session.save(prod);
            } else {
                Producto prodTmp = (Producto) session.merge(prod);
                session.update(prodTmp);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }

    public void eliminar(Producto prod) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            Producto prodTmp = (Producto) session.merge(prod);
            session.delete(prodTmp);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }



}
