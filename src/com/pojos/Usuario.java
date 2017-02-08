/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pojos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "C_USUARIO")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private int idUsuario;
    @Column(name = "USER_NAME")
    private String user_name;
    @Column(name = "PASSWORD")
    private String pass;
    @OneToOne
    @JoinColumn(name = "ID_ALMACEN")
    private Almacen almacen;
    @Column(name = "FECHA_ALTA")
    private String fecha_Alta;
   // private String fecha_baja;
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "ACTIVO")
    private boolean activo;
//    @OneToOne
//    @JoinColumn(name = "ID_IMPRESORA")
//    private Impresora impresora;
//    @OneToOne
//    @JoinColumn(name = "ID_EMPLEADO")
//    private Empleado empleado;
    //private String correo;
    //private int intentos;

    public Usuario() {
        this.idUsuario = 0;
        almacen = new Almacen();
//        empleado = new Empleado();
//        impresora = new Impresora();
    }

    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Usuario) {
            Usuario a = (Usuario) obj;
            if (a.getIdUsuario() == this.getIdUsuario()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = (getIdUsuario() * 17);
        return hash;
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the user_name
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the almacen
     */
    public Almacen getAlmacen() {
        return almacen;
    }

    /**
     * @param almacen the almacen to set
     */
    public void setAlmacen(Almacen almacen) {
        this.almacen = almacen;
    }

    /**
     * @return the fecha_Alta
     */
    public String getFecha_Alta() {
        return fecha_Alta;
    }

    /**
     * @param fecha_Alta the fecha_Alta to set
     */
    public void setFecha_Alta(String fecha_Alta) {
        this.fecha_Alta = fecha_Alta;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
