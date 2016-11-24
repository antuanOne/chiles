/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.persistencia;

import com.pojos.Almacen;
import com.persistencia.utility.HibernateUtil;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class AlmacenDAO implements Serializable {

    public Almacen getAlmacen(int idAlmacen) throws Exception {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Almacen almacen = (Almacen) session.get(Almacen.class, idAlmacen);
        return almacen;
    }

    public List<Almacen> getAlmacenes() throws Exception {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Almacen> listaAlmacen = session.createCriteria(Almacen.class).list();
        return listaAlmacen;
    }


     public void guardaAlmacen(Almacen almacen) throws Exception {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            if (almacen.getIdAlmacen()== 0) {
                session.save(almacen);
            } else {
                Almacen clienteTmp = (Almacen) session.merge(almacen);
                session.update(clienteTmp);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(AlmacenDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
    }

    public void eliminar(Almacen almacen) throws Exception {
       SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            Almacen almacentmp = (Almacen) session.merge(almacen);
            session.delete(almacentmp);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(AlmacenDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }

}
