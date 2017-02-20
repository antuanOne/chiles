package com.persistencia;

import com.app.DateUtils;
import com.persistencia.utility.HibernateUtil;
import com.pojos.DetalleVenta;
import com.pojos.MasterVenta;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Usuario on 27/12/2016.
 */
public class VentasDAO {

    public MasterVenta getVentaById(Long id) throws Exception {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        MasterVenta venta = (MasterVenta) session.get(MasterVenta.class, id);
        return venta;

    }

    public MasterVenta insertMasterVenta(MasterVenta master){
        master.setFechaAlta(new Date());
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(master);
            for (DetalleVenta dt : master.getListaDetalle()) {
                dt.setVenta(master);
                session.save(dt);

                InventarioDAO.reduceInventario(session, master.getAlmacen().getIdAlmacen(),
                        dt.getProducto().getIdProducto(), dt.getCantidad());

            }

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(VentasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
        return master;
    }

    public void cancelaVenta(MasterVenta venta) throws Exception {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            MasterVenta ventaTemp = (MasterVenta) session.get(MasterVenta.class, venta.getIdVenta());
            ventaTemp.setEstatus("C");
            session.update(ventaTemp);
            for (DetalleVenta dt : ventaTemp.getListaDetalle()) {
                session.save(dt);
                InventarioDAO.reduceInventario(session, ventaTemp.getAlmacen().getIdAlmacen(),
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



    public List<MasterVenta> trarVentasPorAlmacenFechas(Date inicio, Date fin, long almacen) throws Exception {

        String fechaInicio = DateUtils.getTextFecha(inicio, "yyyyMMdd") + "  00:00:00:000";
        String fechaFin = DateUtils.getTextFecha(fin, "yyyyMMdd") + " 23:59:59:999";

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        Query query = session.createQuery("From VENTA_MASTER where FECHA_ALTA between :param1 and :param2");
        query.setParameter("param1", fechaInicio);
        query.setParameter("param2", fechaFin);
        List<MasterVenta> tmpList = query.list();
        session.close();
        return tmpList;

    }
}
