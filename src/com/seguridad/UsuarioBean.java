/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.app.GenericBean;
import com.persistencia.AlmacenDAO;
import com.persistencia.SeguridadModule;
import com.persistencia.UsuarioDAO;
import com.pojos.Almacen;
import com.pojos.Usuario;
import com.util.Conexion;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author antuan.yanez
 */
@ManagedBean(name = "usuarioBean")
@ViewScoped
public class UsuarioBean extends GenericBean implements Serializable {

    final private String msgHeader = "Accesos";
    private List<Almacen> listaAlmacen;
    private List<Almacen> listaAlmacenUsu;
    private List<Usuario> listaUsuarios;
    private Usuario usuarioAcceso;
    private Usuario usuarioGral;
    private Long almacenUsu;
    // listas para ver las sucursales que tiene Acceso el Usuario    
    private DualListModel<ObjPickList> almacenAcceso;
    private List<ObjPickList> listAlmacenrestantes;
    private List<ObjPickList> listAlmacenactuales;
    private DualListModel<ObjPickList> accesoUsuario;
    private List<ObjPickList> listAccesosRestantes;
    private List<ObjPickList> listAccesosActuales;

    final private SeguridadModule seguridadModule = new SeguridadModule();
    final private AlmacenDAO almacenModule = new AlmacenDAO();
    final private UsuarioDAO usuarioModule = new UsuarioDAO();

