package com.pojos;

import java.util.Date;

/**
 * Created by Usuario on 19/11/2016.
 */
public class ObjetoBusqueda {
    private int almacen ;
    private Date fecha1;
    private Date fecha2;

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public Date getFecha2() {
        return fecha2;
    }

    public void setFecha2(Date fecha2) {
        this.fecha2 = fecha2;
    }
}
