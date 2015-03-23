
package reservashotel.persistence.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import reservashotel.business.vo.ProvinciaFiltro;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.Provincia;


/**
 * @author alberto
 * Clase DAO para la entidad Provincia.
 */
public class ProvinciaDAO extends GenericDAO<Provincia,Integer> {
    
    /**
     * Lista entidades Provincia en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< Cargo >
     */
    public List<Provincia> listar(Session sesion, ProvinciaFiltro filtro, String ordenarPor) {
        List<Provincia> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Provincia.class);
            
            if (filtro.getId() != null) {
                    crit.add(Restrictions.eq(ConstantesDAO.PROVINCIA_ID, filtro.getId()));
                }
            
            crit.addOrder(Order.asc(ordenarPor));

            lista = crit.list();
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
}
