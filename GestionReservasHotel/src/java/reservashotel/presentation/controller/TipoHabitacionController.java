
package reservashotel.presentation.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.TipoHabitacionService;
import reservashotel.business.vo.TipoHabitacionFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.entities.TipoHabitacion;
import reservashotel.presentation.util.ConstantesErrores;
import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de los tipos de habitación.
 */
@ManagedBean
@SessionScoped
public class TipoHabitacionController {

    private static  TipoHabitacionController    instancia;
    private final   TipoHabitacionService       servicio        = new TipoHabitacionService();
    private         List<TipoHabitacion>        lista           = new ArrayList<TipoHabitacion>();
    private         TipoHabitacionFiltro        filtro          = new TipoHabitacionFiltro();
    private         TipoHabitacion              tipoActual;
    
    
        
    /**
     * Creates a new instance of TipoHabitacionController
     */
    public TipoHabitacionController() {
        instancia = this;
    }
    
    /**
     * Establece el destino en el listado.
     * @return 
     */
    public String doPrepararListadoVacio() {
        // Limpiar el filtro y la lista
        filtro  = new TipoHabitacionFiltro();
        lista   = null;

        return "/tipohabitacion/listado";
    }

    /**
     * Carga el tipo para la consulta.
     * @param tipo TipoHabitacion
     * @return Destino
     */
    public String doPrepararConsulta(TipoHabitacion tipo) {
        this.tipoActual = tipo;
        
        return "consulta";
    }
    
    /**
     * Carga el tipo para su moficiación.
     * @param tipo TipoHabitacion
     * @return Destino
     */
    public String doPrepararModificacion(TipoHabitacion tipo) {
        this.tipoActual = tipo;   

        return "modificacion";
    }
    
    /**
     * Crea la instancia del nuevo tipo.
     * @return Destino
     */
    public String doPrepararAlta() {
        this.tipoActual = new TipoHabitacion();
        
        return "alta";
    }
    
    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doPrepararListado() {
        this.doBuscar();
        
        return "listado";
    }
    
    public List<TipoHabitacion> getLista() {
        return lista;
    }

    public TipoHabitacionFiltro getFiltro() {
        return filtro;
    }

    public TipoHabitacion getTipoActual() {
        return tipoActual;
    }

    
    /**
     * Busca el tipo en base al filtro.
     * @return 
     */
    public String doBuscar() {
        String retorno = null;

        try {
            validacionesFiltro();
            
            this.lista = servicio.listar(this.filtro);
            
            retorno = "listado";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Modifica el tipo en el sistema.
     * @return Destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            this.servicio.modificar(this.tipoActual);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
        
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Inserta el tipo actual en el sistema.
     * @return Destino
     */
    public String doAlta() {
        String retorno;
        
        try {
            this.servicio.insertar(this.tipoActual);
            this.lista = servicio.listar(this.filtro);

            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno= "listado";
            
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
     * Elimina el tipo en el sistema.
     * @param tipo TipoHabitacion
     * @return Destino
     */
    public String doEliminar(TipoHabitacion tipo) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(tipo);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Realiza las validaciones de los datos introducidos en los filtros.
     * @throws InfoException 
     */
    private void validacionesFiltro() throws InfoException {
        if (this.filtro != null) {
            // Rango del precio.
            if ((this.filtro.getPrecioDesde() != null && this.filtro.getPrecioDesde().isNaN())
                    || (this.filtro.getPrecioHasta() != null && this.filtro.getPrecioHasta().isNaN())) {
                throw new InfoException(ConstantesErrores.PRECIO_VALORES_NO_VALIDOS);
            }
            
            if ((this.filtro.getPrecioDesde() != null && this.filtro.getPrecioDesde() < 0)
                    || (this.filtro.getPrecioHasta() != null && this.filtro.getPrecioHasta() < 0)) {
                throw new InfoException(ConstantesErrores.PRECIO_VALORES_NO_VALIDOS);
            }
            
            if (this.filtro.getPrecioDesde() != null && this.filtro.getPrecioHasta() != null
                    && this.filtro.getPrecioDesde() > this.filtro.getPrecioHasta()) {
                throw new InfoException(ConstantesErrores.PRECIOMIN_SUPERIOR_PRECIOMAX);
            }
        }
    }
    
    /**
     * Obtiene los tipos de habitación activos.
     * @return List< TipoHabitacion >
     */
    public List<TipoHabitacion> getListaFiltro() {
        if (lista == null || lista.isEmpty()) {
            try {
                TipoHabitacionFiltro filtro = new TipoHabitacionFiltro();
                filtro.setActivoSn(ConstantesFiltro.ACTIVO_SI);
                
                lista = servicio.listar(filtro);
                
            } catch (Exception e) {
                JsfUtil.mensajeError(e.getMessage());
            }
        }
        
        return lista;
    }
     
    @FacesConverter(forClass = TipoHabitacion.class)
    public static class TipoHabitacionConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            Object retorno = null;
            Integer key;
            
            if (value != null && value.length() != 0) {
                try {
                    key = Integer.parseInt(value);
                    
                    // Recorrer la lista para obtener el elemento cuya clave coincide con la recibida
                    for (TipoHabitacion tipoHab : instancia.getListaFiltro()) {
                        if (tipoHab.getIdTipoHabitacion().equals(key))
                            retorno = tipoHab;                
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
            
            if (object != null && ((TipoHabitacion)object).getIdTipoHabitacion() != null) {
                retorno = ((TipoHabitacion)object).getIdTipoHabitacion().toString();
            }
            
            return retorno;
        }
    }
}