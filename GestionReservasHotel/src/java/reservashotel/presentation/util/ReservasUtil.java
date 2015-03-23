
package reservashotel.presentation.util;

import java.util.List;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import reservashotel.persistence.entities.Habitacion;
import reservashotel.persistence.entities.ReservaCab;
import reservashotel.persistence.entities.ReservaDet;
import reservashotel.persistence.entities.Usuario;

/**
 * @author alberto
 * Clase para las utilidades usadas para las reservas.
 */
public class ReservasUtil {
    
    
    /**
     * Genera la referencia para la reserva pasada por parámetro. Este dato se genera formando un String
     * conformado con la letra inicial de los apellidos y nombre del cliente junto con los dias de 
     * entrada y salida, mes de entrada, año de entrada y los ids de las habitaciones reservadas.
     * @param reserva Reserva.
     * @param reservaDetLista Lista de detalles de la reserva (habitaciones).
     * @return código
     */
    public static String generaReferencia(ReservaCab reserva, List<ReservaDet> reservaDetLista) {
        String apellido1    = reserva.getCliente().getApellido1().substring(0, 1).toUpperCase();
        String apellido2    = reserva.getCliente().getApellido2().substring(0, 1).toUpperCase();
        String nombre       = reserva.getCliente().getNombre().substring(0, 1).toUpperCase();
        String diaEntrada   = String.valueOf(FechasUtil.diaFecha(reserva.getFechaEntrada()));
        String diaSalida    = String.valueOf(FechasUtil.diaFecha(reserva.getFechaSalida()));
        String mesEntrada   = String.valueOf(FechasUtil.mesFecha(reserva.getFechaEntrada()));
        String añoEntrada   = String.valueOf(FechasUtil.anoFecha(reserva.getFechaEntrada())).substring(2, 4);
        String habitaciones = "";
        for(ReservaDet detalle : reservaDetLista) {
            habitaciones = habitaciones + detalle.getHabitacion().getIdHabitacion();
        }
        
        return apellido1 + apellido2 + nombre + diaEntrada + diaSalida + mesEntrada + añoEntrada + habitaciones;
    }
    
    /**
     * Obtiene el usuario activo en la sesión.
     * @return usuario.
     */
    public static Usuario obtenerUsuario() {
        Usuario usuario;
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Application apli = context.getApplication( );
        usuario = (Usuario) apli.evaluateExpressionGet(context, "#{usuarioController.usuarioActual}", Usuario.class);
        
        return usuario;
    }
    
    /**
     * Obtiene la reserva activa en la sesión.
     * @return reserva.
     */
    public static ReservaCab obtenerReservaCab() {
        ReservaCab reserva;
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Application apli = context.getApplication( );
        reserva = (ReservaCab) apli.evaluateExpressionGet(context, "#{reservaController.reservaActual}", ReservaCab.class);
        
        return reserva;
    }
    
    /**
     * Calcula el número de noches según las fechas de entrada y salida.
     * @param reserva
     * @return numero de noches.
     */
    public static int calculaNumeroNoches(ReservaCab reserva) {
        int numNoches = 0;

        // Cálculo de la diferencia de días.
        if (reserva.getFechaEntrada() != null 
                && reserva.getFechaSalida() != null) {
            numNoches = FechasUtil.diferenciaDias(reserva.getFechaEntrada(),
                                               reserva.getFechaSalida());

        }
        
        return numNoches;
    }
}
