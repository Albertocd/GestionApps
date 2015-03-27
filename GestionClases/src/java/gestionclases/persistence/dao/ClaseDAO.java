
package gestionclases.persistence.dao;

import gestionclases.business.vo.ClaseFiltro;
import gestionclases.persistence.dao.generic.GenericDAO;
import gestionclases.persistence.entity.Clase;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author alberto
 * Clase DAO para la entidad Clase.
 */
public class ClaseDAO extends GenericDAO<Clase,Integer> {
    
    /**
     * Lista entidades Clase en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< Clase >
     */
    public List<Clase> listar(Session sesion, ClaseFiltro filtro, String ordenarPor) {
        List<Clase> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Clase.class);
        
            if (filtro != null) {
                
                if (filtro.getHorario() != null) {
                    crit.add(Restrictions.eq("horario", filtro.getHorario()));
                }
                if (filtro.getTipoClase() != null) {
                    crit.add(Restrictions.eq("tipoClase", filtro.getTipoClase()));
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
}
