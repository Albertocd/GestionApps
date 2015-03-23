
package reservashotel.persistence.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.Usuario;


/**
 * @author alberto
 * Clase DAO para la entidad Usuario.
 */
public class UsuarioDAO extends GenericDAO<Usuario,Integer> {
    
    /**
     * Obtiene el usuario cuyo nombre de usuario se corresponde con el parámetro.
     * @param sesion Sesión
     * @param nombreUsuario Nombre de Usuario
     * @return Usuario
     */
    public Usuario getUsuarioBy(Session sesion, String nombreUsuario) {
        Usuario usuario = null;
  
        Criteria crit = sesion.createCriteria(Usuario.class);
        crit.add(Restrictions.eq(ConstantesDAO.USUARIO_NOMBREUSUARIO, nombreUsuario));
        
        Object obj = crit.uniqueResult();
        if (obj != null) {
            usuario = (Usuario) obj;
        }
        
        return usuario;
    }
}
