
package reservashotel.presentation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import reservashotel.business.service.HabitacionService;
import reservashotel.business.vo.BusquedaHabitacionFiltro;
import reservashotel.business.vo.HabitacionFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.entities.Habitacion;
import reservashotel.persistence.entities.ReservaCab;
import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para la ventana de selección de habitación.
 */
@ManagedBean
@SessionScoped
public class HabitacionSeleccionController {

    private final   HabitacionService      servicio   = new HabitacionService();
    private         List<Habitacion>       lista      = null;
    private         HabitacionFiltro       filtro     = new HabitacionFiltro();
    private         ReservaCab             reserva;

    /**
     * getLista
     * @return lista de habitaciones
     */
    public List<Habitacion> getLista() {
        return lista;
    }

    /**
     * getFiltro
     * @return filtro
     */
    public HabitacionFiltro getFiltro() {
        return filtro;
    }
    
    /**
     * Muestra la ventana de seleccion.
     * @param reserva Reserva
     */
    public void doPrepararSeleccion(ReservaCab reserva) {
        if (reserva.getNumeroNoches() == 0) {
            JsfUtil.mensajeAviso("No ha completado la selección de fechas de la reserva.");
            
        } else {
            this.reserva = reserva;

            filtro  = new HabitacionFiltro();
            lista   = null;

            Map<String,Object> options = new HashMap<>();
            options.put("modal", true);
            options.put("draggable", true);
            options.put("resizable", false);
            options.put("contentHeight", 450);
            options.put("contentWidth", 700);

            RequestContext.getCurrentInstance().openDialog("/habitacion/seleccion", options, null);
        }
    }
    
    /**
     * Comprueba en el sistema la disponibilidad de las habitaciones, y muestra
     * las libres en base a los datos del filtro.
     * @return destino
     */
    public String doComprobarDisponibilidad() {       
        String retorno = null;
        
        try {
            // Lista habitaciones en base a los filtros establecidos pero
            // siempre dentro de las habitaciones activas.
            this.filtro.setActivoSn(ConstantesFiltro.ACTIVO_SI);
            
            BusquedaHabitacionFiltro filtroBusqueda = new BusquedaHabitacionFiltro();
            filtroBusqueda.setHabitacion(this.filtro);
            filtroBusqueda.setReserva(reserva);
            
            lista = servicio.listarLibres(filtroBusqueda);
            
            retorno = "/habitacion/seleccion";
        }
        catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Retorna a la llamada la habitación seleccionado en la ventana.
     * @param habitacion 
     */
    public void doSeleccionar(Habitacion habitacion) {
        RequestContext.getCurrentInstance().closeDialog(habitacion);
    }

    /**
     * Limpiar los filtros de la ventana y la lista.
     */
    public void doLimpiar() {
        this.filtro = new HabitacionFiltro();
        this.lista = null;
    }
}
