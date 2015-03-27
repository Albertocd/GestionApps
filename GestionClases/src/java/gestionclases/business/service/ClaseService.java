
package gestionclases.business.service;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.service.generic.IGenericService;
import gestionclases.business.vo.ClaseFiltro;
import gestionclases.persistence.dao.ClaseDAO;
import gestionclases.persistence.entity.Clase;
import gestionclases.persistence.util.HibernateUtil;
import gestionclases.presentation.util.ConstantesErrores;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author alberto
 */
public class ClaseService implements IGenericService<Clase> {

    @Override
    public List<Clase> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<Clase> lista = new ClaseDAO().listar(sesion, "horario");
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(Clase clase) throws Exception {
        Session         sesion          = null;
        ClaseDAO        claseDao        = null;
        
        try {
            claseDao = new ClaseDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            claseDao.insertar(sesion, clase);
            
            sesion.getTransaction().commit();
            
        } catch (Exception ex) {
            if (claseDao != null) {  
                claseDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(Clase clase) throws Exception {
        Session         sesion          = null;
        ClaseDAO        claseDao        = null;
        
        try {
            claseDao = new ClaseDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            claseDao.modificar(sesion, clase);
            
            sesion.getTransaction().commit();

        } catch (Exception ex) {
            if (claseDao != null) {  
                claseDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(Clase clase) throws Exception {
        Session         sesion          = null;
        ClaseDAO        claseDao        = null;
        
        try {
            
            claseDao = new ClaseDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            claseDao.eliminar(sesion, clase);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (claseDao != null) {
                claseDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_UTILIZADO);
            
        } catch (Exception ex) {
            if (claseDao != null) {
                claseDao.rollback(sesion);
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
     * @param filtro ClaseFiltro
     * @return List< Clase > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Clase> listar(ClaseFiltro filtro) throws HibernateException, Exception {
        Session          sesion      =   null;
        ClaseDAO         claseDao    =   null;
        List<Clase>      lista       =   null;
        
        try {
            
            claseDao = new ClaseDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = claseDao.listar(sesion, filtro, "horario");
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (claseDao != null) {
                claseDao.rollback(sesion);
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
