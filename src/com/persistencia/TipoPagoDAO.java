package com.persistencia;

import com.persistencia.utility.HibernateUtil;
import com.pojos.TipoPago;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Usuario on 13/03/2017.
 */
public class TipoPagoDAO {
    public List<TipoPago> getTipoPagos(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<TipoPago> lista = session.createCriteria(TipoPago.class).list();
        session.close();
        return lista;
    }

    public TipoPago getTipoPago(String codigo){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        TipoPago tipoPago = (TipoPago) session.get(TipoPago.class, codigo);
        return tipoPago;
    }
}
