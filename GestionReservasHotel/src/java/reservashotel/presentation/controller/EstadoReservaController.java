
package reservashotel.presentation.controller;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import reservashotel.business.service.EstadoReservaService;
import reservashotel.business.vo.EstadoReservaFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.entities.EstadoReserva;
import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de los estados de la reserva.
 */
@ManagedBean
@SessionScoped
public class EstadoReservaController {

    private static  EstadoReservaController    instancia;
    private final   EstadoReservaService       servicio        = new EstadoReservaService();
    private         List<EstadoReserva>        listaCompleta   = null;
    
    /**
     * Creates a new instance of EstadoReservaController
     */
    public EstadoReservaController() {
        instancia = this;
    }
    
    /**
     * Retorna la lista de tipos de reserva.
     * @return List< EstadoReserva > 
     */
    public List<EstadoReserva> getListaFiltro() {
        if (listaCompleta == null) {
            try {
                EstadoReservaFiltro filtro = new EstadoReservaFiltro();
                filtro.setActivoSn(ConstantesFiltro.ACTIVO_SI);
                
                listaCompleta = servicio.listar(filtro);
            } catch (Exception e) {
                JsfUtil.mensajeError(e.getMessage());
            }
        }
        
        return listaCompleta;
    }
    
    @FacesConverter(forClass = EstadoReserva.class, value = "estadoReservaConverter")
    public static class EstadoReservaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            Object retorno = null;
            Integer key;
            
            if (value != null && value.length() != 0) {
                try {
                    key = Integer.parseInt(value);
                    
                    // Recorrer la lista para obtener el elemento cuya clave coincide con la recibida
                    for (EstadoReserva estado : instancia.getListaFiltro()) {
                        if (estado.getIdEstadoReserva().equals(key))
                            retorno = estado;                
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
            
            if (object != null && ((EstadoReserva)object).getIdEstadoReserva() != null) {
                retorno = ((EstadoReserva)object).getIdEstadoReserva().toString();
            }
            
            return retorno;
        }        
    }    
}