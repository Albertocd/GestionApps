
package reservashotel.business.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.service.generic.ConstantesService;
import reservashotel.business.service.generic.IGenericService;
import reservashotel.business.vo.ProvinciaFiltro;
import reservashotel.persistence.dao.ProvinciaDAO;
import reservashotel.persistence.entities.Provincia;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad Provincia.
 */
public class ProvinciaService implements IGenericService<Provincia> {

    @Override
    public List<Provincia> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<Provincia> lista = new ProvinciaDAO().listar(sesion, ConstantesService.ORDENACION_NOMBRE);
        
        sesion.getTransaction().commit();
        
        return lista;
    } 

    @Override
    public void insertar(Provincia t) {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public void modificar(Provincia t) {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public void eliminar(Provincia t) {
        throw new UnsupportedOperationException("Función no implementada."); 
    }
    
    /**
     * Lista resultados en base al filtro pasado por parámetro.
     * @param filtro ProvinciaFiltro
     * @return List< Provincia > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Provincia> listar(ProvinciaFiltro filtro) throws HibernateException, Exception {
        Session            sesion          =   null;
        ProvinciaDAO       provinciaDao    =   null;
        List<Provincia>    lista           =   null;
        
        try {
            
            provinciaDao = new ProvinciaDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = provinciaDao.listar(sesion, filtro, ConstantesService.ORDENACION_NOMBRE);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (provinciaDao != null) {
                provinciaDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        
        return lista;
    }
}
