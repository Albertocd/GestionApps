
package reservashotel.persistence.dao;

import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import reservashotel.business.vo.ReservaCabFiltro;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.ReservaCab;


/**
 * @author alberto
 * Clase DAO para la entidad ReservaCab.
 */
public class ReservaCabDAO extends GenericDAO<ReservaCab,Integer> {
    
    /**
     * Lista entidades ReservaCab en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< ReservaCab >
     */
    public List<ReservaCab> listar(Session sesion, ReservaCabFiltro filtro, String ordenarPor) {
        List<ReservaCab> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(ReservaCab.class);
        
            if (filtro != null) {
                
                if (filtro.getReferencia() != null && filtro.getReferencia().length() > 0) {
                    crit.add(Restrictions.eq(ConstantesDAO.RESERVACAB_REFERENCIA, filtro.getReferencia()));
                }
                if (filtro.getCliente() != null) {
                    crit.add(Restrictions.eq(ConstantesDAO.RESERVACAB_CLIENTE, filtro.getCliente()));
                }
                if (filtro.getEstado() != 0) {
                    crit.add(Restrictions.eq(ConstantesDAO.RESERVACAB_ID_ESTADO, filtro.getEstado()));
                }
                if (filtro.getFechaRegistroDesde() != null) {
                    crit.add(Restrictions.ge(ConstantesDAO.RESERVACAB_FECHAREGISTRO, filtro.getFechaRegistroDesde()));
                }
                if (filtro.getFechaRegistroHasta() != null) {
                    crit.add(Restrictions.le(ConstantesDAO.RESERVACAB_FECHAREGISTRO, filtro.getFechaRegistroHasta()));
                } 
                if (filtro.getFechaEntradaDesde() != null) {
                    crit.add(Restrictions.ge(ConstantesDAO.RESERVACAB_FECHAENTRADA, filtro.getFechaEntradaDesde()));
                }
                if (filtro.getFechaEntradaHasta() != null) {
                    crit.add(Restrictions.le(ConstantesDAO.RESERVACAB_FECHAENTRADA, filtro.getFechaEntradaHasta()));
                }
                if (filtro.getFechaSalidaDesde() != null) {
                    crit.add(Restrictions.ge(ConstantesDAO.RESERVACAB_FECHASALIDA, filtro.getFechaSalidaDesde()));
                }
                if (filtro.getFechaSalidaHasta() != null) {
                    crit.add(Restrictions.le(ConstantesDAO.RESERVACAB_FECHASALIDA, filtro.getFechaSalidaHasta()));
                }
                
                crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                crit.addOrder(Order.asc(ordenarPor));

                lista = crit.list();
            }
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
    
    
    /**
     * Lista entidades ReservaCab para las cuales la fecha pasada por parámetro
     * se encuentra dentro del rango de fecha de entrada y de salida.
     * @param sesion Sesion 
     * @param fecha Fecha 
     * @param ordenarPor Criterio de ordenación
     * @return List< ReservaCab >
     */
    public List<ReservaCab> listar(Session sesion, Date fecha, String ordenarPor) {
        List<ReservaCab> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(ReservaCab.class);
        
            crit.add(Restrictions.ge(ConstantesDAO.RESERVACAB_FECHAENTRADA, fecha));
            crit.add(Restrictions.le(ConstantesDAO.RESERVACAB_FECHASALIDA, fecha));
            
            crit.addOrder(Order.asc(ordenarPor));

            lista = crit.list();
        
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
    
    /**
     * Obtiene la reserva cuya referencia coincide con el parámetro.
     * @param sesion
     * @param referencia
     * @return reserva
     */
    public ReservaCab buscar(Session sesion, String referencia) {
        ReservaCab reserva = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(ReservaCab.class);
        
            if (referencia != null && !referencia.isEmpty()) { 
                
                crit.add(Restrictions.eq(ConstantesDAO.RESERVACAB_REFERENCIA, referencia));

                reserva = (ReservaCab)crit.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        }
    
        return reserva;
    }
    
    /**
     * Busca las reservas activas en la fecha pasada por parámetro. Por reserva
     * activa se entiende las que no están cerradas ni anuladas.
     * @param sesion Sesión
     * @param fecha Fecha
     * @return 
     */
    public List<ReservaCab> listarReservasActivasFecha(Session sesion, Date fecha) {
        List<ReservaCab> lista = null;
        
        try {
        
            String queryString = "SELECT reserva FROM ReservaCab reserva "
                    + "WHERE (:fecha BETWEEN reserva.fechaEntrada AND reserva.fechaSalida) " 
                    + "AND (reserva.estadoReserva.idEstadoReserva IN (1,2)) ";
            
            Query query = sesion.createQuery(queryString);
            
            query.setParameter("fecha", fecha); 
            
            lista = query.list();
            
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
}