    public UsuarioBean() {
        try {
            listaAlmacen = almacenModule.getAlmacenes();
            listAlmacenrestantes = new ArrayList<ObjPickList>();
            listAlmacenactuales = new ArrayList<ObjPickList>();
            almacenAcceso = new DualListModel<ObjPickList>();

            listAccesosRestantes = new ArrayList<ObjPickList>();
            listAccesosActuales = new ArrayList<ObjPickList>();
            accesoUsuario = new DualListModel<ObjPickList>();
            listaUsuarios = usuarioModule.getUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void seleccionaAlmacen(ValueChangeEvent e) {
        try {
            usuarioAcceso.setAlmacen(new Almacen((int) Integer.parseInt(e.getNewValue().toString())));
            listaUsuarios = usuarioModule.getUsuarios();
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect(SelectEvent event) {
        try {
            usuarioGral = (Usuario) event.getObject();
            usuarioAcceso = getUsuarioGral();
            listaAlmacenUsu = com.util.InfoUsu.almacenAcceso(usuarioAcceso.getIdUsuario());
            almacenUsu = 0L;

            listAlmacenrestantes = new ArrayList<ObjPickList>();
            listAlmacenactuales = new ArrayList<ObjPickList>();
            almacenAcceso = new DualListModel<ObjPickList>();

            listAlmacenrestantes = seguridadModule.llenaAlmacenesrestantes(usuarioAcceso.getIdUsuario());
            listAlmacenactuales = seguridadModule.llenaAlmacenesActuales(usuarioAcceso.getIdUsuario());
            almacenAcceso = new DualListModel<ObjPickList>(listAlmacenrestantes, listAlmacenactuales);


            listAccesosRestantes = new ArrayList<ObjPickList>();
            listAccesosActuales = new ArrayList<ObjPickList>();
            accesoUsuario = new DualListModel<ObjPickList>();

            // accesos individuales
            listAccesosActuales = llenaAccesosActuales();
            listAccesosRestantes = llenaAccesosRestantes();
            accesoUsuario = new DualListModel<ObjPickList>(listAccesosRestantes, listAccesosActuales);

        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<ObjPickList> llenaAccesosActuales() {
        List<ObjPickList> lista = new ArrayList<ObjPickList>();
        try {


            Connection cn = Conexion.obtenerConexion();

            String query = "select sau.acceso,sa.nombreAcceso from SEGURIDAD_ACCESOUSUARIO sau "
                    + "join SEGURIDAD_ACCESO sa on sa.acceso=sau.acceso "
                    + "where id_usuario=?";

            PreparedStatement pState = cn.prepareStatement(query);
            pState.setInt(1, usuarioAcceso.getIdUsuario());
            ResultSet rSet = pState.executeQuery();
            while (rSet.next()) {
                ObjPickList perfilUsu = new ObjPickList();
                perfilUsu.setNombre(rSet.getString("nombreAcceso"));
                perfilUsu.setId(rSet.getString("acceso"));
                lista.add(perfilUsu);
            }
            rSet.close();
            pState.close();
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }

    private List<ObjPickList> llenaAccesosRestantes() {
        List<ObjPickList> lista = new ArrayList<ObjPickList>();
        try {


            Connection cn = Conexion.obtenerConexion();

            String query = "select * from seguridad_acceso where acceso not in (select acceso from Seguridad_ACCESOUSUARIO "
                    + " where id_usuario=?)";

            PreparedStatement pState = cn.prepareStatement(query);
            pState.setInt(1, usuarioAcceso.getIdUsuario());
            ResultSet rSet = pState.executeQuery();
            while (rSet.next()) {
                ObjPickList perfilUsu = new ObjPickList();
                perfilUsu.setNombre(rSet.getString("nombreAcceso"));
                perfilUsu.setId(rSet.getString("acceso"));
                lista.add(perfilUsu);
            }
            rSet.close();
            pState.close();
            cn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

    }


    public void guardaAccesosEspeciales() {
        try {
            listaAlmacenUsu = com.util.InfoUsu.almacenAcceso(usuarioAcceso.getIdUsuario());
            almacenUsu = 0L;
            showInfoMessage(msgHeader, "El acceso se gurdo con exito");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al guardar el acceso " + ex.getMessage());
        }
    }

    public void guardaAlmacenAsignado() {
        try {
            seguridadModule.guardaAlmacenAsignado(usuarioAcceso, almacenAcceso);
            listaAlmacenUsu = com.util.InfoUsu.almacenAcceso(usuarioAcceso.getIdUsuario());
            almacenUsu = 0L;
            showInfoMessage(msgHeader, "La informacion se ha guardado con exito");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al guardar la informacion " + ex.getMessage());
        }
    }

    public void seleccionaAlmacenAcceso(ValueChangeEvent e) {
        try {
            almacenUsu = ((Long) Long.parseLong(e.getNewValue().toString()));
            // llenaUsuarios(almacen);

            listAccesosRestantes = new ArrayList<ObjPickList>();
            listAccesosActuales = new ArrayList<ObjPickList>();
            accesoUsuario = new DualListModel<ObjPickList>();

            // accesos individuales
            listAccesosActuales = seguridadModule.getCurrentUsserAccess(almacenUsu, getUsuario().getUser_name());
            listAccesosRestantes = seguridadModule.getOtherAccess(almacenUsu, getUsuario().getUser_name());
            accesoUsuario = new DualListModel<ObjPickList>(listAccesosRestantes, listAccesosActuales);
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al cargar la informacion del usuario :" + ex.getMessage());
        }
    }

    public void guardaAccesosAsignado() {
        try {
            seguridadModule.guardaAccesosAsignado(usuarioAcceso, accesoUsuario, almacenUsu);
            showInfoMessage(msgHeader, "La informacion se a guardado con exito");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            showErrorMessage(msgHeader, "Error al guardar los accesos " + ex.getMessage());
        }
    }

    /**
     * @return the listaAlmacen
     */
    public List<com.pojos.Almacen> getListaAlmacen() {
        return listaAlmacen;
    }

    /**
     * @param listaAlmacen the listaAlmacen to set
     */
    public void setListaAlmacen(List<com.pojos.Almacen> listaAlmacen) {
        this.listaAlmacen = listaAlmacen;
    }

    /**
     * @return the listaUsuarios
     */
    public List<com.pojos.Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    /**
     * @param listaUsuarios the listaUsuarios to set
     */
    public void setListaUsuarios(List<com.pojos.Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    /**
     * @return the usuarioGral
     */
    public com.pojos.Usuario getUsuarioGral() {
        return usuarioGral;
    }

    /**
     * @param usuarioGral the usuarioGral to set
     */
    public void setUsuarioGral(com.pojos.Usuario usuarioGral) {
        this.usuarioGral = usuarioGral;
    }

    /**
     * @return the almacenAcceso
     */
    public DualListModel<ObjPickList> getAlmacenAcceso() {
        return almacenAcceso;
    }

    /**
     * @param almacenAcceso the almacenAcceso to set
     */
    public void setAlmacenAcceso(DualListModel<ObjPickList> almacenAcceso) {
        this.almacenAcceso = almacenAcceso;
    }

    /**
     * @return the listaAlmacenUsu
     */
    public List<com.pojos.Almacen> getListaAlmacenUsu() {
        return listaAlmacenUsu;
    }

    /**
     * @param listaAlmacenUsu the listaAlmacenUsu to set
     */
    public void setListaAlmacenUsu(List<com.pojos.Almacen> listaAlmacenUsu) {
        this.listaAlmacenUsu = listaAlmacenUsu;
    }

    /**
     * @return the almacenUsu
     */
    public Long getAlmacenUsu() {
        return almacenUsu;
    }

    /**
     * @param almacenUsu the almacenUsu to set
     */
    public void setAlmacenUsu(Long almacenUsu) {
        this.almacenUsu = almacenUsu;
    }

    /**
     * @return the accesoUsuario
     */
    public DualListModel<ObjPickList> getAccesoUsuario() {
        return accesoUsuario;
    }

    /**
     * @param accesoUsuario the accesoUsuario to set
     */
    public void setAccesoUsuario(DualListModel<ObjPickList> accesoUsuario) {
        this.accesoUsuario = accesoUsuario;
    }

    /**
     * @return the listAccesosRestantes
     */
    public List<ObjPickList> getListAccesosRestantes() {
        return listAccesosRestantes;
    }

    /**
     * @param listAccesosRestantes the listAccesosRestantes to set
     */
    public void setListAccesosRestantes(List<ObjPickList> listAccesosRestantes) {
        this.listAccesosRestantes = listAccesosRestantes;
    }

    /**
     * @return the listAccesosActuales
     */
    public List<ObjPickList> getListAccesosActuales() {
        return listAccesosActuales;
    }

    /**
     * @param listAccesosActuales the listAccesosActuales to set
     */
    public void setListAccesosActuales(List<ObjPickList> listAccesosActuales) {
        this.listAccesosActuales = listAccesosActuales;
    }

    /**
     * @return the usuarioAcceso
     */
    public Usuario getUsuarioAcceso() {
        return usuarioAcceso;
    }

    /**
     * @param usuarioAcceso the usuarioAcceso to set
     */
    public void setUsuarioAcceso(Usuario usuarioAcceso) {
        this.usuarioAcceso = usuarioAcceso;
    }
}
