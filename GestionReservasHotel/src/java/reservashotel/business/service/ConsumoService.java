
package reservashotel.business.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.service.generic.IGenericService;
import reservashotel.persistence.dao.ConsumoDAO;
import reservashotel.persistence.dao.ReservaCabDAO;
import reservashotel.persistence.entities.Consumo;
import reservashotel.persistence.entities.ReservaCab;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad Consumo.
 */
public class ConsumoService implements IGenericService<Consumo> {

    @Override
    public List<Consumo> listar() {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public void insertar(Consumo t) throws Exception {
        throw new UnsupportedOperationException("Función no implementada.");
    }

    @Override
    public void modificar(Consumo t) throws Exception {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public void eliminar(Consumo t) throws Exception {
        throw new UnsupportedOperationException("Función no implementada."); 
    }
    
    /**
     * Elimina la lista de consumos.
     * @param lista List< Consumo >
     * @throws Exception 
     */
    public void eliminar(List<Consumo> lista) throws Exception {
        Session      sesion         = null;
        ConsumoDAO   consumoDao     = null;
        
        try {
            
            if (lista != null && !lista.isEmpty()) {
                consumoDao = new ConsumoDAO();
                sesion = HibernateUtil.getSession();
                sesion.beginTransaction();
                
                for(Consumo consumo : lista) {
                    consumoDao.eliminar(sesion, consumo);
                }
                    
                sesion.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (consumoDao != null) {
                consumoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }
    
    
    /**
     * Elimina la lista de consumos.
     * @param sesion Sesion
     * @param lista List< Consumo >
     * @throws Exception 
     */
    public void eliminar(Session sesion, List<Consumo> lista) throws Exception {
        ConsumoDAO   consumoDao     = null;
        
        try {
            
            if (lista != null && !lista.isEmpty()) {
                consumoDao = new ConsumoDAO();
                
                for(Consumo consumo : lista) {
                    consumoDao.eliminar(sesion, consumo);
                }

            }
        } catch (Exception ex) {
            if (consumoDao != null) {
                consumoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } 
    }
    
    /**
     * Inserta la lista de consumos.
     * @param lista List< Consumo >
     * @throws Exception 
     */
    public void insertar(List<Consumo> lista) throws Exception {
        Session      sesion         = null;
        ConsumoDAO   consumoDao     = null;
        
        try {
            
            if (lista != null && !lista.isEmpty()) {
                consumoDao = new ConsumoDAO();
                sesion = HibernateUtil.getSession();
                sesion.beginTransaction();
                
                for(Consumo consumo : lista) {
                    consumoDao.insertar(sesion, consumo);
                }
                    
                sesion.getTransaction().commit();
            }
        } catch (Exception ex) {
            if (consumoDao != null) {
                consumoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }
    
    /**
     * Modifica la reserva con los consumos actualizados.
     * @param reservaCab Reserva actual
     * @throws java.lang.Exception 
     */
    public void modificarConsumos(ReservaCab reservaCab) throws Exception {
        Session         sesion         = null;
        ReservaCabDAO   reservaCabDao  = null;
        ConsumoDAO      consumoDao     = null;
        
        try {
            reservaCabDao = new ReservaCabDAO();
            consumoDao = new ConsumoDAO();
            
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();

            // Borrado de los consumos a eliminar.
            for (Consumo consumo : reservaCab.getConsumosBorrar()) {
                consumoDao.eliminar(sesion, consumo);
            }
            
            reservaCabDao.modificar(sesion, reservaCab);
            
            sesion.getTransaction().commit();
            
        } catch (Exception ex) {
            if (reservaCabDao != null) {
                reservaCabDao.rollback(sesion);
            }
            if (consumoDao != null) {
                consumoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);

        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }
  
}
