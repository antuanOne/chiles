package com.persistencia;

import com.app.DateUtils;
import com.persistencia.utility.HibernateUtil;
import com.pojos.DetalleVenta;
import com.pojos.MasterVenta;
import com.util.Conexion;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public MasterVenta insertMasterVenta(MasterVenta master) {
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
                InventarioDAO.aumentaInventario(session, ventaTemp.getAlmacen().getIdAlmacen(),
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

    public List<MasterVenta> trarVentasPorAlmacenFechas1(Date inicio, Date fin, long almacen) throws Exception {
        List<MasterVenta> lista = new ArrayList<MasterVenta>();
        Connection cn = null;
        PreparedStatement pState = null;
        ResultSet rSet = null;
        try {

            cn = Conexion.obtenerConexion();

            String query = "select * from VENTA_MASTER where FECHA_ALTA between ? and ?";

            String fechaInicio = DateUtils.getTextFecha(inicio, "yyyyMMdd") + "  00:00:00:000";
            String fechaFin = DateUtils.getTextFecha(fin, "yyyyMMdd") + " 23:59:59:999";

            pState = cn.prepareStatement(query);
            pState.setString(1, fechaInicio);
            pState.setString(2, fechaFin);
            rSet = pState.executeQuery();
            while (rSet.next()) {
                MasterVenta venta = new MasterVenta();
                venta.setEstatus(rSet.getString("ESTATUS"));
                String fecha1 = rSet.getString("FECHA_ALTA_SYS");
                String fecha2 = rSet.getString("FECHA_ALTA");
                venta.setFechaAltaSys(rSet.getDate("FECHA_ALTA_SYS"));
                venta.setFechaAlta(rSet.getDate("FECHA_ALTA"));
                venta.getAlmacen().setIdAlmacen(rSet.getInt("ID_ALMACEN"));
                venta.getCliente().setIdCliente(rSet.getInt("ID_CLIENTE"));
                venta.setIdVenta(rSet.getLong("ID_VENTA"));
                lista.add(venta);
            }

        } catch (SQLException ex) {
            Logger.getLogger(VentasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(VentasDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rSet != null) {
                rSet.close();
            }
            if (pState != null) {
                pState.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
        return lista;

    }


}
