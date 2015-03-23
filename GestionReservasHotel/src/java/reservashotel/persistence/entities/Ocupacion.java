
package reservashotel.persistence.entities;

import java.io.Serializable;

/**
 * @author alberto
 * Entidad ocupación.
 */
public class Ocupacion implements Serializable {
    
    private Habitacion  habitacion;
    private boolean     ocupadaSn;
    private Cliente     cliente;
    private ReservaDet  reservaDetalle;

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ReservaDet getReservaDetalle() {
        return reservaDetalle;
    }

    public void setReservaDetalle(ReservaDet reservaDetalle) {
        this.reservaDetalle = reservaDetalle;
    }

    public boolean isOcupadaSn() {
        return ocupadaSn;
    }

    public void setOcupadaSn(boolean ocupadaSn) {
        this.ocupadaSn = ocupadaSn;
    }

    
}
