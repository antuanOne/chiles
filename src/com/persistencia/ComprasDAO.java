/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.persistencia;

import com.app.DateUtils;
import com.persistencia.utility.HibernateUtil;
import com.pojos.ConceptosExtra;
import com.pojos.DetalleCompra;
import com.pojos.MasterCompra;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Usuario
 */
public class ComprasDAO {
    public MasterCompra getCompraById(Long id) throws Exception {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        MasterCompra compra = (MasterCompra) session.get(MasterCompra.class, id);
        return compra;

    }

    public MasterCompra insertMasterCompra(MasterCompra master) throws Exception {
        master.setFechaAlta(new Date());
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(master);
            for (ConceptosExtra ce : master.getListaExtra()) {
                ce.setCompra(master);
                session.save(ce);
            }
            for (DetalleCompra dt : master.getListDetalle()) {
                dt.setCompra(master);
                session.save(dt);

                InventarioDAO.aumentaInventario(session, master.getAlmacen().getIdAlmacen(),
                        dt.getProducto().getIdProducto(), dt.getCantidad());

            }

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ComprasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
        return master;
    }

    public void cancelaCompra(MasterCompra compra) throws Exception {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            MasterCompra compraTemp = (MasterCompra) session.get(MasterCompra.class, compra.getIdCompra());
            compraTemp.setEstatus("C");
            session.update(compraTemp);
            for (DetalleCompra dt : compraTemp.getListDetalle()) {
                session.save(dt);
                InventarioDAO.reduceInventario(session, compraTemp.getAlmacen().getIdAlmacen(),
                        dt.getProducto().getIdProducto(), dt.getCantidad());

            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ComprasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
    }

    public List<MasterCompra> trarComprasPorAlmacenFechas(Date inicio, Date fin, long almacen) throws Exception {

        String fechaInicio = DateUtils.getTextFecha(inicio, "yyyyMMdd") + "  00:00:00:000";
        String fechaFin = DateUtils.getTextFecha(fin, "yyyyMMdd") + " 23:59:59:999";

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("From COMPRA_MASTER where FECHA_ALTA between :param1 and :param2");
        query.setParameter("param1", fechaInicio);
        query.setParameter("param2", fechaFin);
        List<MasterCompra> tmpList = query.list();
        session.close();
        return tmpList;

    }
}

