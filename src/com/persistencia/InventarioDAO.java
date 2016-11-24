package com.persistencia;

import java.util.List;

import com.persistencia.utility.HibernateUtil;
import com.pojos.InventoryMaster;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class InventarioDAO {
    public static void reduceInventario(Session session, long idAlmacen, long idProducto, int cantidad) {
        Query query = session.createQuery("From INVENTARIO_MASTER where ID_PRODUCTO = :param1 and ID_ALMACEN =:param2");
        query.setParameter("param1", idProducto);
        query.setParameter("param2", idAlmacen);
        List<InventoryMaster> tmpList = query.list();
        InventoryMaster inventory = tmpList.get(0);
        inventory.setCantidad(inventory.getCantidad() - cantidad);
        session.saveOrUpdate(inventory);
    }

    public static void aumentaInventario(Session session, int idAlmacen, long idProducto, int cantidad) {
        Query query = session.createQuery("From INVENTARIO_MASTER where ID_PRODUCTO = :param1 and ID_ALMACEN =:param2");
        query.setParameter("param1", idProducto);
        query.setParameter("param2", idAlmacen);
        List<InventoryMaster> tmpList = query.list();
        InventoryMaster inventory = new InventoryMaster();
        if(!tmpList.isEmpty()) {
            inventory = tmpList.get(0);
            inventory.setCantidad(inventory.getCantidad() + cantidad);
        }else{
            inventory.getAlmacen().setIdAlmacen(idAlmacen);
            inventory.getProducto().setIdProducto(idProducto);
            inventory.setCantidad(cantidad);
        }
        session.saveOrUpdate(inventory);
    }

    public static int getExistenciaProducto(long idAlmacen, long idProducto) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("From INVENTARIO_MASTER where ID_PRODUCTO = :param1 and ID_ALMACEN =:param2");
        query.setParameter("param1", idProducto);
        query.setParameter("param2", idAlmacen);
        List<InventoryMaster> tmpList = query.list();
        if(tmpList.isEmpty()){
            return 0;
        }
        InventoryMaster inventory = tmpList.get(0);
        return inventory.getCantidad();
    }

    public List<InventoryMaster> getInventoryById(long idProducto) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("From INVENTARIO_MASTER where ID_PRODUCTO = :param1");
        query.setParameter("param1", idProducto);
        List<InventoryMaster> tmpList = query.list();
        return tmpList;
    }

    public List<InventoryMaster> getInventoryByDesc(String descripcion) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("From INVENTARIO_MASTER    where DESCRIPCION = :param1");
        query.setParameter("param1", descripcion);
        List<InventoryMaster> tmpList = query.list();
        return tmpList;
    }
}
