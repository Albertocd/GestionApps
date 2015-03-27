
package gestionclases.presentation.controller;

import gestionclases.business.service.DiaSemanaService;
import gestionclases.persistence.entity.DiaSemana;
import gestionclases.presentation.util.JsfUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author alberto
 */
@ManagedBean
@SessionScoped
public class DiaSemanaController {

    private static  DiaSemanaController    instancia;
    private final   DiaSemanaService       servicio        = new DiaSemanaService();
    private         List<DiaSemana>        lista           = null;

    
    /**
     * Creates a new instance of DiaSemanaController
     */
    public DiaSemanaController() {
        instancia = this;
    }

    public List<DiaSemana> getLista() {
        if (lista == null || lista.isEmpty()) {
            try {
                lista = servicio.listar();
            } catch (Exception e) {
                JsfUtil.mensajeError(e.getMessage());
            }
        }
        
        return lista;
    }
    
    
    @FacesConverter(forClass = DiaSemana.class, value = "diaSemanaConverter")
    public static class DiaSemanaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            Object retorno = null;
            Integer key;
            
            if (value != null && value.length() != 0) {
                try {
                    key = Integer.parseInt(value);
                    
                    // Recorrer la lista para obtener el elemento cuya clave coincide con la recibida
                    for (DiaSemana dia : instancia.getLista()) {
                        if (dia.getId().equals(key))
                            retorno = dia;                
                    }
                } catch (NumberFormatException e) {
                    retorno = null;
                }
            }            
            
            return retorno;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object object) {
            String retorno = null;
            
            if (object != null && ((DiaSemana)object).getId() != null) {
                retorno = ((DiaSemana)object).getId().toString();
            }
            
            return retorno;
        }        
    }  
}
