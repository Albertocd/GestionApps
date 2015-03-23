
package reservashotel.persistence.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import reservashotel.business.vo.EstadoReservaFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.EstadoReserva;


/**
 * @author alberto
 * Clase DAO para la entidad EstadoReserva.
 */
public class EstadoReservaDAO extends GenericDAO<EstadoReserva,Integer> {
    
    /**
     * Lista entidades EstadoReserva en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< EstadoReserva >
     */
    public List<EstadoReserva> listar(Session sesion, EstadoReservaFiltro filtro, String ordenarPor) {
        List<EstadoReserva> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(EstadoReserva.class);
        
            if (filtro != null) {
                
                if (filtro.getActivoSn() != 0) {
                    Boolean activoSn = null;
                    if (filtro.getActivoSn() == ConstantesFiltro.ACTIVO_SI) {
                        activoSn = Boolean.TRUE;
                    } else if (filtro.getActivoSn() == ConstantesFiltro.ACTIVO_NO) {
                        activoSn = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq(ConstantesDAO.ESTADORESERVA_ACTIVOSN, activoSn));
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
