/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antuan.yanez
 */
public class DateUtils {

    private DateUtils() {
    }

    public static String getTextFecha(Date fecha, String formato) {
        String texto = "";
        SimpleDateFormat formatter = new SimpleDateFormat(formato);
        texto = formatter.format(fecha);
        return texto;
    }

    public static String getTextFecha(String formato) {
        String fecha = "";
        Calendar c = Calendar.getInstance();
        fecha = getTextFecha(c.getTime(), formato);
        return fecha;
    }

    public static Date trarfecha() {
        Date dateWithoutTime = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateWithoutTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateWithoutTime;
    }

    public static Date trarfechaSimple(Date fecha) {
        Date dateWithoutTime = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateWithoutTime = sdf.parse(sdf.format(fecha));
        } catch (ParseException ex) {
            Logger.getLogger(DateUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dateWithoutTime;
    }
}
