
package gestionclases.persistence.dao;

import gestionclases.business.vo.HorarioFiltro;
import gestionclases.persistence.dao.generic.GenericDAO;
import gestionclases.persistence.entity.Horario;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author alberto
 * Clase DAO para la entidad Horario.
 */
public class HorarioDAO extends GenericDAO<Horario,Integer> {
    
    
    /**
     * Lista entidades Horario en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< Cargo >
     */
    public List<Horario> listar(Session sesion, HorarioFiltro filtro, String ordenarPor) {
        List<Horario> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Horario.class);
        
            if (filtro != null) {
                
                if (filtro.getDiaSemana() != 0) {
                    crit.add(Restrictions.eq("diaSemana.id", filtro.getDiaSemana()));
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
