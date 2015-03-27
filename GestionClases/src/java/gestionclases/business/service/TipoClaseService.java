
package gestionclases.business.service;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.service.generic.IGenericService;
import gestionclases.persistence.dao.TipoClaseDAO;
import gestionclases.persistence.entity.TipoClase;
import gestionclases.persistence.util.HibernateUtil;
import gestionclases.presentation.util.ConstantesErrores;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author alberto
 */
public class TipoClaseService implements IGenericService<TipoClase> {

    @Override
    public List<TipoClase> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<TipoClase> lista = new TipoClaseDAO().listar(sesion, "descripcion");
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(TipoClase tipoClase) throws Exception {
        Session         sesion          = null;
        TipoClaseDAO    tipoClaseDao    = null;
        
        try {
            tipoClaseDao = new TipoClaseDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            tipoClaseDao.insertar(sesion, tipoClase);
            
            sesion.getTransaction().commit();
            
        } catch (Exception ex) {
            if (tipoClaseDao != null) {  
                tipoClaseDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(TipoClase tipoClase) throws Exception {
        Session         sesion          = null;
        TipoClaseDAO    tipoClaseDao    = null;
        
        try {
            tipoClaseDao = new TipoClaseDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            tipoClaseDao.modificar(sesion, tipoClase);
            
            sesion.getTransaction().commit();

        } catch (Exception ex) {
            if (tipoClaseDao != null) {  
                tipoClaseDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(TipoClase tipoClase) throws Exception {
        Session         sesion          = null;
        TipoClaseDAO    tipoClaseDao    = null;
        
        try {
            
            tipoClaseDao = new TipoClaseDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            tipoClaseDao.eliminar(sesion, tipoClase);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (tipoClaseDao != null) {
                tipoClaseDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_UTILIZADO);
            
        } catch (Exception ex) {
            if (tipoClaseDao != null) {
                tipoClaseDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        } 
    }
    
}
