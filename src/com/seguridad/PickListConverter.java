/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

 
@FacesConverter("pickListConverter")
public class PickListConverter implements Converter {
 
 
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        return getObjectFromUIPickListComponent(component,value);
    }
 
    
    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string="";
        if(object == null){
            string="";
        }else{
            try{
                string = String.valueOf(((ObjPickList)object).getId());
            }catch(ClassCastException cce){
                throw new ConverterException();
            }
        }
        return string;
    }
 
    @SuppressWarnings("unchecked")
    private ObjPickList getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<ObjPickList> dualList;
        try{
            dualList = (DualListModel<ObjPickList>) ((PickList)component).getValue();
            ObjPickList team = getObjectFromList(dualList.getSource(),value);
            if(team==null){
                team = getObjectFromList(dualList.getTarget(),value);
            }
             
            return team;
        }catch(ClassCastException cce){
            throw new ConverterException();
        }catch(NumberFormatException nfe){
            throw new ConverterException();
        }
    }
 
    private ObjPickList getObjectFromList(final List<?> list, final String identifier) {
        for(final Object object:list){
            final ObjPickList team = (ObjPickList) object;
            if(team.getId().equals(identifier)){
                return team;
            }
        }
        return null;
    }
}