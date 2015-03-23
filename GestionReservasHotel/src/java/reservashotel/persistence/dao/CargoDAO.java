
package reservashotel.persistence.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import reservashotel.business.vo.CargoFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.dao.generic.ConstantesDAO;
import reservashotel.persistence.dao.generic.GenericDAO;
import reservashotel.persistence.entities.Cargo;


/**
 * @author alberto
 * Clase DAO para la entidad Cargo.
 */
public class CargoDAO extends GenericDAO<Cargo,Integer> {
    
    /**
     * Lista entidades Cargo en base al filtro recibido.
     * @param sesion Sesion 
     * @param filtro Filtro
     * @param ordenarPor Criterio de ordenación
     * @return List< Cargo >
     */
    public List<Cargo> listar(Session sesion, CargoFiltro filtro, String ordenarPor) {
        List<Cargo> lista = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Cargo.class);
        
            if (filtro != null) {
                
                if (filtro.getCodigo() != null && filtro.getCodigo().length() > 0) {
                    crit.add(Restrictions.ilike(ConstantesDAO.CARGO_CODIGO, filtro.getCodigo(), MatchMode.ANYWHERE));
                }
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
                    crit.add(Restrictions.eq(ConstantesDAO.CARGO_ACTIVOSN, activoSn));
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
     * Obtiene el cargo cuyo codigo es el pasado por parámetro.
     * @param sesion
     * @param codigo
     * @return cargo
     */
    public Cargo buscar(Session sesion, String codigo) {
        Cargo cargo = null;
        
        try {
        
            Criteria crit = sesion.createCriteria(Cargo.class);
        
            if (codigo != null && !codigo.isEmpty()) { 
                
                crit.add(Restrictions.eq(ConstantesDAO.CARGO_CODIGO, codigo));

                cargo = (Cargo)crit.uniqueResult();
            }
        } catch (Exception e) {
            throw e;
        }
    
        return cargo;
    }
}
