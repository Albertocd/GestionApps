
package reservashotel.business.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import reservashotel.business.service.generic.ConstantesService;
import reservashotel.business.service.generic.IGenericService;
import reservashotel.persistence.dao.HabitacionDAO;
import reservashotel.persistence.dao.ReservaCabDAO;
import reservashotel.persistence.entities.Cliente;
import reservashotel.persistence.entities.Habitacion;
import reservashotel.persistence.entities.Ocupacion;
import reservashotel.persistence.entities.ReservaCab;
import reservashotel.persistence.entities.ReservaDet;
import reservashotel.persistence.util.HibernateUtil;

/**
 * @author alberto
 * Clase para las funcionalidades del listado de ocupacion.
 */
public class OcupacionService implements IGenericService<Ocupacion> {

    @Override
    public List<Ocupacion> listar() {
        throw new UnsupportedOperationException("Funcionalidad no implementada.");
    }

    @Override
    public void insertar(Ocupacion t) throws Exception {
        throw new UnsupportedOperationException("Funcionalidad no implementada.");
    }

    @Override
    public void modificar(Ocupacion t) throws Exception {
        throw new UnsupportedOperationException("Funcionalidad no implementada."); 
    }

    @Override
    public void eliminar(Ocupacion t) throws Exception {
        throw new UnsupportedOperationException("Funcionalidad no implementada."); 
    }
    
    
    /**
     * Obtiene el listado de ocupaciones para la fecha.
     * @param fecha
     * @return lista de ocupaciones.
     */
    public List<Ocupacion> listar(Date fecha) {
        Ocupacion           ocupacion       = null;
        List<Ocupacion>     lista           = new ArrayList<Ocupacion>(); 
        List<ReservaCab>    reservasActivas = new ArrayList<ReservaCab>(); 
        List<Habitacion>    habOcupadas     = new ArrayList<Habitacion>(); 
        
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<Habitacion> habitaciones = new HabitacionDAO().listar(sesion, ConstantesService.ORDENACION_CODIGO);
        if (habitaciones != null && !habitaciones.isEmpty()) {
            // Reservas de hoy (Activas o Reservadas)
            reservasActivas = new ReservaCabDAO().listarReservasActivasFecha(sesion, fecha);
            
            // Habitaciones ocupadas.
            for(ReservaCab reserva : reservasActivas) {
                for(ReservaDet detalle : reserva.getDetalles()) {
                    ocupacion = new Ocupacion();
                    ocupacion.setCliente(reserva.getCliente());
                    ocupacion.setOcupadaSn(Boolean.TRUE);
                    ocupacion.setReservaDetalle(detalle);
                    ocupacion.setHabitacion(detalle.getHabitacion());
                    
                    lista.add(ocupacion);
                    habOcupadas.add(detalle.getHabitacion());
                }
            }
            
            for(Habitacion hab : habitaciones) {
                // Habitaciones libres.
                if (!habOcupadas.contains(hab)) {
                    ocupacion = new Ocupacion();
                    ocupacion.setCliente(new Cliente());
                    ocupacion.setOcupadaSn(Boolean.FALSE);
                    ocupacion.setReservaDetalle(new ReservaDet());
                    ocupacion.setHabitacion(hab);
                    
                    lista.add(ocupacion);
                }
            }
            
        }
        
        sesion.getTransaction().commit();

        return lista;
    }
}
