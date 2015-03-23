
package reservashotel.presentation.controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import reservashotel.business.service.ProvinciaService;
import reservashotel.business.vo.ProvinciaFiltro;
import reservashotel.persistence.entities.Provincia;
import reservashotel.presentation.util.JsfUtil;


/**
 * @author alberto
 * Controller para las funcionalidades de las provincias.
 */
@ManagedBean
@SessionScoped
public class ProvinciaController {

    private static  ProvinciaController    instancia;
    
    private final   ProvinciaService  servicio        = new ProvinciaService();
    private         List<Provincia>   lista           = null;
    private         Provincia         provinciaActual = new Provincia();
    
    /**
     * Creates a new instance of ProvinciaController
     */
    public ProvinciaController() {
        instancia = this;
    }

    public Provincia getProvinciaActual() {
        return provinciaActual;
    }

    public void setProvinciaActual(Provincia provinciaActual) {
        this.provinciaActual = provinciaActual;
    }
    
    public List<Provincia> getLista() {
        List<Provincia> lista = null;
        
        try {
            lista = servicio.listar();
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }

        return lista;
    }
    

    public List<Provincia> getListaFiltro() {
        try {
            ProvinciaFiltro filtro = new ProvinciaFiltro();

            lista = servicio.listar(filtro);
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }

        return lista;
    }    
    
    @FacesConverter(forClass = Provincia.class)
    public static class ProvinciaConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            Object retorno = null;
            Integer key;
            
            if (value != null && value.length() != 0) {
                try {
                    key = Integer.parseInt(value);
                    
                    // Recorrer la lista para obtener el elemento cuya clave coincide con la recibida
                    for (Provincia prov : instancia.getListaFiltro()) {
                        if (prov.getIdProvincia().equals(key))
                            retorno = prov;                
                    }
                } catch (NumberFormatException e) {
                    retorno = null;
                }
            }            
            
            return retorno;
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object)
        {
            String retorno = null;
            
            if (object != null && ((Provincia)object).getIdProvincia() != null) {
                retorno = ((Provincia)object).getIdProvincia().toString();
            }
            
            return retorno;
        }
    }
}
