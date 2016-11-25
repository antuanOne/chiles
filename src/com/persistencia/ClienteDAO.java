package com.persistencia;

import com.persistencia.utility.HibernateUtil;
import com.pojos.Cliente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Usuario on 24/11/2016.
 */
public class ClienteDAO {

    public List<Cliente> getClientes(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Cliente> lista = session.createCriteria(Cliente.class).list();
        return null;
    }
}
