
package reservashotel.persistence.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import reservashotel.business.vo.ClienteFiltro;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.Cliente;


/**
 * @author alberto
 * Clase DAO para la entidad Cliente.
 */
public class ClienteDAO extends GenericDAO<Cliente,Integer> {
    
    
    /**
     * Lista entidades Cliente en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< Cliente >
     */
    public List<Cliente> listar(Session sesion, ClienteFiltro filtro, String ordenarPor) {
        List<Cliente> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Cliente.class);
        
            if (filtro != null) {
                
                if (filtro.getNombre() != null && filtro.getNombre().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.CLIENTE_NOMBRE, filtro.getNombre(), MatchMode.ANYWHERE));
                }
                if (filtro.getApellido1() != null && filtro.getApellido1().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.CLIENTE_APELLIDO1, filtro.getApellido1(), MatchMode.ANYWHERE));
                }
                if (filtro.getApellido2() != null && filtro.getApellido2().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.CLIENTE_APELLIDO2, filtro.getApellido2(), MatchMode.ANYWHERE));
                }
                if (filtro.getDni() != null && filtro.getDni().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.CLIENTE_DNI, filtro.getDni(), MatchMode.ANYWHERE));
                }
                if (filtro.getIdProvincia() != null) {
                    crit.add(Restrictions.eq(ConstantesDAO.CLIENTE_ID_PROVINCIA, filtro.getIdProvincia()));
                }
                    
                crit.addOrder(Order.asc(ordenarPor));

                lista = crit.list();
            }
        } catch (Exception e) {
            throw e;
        }
    
        return lista;
    }
    
    /**
     * Obtiene el cliente cuyo dni es el pasado por parámetro.
     * @param sesion
     * @param dni
     * @return cliente
     */
    public Cliente buscar(Session sesion, String dni) {
        Cliente cliente = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Cliente.class);
        
            if (dni != null && !dni.isEmpty()) { 
                
                crit.add(Restrictions.eq(ConstantesDAO.CLIENTE_DNI, dni));

                cliente = (Cliente)crit.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        }
    
        return cliente;
    }
}
