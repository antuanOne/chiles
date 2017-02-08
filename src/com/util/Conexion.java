/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author virtual.user
 */
public class Conexion {

    //static private String cadenaConexion = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=SCItmp";
    final static private String cadenaConexion = "jdbc:sqlserver://127.0.0.1:1433;DatabaseName=laMayor";
    final static private String usuario = "chiles";
    final static private String password = "chiles";

    static public Connection obtenerConexion() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Connection cn = DriverManager.getConnection(cadenaConexion, usuario, password);
        return cn;
    }
}
