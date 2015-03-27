
package gestionclases.presentation.controller;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.exception.InfoException;
import gestionclases.business.service.HorarioService;
import gestionclases.business.vo.HorarioFiltro;
import gestionclases.persistence.entity.Horario;
import gestionclases.presentation.util.ConstantesErrores;
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
public class HorarioController {

    private static  HorarioController  instancia;
    private final   HorarioService     servicio        = new HorarioService();
    private         Horario            horario         = new Horario();
    private         List<Horario>      lista           = new ArrayList<Horario>();
    private         HorarioFiltro      filtro          = new HorarioFiltro();

    /**
     * Creates a new instance of HorarioController
     */
    public HorarioController() {
        instancia = this;
    }

    public Horario getHorario() {
        return horario;
    }

    public List<Horario> getLista() {
        return lista;
    }

    public HorarioFiltro getFiltro() {
        return filtro;
    }
    
    
    /**
     * Crea la instancia del nuevo horario.
     * @return destino
     */
    public String doPrepararAlta() {
        this.horario = new Horario();

        return "/horario/alta";
    }
    
    /**
     * Búsqueda de horarios.
     * @return 
     */
    public String doBuscar() {
        String retorno = null;

        try {
            this.lista = servicio.listar(this.filtro);
            
            retorno = "/horario/listado";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
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
        // Limpiar el filtro y la lista
        filtro  = new HorarioFiltro();
        lista   = null;

        return "/horario/listado";
    }

    
    /**
     * Carga el horario para su moficiación.
     * @param horario Horario
     * @return destino
     */
    public String doPrepararModificacion(Horario horario) {
        this.horario = horario;   

        return "/horario/modificacion";
    }
    
    /**
     * Modifica el horario en el sistema.
     * @return destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            validaciones();
            
            this.servicio.modificar(this.horario);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "/horario/listado";
        
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
     * Elimina el horario en el sistema.
     * @param horario Horario
     * @return destino
     */
    public String doEliminar(Horario horario) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(horario);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "/horario/listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Inserta el horario actual en el sistema.
     * @return destino
     */
    public String doAlta() {
        String retorno;
        
        try {
            validaciones();
            
            this.servicio.insertar(this.horario);
            this.lista = servicio.listar(this.filtro);

            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno= "/horario/listado";
            
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
        return "/horario/listado";
    }
    
    private void validaciones() throws ErrorException {
        if (this.lista != null && !this.lista.isEmpty()) {
            for(Horario h : lista) {
                if (this.horario.getHora().equalsIgnoreCase(h.getHora()) 
                        && this.horario.getDiaSemana().equals(h.getDiaSemana())) {
                    throw new ErrorException(ConstantesErrores.HORARIO_HORA_EXISTE);
                }
            }
        }
    }
    
    @FacesConverter(forClass = Horario.class, value = "horarioConverter")
    public static class HorarioConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            Object retorno = null;
            Integer key;
            
            if (value != null && value.length() != 0) {
                try {
                    key = Integer.parseInt(value);
                    
                    // Recorrer la lista para obtener el elemento cuya clave coincide con la recibida
                    for (Horario h : instancia.getLista()) {
                        if (h.getId().equals(key))
                            retorno = h;                
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
            
            if (object != null && ((Horario)object).getId() != null) {
                retorno = ((Horario)object).getId().toString();
            }
            
            return retorno;
        }        
    }  
}
