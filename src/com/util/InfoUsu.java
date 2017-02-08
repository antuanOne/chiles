/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.app.GenericBean;
import com.pojos.Almacen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antuan.yanez
 */
public class InfoUsu extends GenericBean {

    public static List<Almacen> almacenAcceso(int usu) {
        List<Almacen> listaAlmacen = new ArrayList<Almacen>();
        try {
            
            Connection cn = Conexion.obtenerConexion();

            String query = "select c.ID_ALMACEN,c.NOMBRE from SEGURIDAD_ALMACEN_USUARIO s "
                    + "join ALMACEN c on c.ID_ALMACEN=s.ID_ALMACEN "
                    + "where ID_USUARIO=" + usu;

            PreparedStatement pState = cn.prepareStatement(query);
            ResultSet rSet = pState.executeQuery();
            Almacen a = null;
            while (rSet.next()) {
                a = new Almacen(rSet.getInt("ID_ALMACEN"));
                a.setNombreAlmacen(rSet.getString("NOMBRE"));
                listaAlmacen.add(a);
            }
            rSet.close();
            pState.close();
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(InfoUsu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoUsu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaAlmacen;

    }

    public static List<Almacen> almacenAccesoPantalla(Long usu, Long acceso) {
        List<Almacen> listaAlmacen = new ArrayList<Almacen>();
        try {
            
            Connection cn = Conexion.obtenerConexion();

            String query = "select c.ID_ALMACEN,c.NOMBRE from SEGURIDAD_ALMACEN_USUARIO s "
                    + "join ALMACEN c on c.ID_ALMACEN=s.ID_ALMACEN "
                    + "where ID_USUARIO=" + usu + " and acceso=" + acceso;

            PreparedStatement pState = cn.prepareStatement(query);
            ResultSet rSet = pState.executeQuery();
            while (rSet.next()) {
                Almacen a = new Almacen(rSet.getInt("ID_ALMACEN"));
                a.setNombreAlmacen(rSet.getString("NOMBRE"));
                listaAlmacen.add(a);
            }
            rSet.close();
            pState.close();
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(InfoUsu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InfoUsu.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaAlmacen;

    }

    
}
