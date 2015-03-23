
package reservashotel.persistence.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import reservashotel.business.vo.TipoHabitacionFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.TipoHabitacion;


/**
 * @author alberto
 * Clase DAO para la entidad TipoHabitacion.
 */
public class TipoHabitacionDAO extends GenericDAO<TipoHabitacion,Integer> {
    
    /**
     * Lista entidades TipoHabitacion en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< TipoHabitacion >
     */
    public List<TipoHabitacion> listar(Session sesion, TipoHabitacionFiltro filtro, String ordenarPor) {
        List<TipoHabitacion> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(TipoHabitacion.class);
        
            if (filtro != null) {
                
                if (filtro.getNombre() != null && filtro.getNombre().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.CARGO_NOMBRE, filtro.getNombre(), MatchMode.ANYWHERE));
                }
                if (filtro.getPrecioDesde() != null) {
                    crit.add(Restrictions.ge(ConstantesDAO.CARGO_PRECIO, filtro.getPrecioDesde()));
                }
                if (filtro.getPrecioHasta() != null) {
                    crit.add(Restrictions.le(ConstantesDAO.CARGO_PRECIO, filtro.getPrecioHasta()));
                }
                if (filtro.getActivoSn() != 0) {
                    Boolean activoSn = null;
                    if (filtro.getActivoSn() == ConstantesFiltro.ACTIVO_SI) {
                        activoSn = Boolean.TRUE;
                    } else if (filtro.getActivoSn() == ConstantesFiltro.ACTIVO_NO) {
                        activoSn = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq(ConstantesDAO.TIPOHABITACION_ACTIVOSN, activoSn));
                }
                    
                crit.addOrder(Order.asc(ordenarPor));

                lista = crit.list();
            }
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
}
