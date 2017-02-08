package com.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


import com.app.GenericBean;
import com.util.Conexion;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean extends GenericBean implements Serializable {

    private MenuModel model;
    private List<Menu> listaMenu;

    public MenuBean() {

        model = new DefaultMenuModel();
        List<String> accesos = new ArrayList<String>();
        listaMenu = new ArrayList<Menu>();
        traeAccesos();
        Iterator iter = listaMenu.iterator();
        Menu menuAcc;
        while (iter.hasNext()) {
            menuAcc = (Menu) iter.next();
            // buscamos la palabra que necesitamos y la asignamos para que aparesca en el idioma correcto del usuario
            String aplicacion = menuAcc.getAplicacion();
            DefaultSubMenu sub = new DefaultSubMenu();
            sub.setLabel(aplicacion);
            sub.setStyle("font-size: 12px !important;");
            for (Acceso acc : menuAcc.getAccesos()) {
                DefaultMenuItem item = new DefaultMenuItem();
                item.setValue(acc.getModulo());
                item.setUrl(acc.getUrl());
                item.setStyle("font-size: 11px !important;");
                sub.addElement(item);
            }
            model.addElement(sub);
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accesos", accesos);
    }

    private void traeAccesos() {

        try {

            Connection cn = Conexion.obtenerConexion();

            String query = "select distinct sApl.Aplicacion,sApl.NombreAplicacion,sApl.Orden from Seguridad_ACCESOUSUARIO sau "
                    + " join SEGURIDAD_ACCESO sa on sa.acceso=sau.acceso"
                    + " join SEGURIDAD_MODULOAPLICACION sma  on sma.modulo=sa.modulo"
                    + " join SEGURIDAD_APLICACION sApl on sApl.Aplicacion=sma.Aplicacion"
                    + " where sau.id_usuario='" + getUsuario().getIdUsuario() + "'"
                    + " order by sApl.Orden";

            PreparedStatement pState = cn.prepareStatement(query);
            ResultSet rSet = pState.executeQuery();
            while (rSet.next()) {
                Menu menuItem = new Menu();
                List<Acceso> listAcc = new ArrayList<Acceso>();
                menuItem.setAplicacion(rSet.getString("NombreAplicacion"));
                menuItem.setNoAplicacion(rSet.getString("aplicacion"));

                //Se toman los acceso por Usuario


                Connection cnurls = Conexion.obtenerConexion();

                /*   String queryusuario = "select nombreModulo,URLModulo,acceso,visibleMenu "
                 + "from S_MODULOAPLICACION ma join S_ACCESO acc on acc.modulo=ma.modulo "
                 + "where acc.modulo  in( select modulo from S_ACCESO where acceso in (select acceso from S_ACCESOUSUARIO "
                 + "where usuario='" + usuario + "')) and Aplicacion=" + menuItem.getNoAplicacion() + " order by orden";*/

                String queryusuario = "select smasb.nombreModulo,smasb.URLModulo,sausb.acceso,smasb.visibleMenu from SEGURIDAD_ACCESOUSUARIO sausb "
                        + " join SEGURIDAD_ACCESO sasb on sausb.acceso=sasb.acceso"
                        + " join SEGURIDAD_MODULOAPLICACION smasb on smasb.modulo= sasb.modulo"
                        + " join SEGURIDAD_APLICACION sab on sab.Aplicacion= smasb.Aplicacion"
                        + " where sausb.id_usuario='" + getUsuario().getIdUsuario() + "' and sab.Aplicacion=" + menuItem.getNoAplicacion();

                PreparedStatement pStateurl = cn.prepareStatement(queryusuario);
                ResultSet rSeturl = pStateurl.executeQuery();
                while (rSeturl.next()) {
                    Acceso acc = new Acceso();
                    acc.setAcceso(rSeturl.getString("acceso"));
                    acc.setModulo(rSeturl.getString("nombreModulo"));
                    acc.setUrl(rSeturl.getString("URLModulo"));
                    acc.setVisible(rSeturl.getBoolean("visibleMenu"));
                    listAcc = agregaAcceso(listAcc, acc);
                }
                rSeturl.close();
                pStateurl.close();
                cnurls.close();
                //Se agrega la lista a la aplicacion
                menuItem.setAccesos(listAcc);
                listaMenu.add(menuItem);

            }

            rSet.close();
            pState.close();
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Acceso> agregaAcceso(List<Acceso> listAccesos, Acceso acceso) {

        Iterator iter = listAccesos.iterator();
        Acceso ac;

        boolean existe = false;
        while (iter.hasNext()) {
            ac = (Acceso) iter.next();
            if (ac.getAcceso().trim().equals(acceso.getAcceso().trim())) {
                existe = true;
            } else {
            }
        }
        if (!existe) {
            listAccesos.add(acceso);
        }
        return listAccesos;

    }

    /**
     * @return the model
     */
    public MenuModel getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MenuModel model) {
        this.model = model;
    }



    /**
     * @return the listaMenu
     */
    public List<Menu> getListaMenu() {
        return listaMenu;
    }

    /**
     * @param listaMenu the listaMenu to set
     */
    public void setListaMenu(List<Menu> listaMenu) {
        this.listaMenu = listaMenu;
    }
}

