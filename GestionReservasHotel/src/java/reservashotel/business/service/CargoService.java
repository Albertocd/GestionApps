
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
import reservashotel.business.vo.CargoFiltro;
import reservashotel.persistence.dao.CargoDAO;
import reservashotel.persistence.entities.Cargo;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad Cargo.
 */
public class CargoService implements IGenericService<Cargo> {
    
    @Override
    public List<Cargo> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<Cargo> lista = new CargoDAO().listar(sesion, ConstantesService.ORDENACION_CODIGO);
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(Cargo cargo) throws Exception {
        Session    sesion     = null;
        CargoDAO   cargoDao   = null;
        
        try {
            cargoDao = new CargoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            cargoDao.insertar(sesion, cargo);
            
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (cargoDao != null) {
                cargoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.CODIGO_NOMBRE_EXISTE);
            
        } catch (Exception ex) {
            if (cargoDao != null) {  
                cargoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(Cargo cargo) throws Exception {
        Session    sesion     = null;
        CargoDAO   cargoDao   = null;
        
        try {
            cargoDao = new CargoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            cargoDao.modificar(sesion, cargo);
            
            sesion.getTransaction().commit();
         
        } catch (ConstraintViolationException ex) { 
            if (cargoDao != null) {
                cargoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.CODIGO_NOMBRE_EXISTE);
            
        } catch (StaleObjectStateException ex) {
            if (cargoDao != null) {  
                cargoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_YA_MODIFICADO);
            
        } catch (Exception ex) {
            if (cargoDao != null) {  
                cargoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(Cargo cargo) throws Exception {
        Session    sesion     = null;
        CargoDAO   cargoDao   = null;
        
        try {
            
            cargoDao = new CargoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            cargoDao.eliminar(sesion, cargo);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (cargoDao != null) {
                cargoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_UTILIZADO);
            
        } catch (Exception ex) {
            if (cargoDao != null) {
                cargoDao.rollback(sesion);
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
     * @param filtro CargoFiltro
     * @return List< Cargo > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Cargo> listar(CargoFiltro filtro) throws HibernateException, Exception {
        Session         sesion      =   null;
        CargoDAO        cargoDao    =   null;
        List<Cargo>     lista       =   null;
        
        try {
            
            cargoDao = new CargoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = cargoDao.listar(sesion, filtro, ConstantesService.ORDENACION_CODIGO);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (cargoDao != null) {
                cargoDao.rollback(sesion);
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
     * Obtiene el cargo que se corresponde con el código.
     * @param codigo
     * @return cargo
     * @throws HibernateException
     * @throws Exception 
     */
    public Cargo buscar(String codigo) throws HibernateException, Exception {
        Session         sesion      =   null;
        CargoDAO        cargoDao    =   null;
        Cargo           cargo       =   null;
        
        try {
            
            cargoDao = new CargoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            cargo = cargoDao.buscar(sesion, codigo);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (cargoDao != null) {
                cargoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        
        return cargo;
    }
}

