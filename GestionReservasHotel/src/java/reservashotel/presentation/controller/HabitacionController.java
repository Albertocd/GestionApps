
package reservashotel.presentation.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.HabitacionService;
import reservashotel.business.vo.HabitacionFiltro;
import reservashotel.persistence.entities.Habitacion;

import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de las habitaciones.
 */
@ManagedBean
@SessionScoped
public class HabitacionController {

    private final   HabitacionService      servicio        = new HabitacionService();
    private         HabitacionFiltro       filtro          = new HabitacionFiltro();
    private         List<Habitacion>       lista           = new ArrayList<Habitacion>();
    private         Habitacion             habitacionActual;

    /**
    * Creates a new instance of HabitacionController
    */
    public HabitacionController() {
    }

    /**
     * getHabitacionActual
     * @return habitacionActual
     */
    public Habitacion getHabitacionActual() {
        return this.habitacionActual;
    }

    /**
     * setHabitacionActual
     * @param habitacionActual Habitacion
     */
    public void setHabitacionActual(Habitacion habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    /**
     * getFiltro
     * @return HabitacionFiltro
     */
    public HabitacionFiltro getFiltro() {
        return filtro;
    }

    /**
     * setFiltro
     * @param filtro HabitacionFiltro
     */
    public void setFiltro(HabitacionFiltro filtro) {
        this.filtro = filtro;
    }
    
    /**
     * Carga la lista en la página
     * @return List< Habitacion > 
     */
    public List<Habitacion> getLista() {
        return this.lista;
    }
    
    /**
     * Crea la instancia de la nueva habitacion.
     * @return Destino
     */
    public String doPrepararAlta() {
        this.habitacionActual = new Habitacion();
        
        return "alta";
    }

    /**
     * Inserta la habitacion actual en el sistema.
     * @return Destino
     */
    public String doAlta() {
        try {
            this.habitacionActual.setCodigo(this.habitacionActual.getCodigo().toUpperCase());
            this.servicio.insertar(this.habitacionActual);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            return "listado";
        
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            return null;
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
            
            return null;
        }
    }

    /**
     * Establece el destino en el listado.
     * @return 
     */
    public String doPrepararListado() {
        return "listado";
    }
    
    /**
     * Establece el destino en el listado.
     * @return 
     */
    public String doPrepararListadoVacio() {
        // Limpiar el filtro y la lista
        filtro  = new HabitacionFiltro();
        lista   = null;

        return "/habitacion/listado";
    }
    
    /**
     * Carga la habitacion para la consulta.
     * @param habitacion Habitacion
     * @return Destino
     */
    public String doPrepararConsulta(Habitacion habitacion) {
        this.habitacionActual = habitacion;
        
        return "consulta";
    }
    
    /**
     * Carga la habitacion para su moficiación.
     * @param habitacion Habitacion
     * @return Destino
     */
    public String doPrepararModificacion(Habitacion habitacion) {
        this.habitacionActual = habitacion;   

        return "modificacion";
    }
    
    /**
     * Modifica la habitacion en el sistema.
     * @return Destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            this.servicio.modificar(habitacionActual);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "listado";
        
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
     * Elimina la habitacion del sistema.
     * @param habitacion Habitacion
     * @return Destino
     */
    public String doEliminar(Habitacion habitacion) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(habitacion);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    public String doBuscar() {
        String retorno = null;

        try {
            this.lista = servicio.listar(this.filtro);
            
            retorno = "listado";
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
}
