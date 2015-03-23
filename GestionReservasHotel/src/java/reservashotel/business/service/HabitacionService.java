
package reservashotel.business.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.exception.ConstraintViolationException;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.generic.ConstantesService;
import reservashotel.business.service.generic.IGenericService;
import reservashotel.business.vo.BusquedaHabitacionFiltro;
import reservashotel.business.vo.HabitacionFiltro;
import reservashotel.persistence.dao.HabitacionDAO;
import reservashotel.persistence.entities.Habitacion;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad Habitacion.
 */
public class HabitacionService implements IGenericService<Habitacion> {
    
    @Override
    public List<Habitacion> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<Habitacion> lista = new HabitacionDAO().listar(sesion, ConstantesService.ORDENACION_CODIGO);
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(Habitacion habitacion) throws Exception {
        Session         sesion          = null;
        HabitacionDAO   habitacionDao   = null;
        
        try {
            habitacionDao = new HabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            habitacionDao.insertar(sesion, habitacion);
            
            sesion.getTransaction().commit();
        
        } catch (ConstraintViolationException ex) {
            if (habitacionDao != null) {  
                habitacionDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.CODIGO_EXISTENTE);
            
        } catch (Exception ex) {
            if (habitacionDao != null) {  
                habitacionDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(Habitacion habitacion) throws Exception {
        Session         sesion          = null;
        HabitacionDAO   habitacionDao   = null;
        
        try {
            habitacionDao = new HabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            habitacionDao.modificar(sesion, habitacion);
            
            sesion.getTransaction().commit();
            
        } catch (StaleObjectStateException ex) {
            if (habitacionDao != null) {  
                habitacionDao.rollback(sesion);
            }
            throw new InfoException(ConstantesErrores.REGISTRO_YA_MODIFICADO);
        
        } catch (ConstraintViolationException ex) {
            if (habitacionDao != null) {  
                habitacionDao.rollback(sesion);
            }
            throw new InfoException(ConstantesErrores.CODIGO_EXISTENTE);
           
        } catch (Exception ex) {
            if (habitacionDao != null) {  
                habitacionDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(Habitacion habitacion) throws Exception {
        Session         sesion          = null;
        HabitacionDAO   habitacionDao   = null;
        
        try {
            
            habitacionDao = new HabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            habitacionDao.eliminar(sesion, habitacion);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (habitacionDao != null) {  
                habitacionDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.HABITACION_EN_RESERVAS);
            
        } catch (Exception ex) {
            if (habitacionDao != null) {
                habitacionDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }
    
    /**
     * Lista resultados en base al filtro pasado por parámetro.
     * @param filtro HabitacionFiltro
     * @return List< Habitacion > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Habitacion> listar(HabitacionFiltro filtro) throws HibernateException, Exception {
        Session             sesion          =   null;
        HabitacionDAO       habitacionDao   =   null;
        List<Habitacion>    lista           =   null;
        
        try {
            
            habitacionDao = new HabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = habitacionDao.listar(sesion, filtro, ConstantesService.ORDENACION_CODIGO);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (habitacionDao != null) {
                habitacionDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        
        return lista;
    }
    
    /**
     * Obtiene las habitaciones libres para las condiciones establecidas en el filtro.
     * @param filtro BusquedaHabitacionFiltro
     * @return List< Habitacion >
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Habitacion> listarLibres(BusquedaHabitacionFiltro filtro) throws HibernateException, Exception {
        Session             sesion          =   null;
        HabitacionDAO       habitacionDao   =   null;
        List<Habitacion>    lista           =   null;
        
        try {
            
            habitacionDao = new HabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = habitacionDao.listarLibres(sesion, filtro);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (habitacionDao != null) {
                habitacionDao.rollback(sesion);
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
