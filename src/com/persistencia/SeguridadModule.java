/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.persistencia;

import com.seguridad.ObjPickList;
import com.util.Conexion;
import com.pojos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Usuario
 */
public class SeguridadModule {

    public boolean puedeCancelarVentaCredito(int id) throws Exception {
        Connection cn = null;
        PreparedStatement pState = null;
        ResultSet rSet = null;
        try {
            cn = Conexion.obtenerConexion();
            String query = "select CANCELA_CREDITO from SEGURIDAD_ACCESO_PANTALLA where ID_USUARIO=?";
            pState = cn.prepareStatement(query);
            pState.setLong(1, id);
            rSet = pState.executeQuery();
            while (rSet.next()) {
                return rSet.getBoolean("CANCELA_CREDITO");
            }
            rSet.close();
            pState.close();
            cn.close();

        } catch (SQLException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
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
        return false;
    }

    public boolean puedeCambiarPrecio(int id) throws Exception {
        Connection cn = null;
        PreparedStatement pState = null;
        ResultSet rSet = null;
        try {
            cn = Conexion.obtenerConexion();
            String query = "select VENTAS_PRECIO_LIBRE from SEGURIDAD_ACCESO_PANTALLAS_ where ID_USUARIO=" + id;
            pState = cn.prepareStatement(query);
            rSet = pState.executeQuery();
            while (rSet.next()) {
                // p.setDescripcion(rSet.getString("DESCRIPCION"));
                return rSet.getBoolean("VENTAS_PRECIO_LIBRE");
            }
            rSet.close();
            pState.close();
            cn.close();

        } catch (SQLException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
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
        return false;
    }

    public List<ObjPickList> getCurrentUsserAccess(long idAlmacen, String userName) throws Exception {
        List<ObjPickList> lista = new ArrayList<ObjPickList>();
        Connection cn = null;
        PreparedStatement pState = null;
        ResultSet rSet = null;
        try {
            cn = Conexion.obtenerConexion();
            StringBuilder query = new StringBuilder();
            query.append("select sau.acceso,sa.nombreAcceso from SEGURIDAD_ACCESOUSUARIO sau ");
            query.append("join SEGURIDAD_ACCESO sa on sa.acceso=sau.acceso where usuario=? and sau.id_almacen=?");

            pState = cn.prepareStatement(query.toString());
            pState.setString(1, userName);
            pState.setLong(2, idAlmacen);
            rSet = pState.executeQuery();
            while (rSet.next()) {
                ObjPickList perfilUsu = new ObjPickList();
                perfilUsu.setNombre(rSet.getString("nombreAcceso"));
                perfilUsu.setId(rSet.getString("acceso"));
                lista.add(perfilUsu);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
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

    public List<ObjPickList> getOtherAccess(long idAlmacen, String userName) throws Exception {
        List<ObjPickList> lista = new ArrayList<ObjPickList>();
        Connection cn = null;
        PreparedStatement pState = null;
        ResultSet rSet = null;
        try {
            cn = Conexion.obtenerConexion();

            String query = "select * from SEGURIDAD_ACCESO where acceso not in (select acceso from SEGURIDAD_ACCESOUSUARIO "
                    + " where usuario=? and id_almacen=?)";

            pState = cn.prepareStatement(query);
            pState.setString(1, userName);
            pState.setLong(2, idAlmacen);
            rSet = pState.executeQuery();
            while (rSet.next()) {
                ObjPickList perfilUsu = new ObjPickList();
                perfilUsu.setNombre(rSet.getString("nombreAcceso"));
                perfilUsu.setId(rSet.getString("acceso"));
                lista.add(perfilUsu);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
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

    public void guardaAccesosAsignado(Usuario usuarioAcceso, DualListModel<ObjPickList> accesoUsuario, long almacenUsu) throws Exception {
        Connection cn = null;
        PreparedStatement pState = null;
        try {
            cn = Conexion.obtenerConexion();
            cn.setAutoCommit(false);
            String sqlQuery = "Delete from SEGURIDAD_ACCESOUSUARIO where id_usuario=" + usuarioAcceso.getIdUsuario() ;
            pState = cn.prepareStatement(sqlQuery);
            pState.executeUpdate();

            for (ObjPickList df : accesoUsuario.getTarget()) {
                pState = cn.prepareStatement("Insert Into SEGURIDAD_ACCESOUSUARIO (id_usuario,acceso) values(?,?)");
                pState.setInt(1, usuarioAcceso.getIdUsuario());
                pState.setString(2, df.getId());
                pState.executeUpdate();
            }
            cn.commit();
        } catch (SQLException ex) {
            if (cn != null) {
                cn.rollback();
            }
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (pState != null) {
                pState.close();
            }
            if (cn != null) {
                cn.close();
            }
        }
    }

    public void guardaAlmacenAsignado(Usuario usuarioAcceso, DualListModel<ObjPickList> almacenAcceso) throws Exception {
        Connection cn = null;
        PreparedStatement pState = null;
        try {
            cn = Conexion.obtenerConexion();
            cn.setAutoCommit(false);

            pState = cn.prepareStatement("Delete from SEGURIDAD_ALMACEN_USUARIO where id_usuario=?");
            pState.setLong(1, usuarioAcceso.getIdUsuario());
            pState.executeUpdate();

            for (ObjPickList df : almacenAcceso.getTarget()) {
                pState = cn.prepareStatement("Insert Into SEGURIDAD_ALMACEN_USUARIO (id_usuario,id_Almacen) "
                        + "values(?,?)");
                pState.setLong(1, usuarioAcceso.getIdUsuario());
                pState.setString(2, df.getId());
                pState.executeUpdate();
            }

            cn.commit();

        } catch (SQLException ex) {
            if (cn != null) {
                cn.rollback();
            }
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (pState != null) {
                pState.close();
            }
            if (cn != null) {
                cn.close();
            }
        }

    }

    public List<ObjPickList> llenaAlmacenesrestantes(long idUsuario) throws Exception {
        List<ObjPickList> lista = new ArrayList<ObjPickList>();
        Connection cn = null;
        PreparedStatement pState = null;
        ResultSet rSet = null;
        try {

            cn = Conexion.obtenerConexion();

            String query = "select * from ALMACEN where ID_Almacen not in (select ID_ALMACEN from " +
                    "SEGURIDAD_ALMACEN_USUARIO where id_usuario=?)";

            pState = cn.prepareStatement(query);
            pState.setLong(1, idUsuario);
            rSet = pState.executeQuery();
            while (rSet.next()) {
                ObjPickList perfilUsu = new ObjPickList();
                perfilUsu.setNombre(rSet.getString("NOMBRE"));
                perfilUsu.setId(rSet.getString("id_Almacen"));
                lista.add(perfilUsu);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
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

    public List<ObjPickList> llenaAlmacenesActuales(long idUsuario) throws Exception {
        List<ObjPickList> lista = new ArrayList<ObjPickList>();
        Connection cn = null;
        PreparedStatement pState = null;
        ResultSet rSet = null;
        try {

            cn = Conexion.obtenerConexion();

            String query = "select cus.ID_Almacen,cs.NOMBRE from SEGURIDAD_ALMACEN_USUARIO  cus "
                    + "join ALMACEN cs on cus.ID_ALMACEN= cs.ID_ALMACEN "
                    + "where cus.id_usuario=?";

            pState = cn.prepareStatement(query);
            pState.setLong(1, idUsuario);
            rSet = pState.executeQuery();
            while (rSet.next()) {
                ObjPickList perfilUsu = new ObjPickList();
                perfilUsu.setNombre(rSet.getString("NOMBRE"));
                perfilUsu.setId(rSet.getString("id_Almacen"));
                lista.add(perfilUsu);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SeguridadModule.class.getName()).log(Level.SEVERE, null, ex);
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
