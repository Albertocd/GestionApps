
package gestionclases.presentation.controller;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.exception.InfoException;
import gestionclases.business.service.TipoClaseService;
import gestionclases.persistence.entity.TipoClase;
import gestionclases.presentation.util.JsfUtil;
import java.util.ArrayList;
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
public class TipoClaseController {

    private static  TipoClaseController   instancia;
    private final   TipoClaseService      servicio        = new TipoClaseService();
    private         TipoClase             tipoClase       = new TipoClase();
    private         List<TipoClase>       lista           = new ArrayList<TipoClase>();
    

    /**
     * Creates a new instance of TipoClaseController
     */
    public TipoClaseController() {
        instancia = this;
    }
    
    public TipoClase getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(TipoClase tipoClase) {
        this.tipoClase = tipoClase;
    }

    public List<TipoClase> getLista() {
        return lista;
    }

    public void setLista(List<TipoClase> lista) {
        this.lista = lista;
    }
    
    
    /**
     * Crea la instancia del nuevo tipo.
     * @return destino
     */
    public String doPrepararAlta() {
        this.tipoClase = new TipoClase();
        
        return "/tipoclase/alta";
    }
    
    /**
     * Búsqueda de tipos de clases.
     * @return 
     */
    public String doBuscar() {
        String retorno = null;

        try {
            this.lista = servicio.listar();
            
            retorno = "/tipoclase/listado";
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Establece el destino en el listado.
     * @return 
     */
    public String doPrepararListadoVacio() {
        lista   = null;

        return "/tipoclase/listado";
    }
    
    /**
     * Carga el tipo de clase para su moficiación.
     * @param tipoclase TipoClase
     * @return destino
     */
    public String doPrepararModificacion(TipoClase tipoclase) {
        this.tipoClase = tipoclase;   

        return "/tipoclase/modificacion";
    }
    
    /**
     * Modifica el tipo de clase en el sistema.
     * @return destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            this.servicio.modificar(this.tipoClase);
            this.lista = servicio.listar();
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "/tipoclase/listado";
        
        } catch (InfoException ex) {
            JsfUtil.mensajeInfo(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }
        
        return retorno;
    }

    /**
     * Elimina el tipo de clase en el sistema.
     * @param tipoclase TipoClase
     * @return destino
     */
    public String doEliminar(TipoClase tipoclase) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(tipoclase);
            this.lista = servicio.listar();
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "/tipoclase/listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Inserta el tipò de clase actual en el sistema.
     * @return destino
     */
    public String doAlta() {
        String retorno;
        
        try {
            this.servicio.insertar(this.tipoClase);
            this.lista = servicio.listar();

            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno= "/tipoclase/listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
        } catch (InfoException ex) {
            JsfUtil.mensajeInfo(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
            
            retorno = null;
        }
        
        return retorno;
    }

    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doPrepararListado() {
        return "/tipoclase/listado";
    }
    
    @FacesConverter(forClass = TipoClase.class, value = "tipoClaseConverter")
    public static class TipoClaseConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            Object retorno = null;
            Integer key;
            
            if (value != null && value.length() != 0) {
                try {
                    key = Integer.parseInt(value);
                    
                    // Recorrer la lista para obtener el elemento cuya clave coincide con la recibida
                    for (TipoClase tipo : instancia.getLista()) {
                        if (tipo.getId().equals(key))
                            retorno = tipo;                
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
            
            if (object != null && ((TipoClase)object).getId() != null) {
                retorno = ((TipoClase)object).getId().toString();
            }
            
            return retorno;
        }        
    } 
}
