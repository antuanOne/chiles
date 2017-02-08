package com.persistencia;;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.Serializable;

import com.persistencia.utility.HibernateUtil;
import com.pojos.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author virtual.user
 */
public class UsuarioDAO implements Serializable {

    public Usuario getUsuario(int idUsuario) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Usuario usuario = (Usuario) session.get(Usuario.class, idUsuario);
        session.close();
        return usuario;
    }

    public Usuario getUsuario(String userName) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("From C_USUARIO where USER_NAME =:param1");
        query.setParameter("param1", userName);
        List<Usuario> tmpList = query.list();
        if (tmpList.isEmpty()) {
            return new Usuario();
        }
        return tmpList.get(0);

    }

    public Boolean isUsuarioValido(String usuarioName, String pass) {
        Usuario usuario = getUsuario(usuarioName);
        if (usuario.getIdUsuario() == 0) {
            return false;
        }
        return pass.equals(usuario.getPass());

    }

    public List<Usuario> getUsuarios() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Usuario> listaUsuarios = session.createCriteria(Usuario.class).list();
        session.close();
        return listaUsuarios;

    }

    public void guardar(Usuario usuario) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            if (usuario.getIdUsuario() == 0) {
                usuario.setFecha_Alta(com.util.DateUtils.getTextFecha("yyyyMMdd"));
                session.save(usuario);
            } else {
                Usuario usuarioTmp = (Usuario) session.merge(usuario);
                session.update(usuarioTmp);
            }
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }

    }

    public void eliminar(Usuario usuario) throws Exception {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try {

            session.beginTransaction();
            Usuario clienteTmp = (Usuario) session.merge(usuario);
            session.delete(clienteTmp);

            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            session.close();
        }
    }

}
