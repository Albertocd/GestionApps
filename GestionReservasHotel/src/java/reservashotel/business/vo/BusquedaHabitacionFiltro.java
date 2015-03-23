
package reservashotel.business.vo;

import reservashotel.persistence.entities.ReservaCab;

/**
 * @author alberto
 * Filtro específico para la ventana de búsqueda de habitaciones libres.
 */
public class BusquedaHabitacionFiltro {
    
    private ReservaCab          reserva;
    private HabitacionFiltro    habitacion;

    public ReservaCab getReserva() {
        return reserva;
    }

    public void setReserva(ReservaCab reserva) {
        this.reserva = reserva;
    }

    public HabitacionFiltro getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(HabitacionFiltro habitacion) {
        this.habitacion = habitacion;
    }

}
