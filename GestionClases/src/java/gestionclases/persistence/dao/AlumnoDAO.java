
package gestionclases.persistence.dao;

import gestionclases.business.vo.AlumnoFiltro;
import gestionclases.business.vo.generic.ConstantesFiltro;
import gestionclases.persistence.dao.generic.GenericDAO;
import gestionclases.persistence.entity.Alumno;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author alberto
 * Clase DAO para la entidad Alumno.
 */
public class AlumnoDAO extends GenericDAO<Alumno,Integer> {
    
    /**
     * Lista entidades Alumno en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< Cargo >
     */
    public List<Alumno> listar(Session sesion, AlumnoFiltro filtro, String ordenarPor) {
        List<Alumno> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Alumno.class);
        
            if (filtro != null) {
                
                if (filtro.getNombre() != null && filtro.getNombre().length() > 0) {
                    crit.add(Restrictions.ilike("nombre", filtro.getNombre(), MatchMode.ANYWHERE));
                }
                if (filtro.getEsGrupo() != 0) {
                    Boolean esGrupo = null;
                    if (filtro.getEsGrupo() == ConstantesFiltro.GRUPO_SI) {
                        esGrupo = Boolean.TRUE;
                    } else if (filtro.getEsGrupo() == ConstantesFiltro.GRUPO_NO) {
                        esGrupo = Boolean.FALSE;
                    }
                    crit.add(Restrictions.eq("esGrupo", esGrupo));
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
