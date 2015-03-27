
package gestionclases.persistence.dao;

import gestionclases.persistence.dao.generic.GenericDAO;
import gestionclases.persistence.entity.Acceso;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;



/**
 * @author alberto
 * Clase DAO para la entidad Acceso.
 */
public class AccesoDAO extends GenericDAO<Acceso,Integer> {
    
    /**
     * Obtiene el usuario cuyo nombre de usuario se corresponde con el parámetro.
     * @param sesion Sesión
     * @param nombreUsuario Nombre de Usuario
     * @return Usuario
     */
    public Acceso getAccesoBy(Session sesion, String nombreUsuario) {
        Acceso usuario = null;
  
        Criteria crit = sesion.createCriteria(Acceso.class);
        crit.add(Restrictions.eq("usuario", nombreUsuario));
        
        Object obj = crit.uniqueResult();
        if (obj != null) {
            usuario = (Acceso) obj;
        }
        
        return usuario;
    }
}
