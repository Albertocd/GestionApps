
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
import reservashotel.business.vo.TipoHabitacionFiltro;
import reservashotel.persistence.dao.TipoHabitacionDAO;
import reservashotel.persistence.entities.TipoHabitacion;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad TipoHabitacion.
 */
public class TipoHabitacionService implements IGenericService<TipoHabitacion> {
    
    @Override
    public List<TipoHabitacion> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<TipoHabitacion> lista = new TipoHabitacionDAO().listar(sesion, ConstantesService.ORDENACION_NOMBRE);
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(TipoHabitacion tipoHab) throws Exception {
        Session             sesion          = null;
        TipoHabitacionDAO   tipoHabDao      = null;
        
        try {
            tipoHabDao = new TipoHabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            tipoHabDao.insertar(sesion, tipoHab);
            
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (tipoHabDao != null) {
                tipoHabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.CODIGO_NOMBRE_EXISTE);
            
        } catch (Exception ex) {
            if (tipoHabDao != null) {  
                tipoHabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(TipoHabitacion tipoHab) throws Exception {
        Session             sesion          = null;
        TipoHabitacionDAO   tipoHabDao      = null;
        
        try {
            tipoHabDao = new TipoHabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            tipoHabDao.modificar(sesion, tipoHab);
            
            sesion.getTransaction().commit();
         
        } catch (ConstraintViolationException ex) { 
            if (tipoHabDao != null) {
                tipoHabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.CODIGO_NOMBRE_EXISTE);
            
        } catch (StaleObjectStateException ex) {
            if (tipoHabDao != null) {  
                tipoHabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_YA_MODIFICADO);
            
        } catch (Exception ex) {
            if (tipoHabDao != null) {  
                tipoHabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(TipoHabitacion tipoHab) throws Exception {
        Session             sesion          = null;
        TipoHabitacionDAO   tipoHabDao      = null;
        
        try {
            
            tipoHabDao = new TipoHabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            tipoHabDao.eliminar(sesion, tipoHab);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (tipoHabDao != null) {
                tipoHabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_UTILIZADO);
            
        } catch (Exception ex) {
            if (tipoHabDao != null) {
                tipoHabDao.rollback(sesion);
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
     * @param filtro TipoHabitacionFiltro
     * @return List< TipoHabitacion > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<TipoHabitacion> listar(TipoHabitacionFiltro filtro) throws HibernateException, Exception {
        Session                 sesion      =   null;
        TipoHabitacionDAO       tipoHabDao  =   null;
        List<TipoHabitacion>    lista       =   null;
        
        try {
            
            tipoHabDao = new TipoHabitacionDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = tipoHabDao.listar(sesion, filtro, ConstantesService.ORDENACION_NOMBRE);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (tipoHabDao != null) {
                tipoHabDao.rollback(sesion);
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
