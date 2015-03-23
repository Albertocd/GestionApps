
package reservashotel.business.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.service.generic.ConstantesService;
import reservashotel.business.service.generic.IGenericService;
import reservashotel.business.vo.EstadoReservaFiltro;
import reservashotel.persistence.dao.EstadoReservaDAO;
import reservashotel.persistence.entities.EstadoReserva;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad EstadoReserva.
 */
public class EstadoReservaService implements IGenericService<EstadoReserva> {
    
    @Override
    public List<EstadoReserva> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<EstadoReserva> lista = new EstadoReservaDAO().listar(sesion, ConstantesService.ORDENACION_NOMBRE);
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(EstadoReserva t) throws Exception {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public void modificar(EstadoReserva t) throws Exception {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public void eliminar(EstadoReserva t) throws Exception {
        throw new UnsupportedOperationException("Función no implementada."); 
    }
    
    /**
     * Lista resultados en base al filtro pasado por parámetro.
     * @param filtro EstadoReservaFiltro
     * @return List< EstadoReserva > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<EstadoReserva> listar(EstadoReservaFiltro filtro) throws HibernateException, Exception {
        Session                sesion      =   null;
        EstadoReservaDAO       estResDao   =   null;
        List<EstadoReserva>    lista       =   null;
        
        try {
            
            estResDao = new EstadoReservaDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = estResDao.listar(sesion, filtro, ConstantesService.ORDENACION_NOMBRE);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (estResDao != null) {
                estResDao.rollback(sesion);
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
