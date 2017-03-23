package com.persistencia;

import com.persistencia.utility.HibernateUtil;
import com.pojos.MasterCompra;
import com.pojos.PagoCompra;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Usuario on 20/02/2017.
 */
public class CuentasPorPagarDAO {

    public List<MasterCompra> getComprasPorPagar(int almacen) throws Exception {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("From COMPRA_MASTER where credito = :param1");
        query.setParameter("param1", true);
        List<MasterCompra> tmpList = query.list();
        session.close();
        for(MasterCompra row :tmpList ){
            row.calculaTotales();
        }
        return tmpList;
    }

    public List<PagoCompra> getPagosCompra(long idCompra){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("From PAGOS_COMPRAS where ID_COMPRA = :param1 and ESTATUS = :param2");
        query.setParameter("param1", idCompra);
        query.setParameter("param2", "A");
        List<PagoCompra> tmpList = query.list();
        session.close();
        return tmpList;
    }

    public void savePago(PagoCompra pago){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            pago.setEstatus("A");
            pago.setFechaAlta(new Date());
                session.save(pago);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(CuentasPorPagarDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
    }


    public void eliminaPago(PagoCompra pago){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            PagoCompra pagoTmp = (PagoCompra) session.merge(pago);
            pagoTmp.setEstatus("D");
            session.update(pagoTmp);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(CuentasPorPagarDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
    }

}
