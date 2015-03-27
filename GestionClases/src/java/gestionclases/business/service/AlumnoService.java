
package gestionclases.business.service;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.service.generic.IGenericService;
import gestionclases.business.vo.AlumnoFiltro;
import gestionclases.persistence.dao.AlumnoDAO;
import gestionclases.persistence.entity.Alumno;
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
public class AlumnoService implements IGenericService<Alumno> {

    @Override
    public List<Alumno> listar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void insertar(Alumno alumno) throws Exception {
        Session     sesion      = null;
        AlumnoDAO   alumnoDao   = null;
        
        try {
            alumnoDao = new AlumnoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            alumnoDao.insertar(sesion, alumno);
            
            sesion.getTransaction().commit();
            
        } catch (Exception ex) {
            if (alumnoDao != null) {  
                alumnoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(Alumno alumno) throws Exception {
        Session     sesion      = null;
        AlumnoDAO   alumnoDao   = null;
        
        try {
            alumnoDao = new AlumnoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            alumnoDao.modificar(sesion, alumno);
            
            sesion.getTransaction().commit();

        } catch (Exception ex) {
            if (alumnoDao != null) {  
                alumnoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(Alumno alumno) throws Exception {
        Session     sesion      = null;
        AlumnoDAO   alumnoDao   = null;
        
        try {
            
            alumnoDao = new AlumnoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            alumnoDao.eliminar(sesion, alumno);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (alumnoDao != null) {
                alumnoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_UTILIZADO);
            
        } catch (Exception ex) {
            if (alumnoDao != null) {
                alumnoDao.rollback(sesion);
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
     * @param filtro AlumnoFiltro
     * @return List< Alumno > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Alumno> listar(AlumnoFiltro filtro) throws HibernateException, Exception {
        Session          sesion      =   null;
        AlumnoDAO        alumnoDao    =   null;
        List<Alumno>     lista       =   null;
        
        try {
            
            alumnoDao = new AlumnoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = alumnoDao.listar(sesion, filtro, "nombre");
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (alumnoDao != null) {
                alumnoDao.rollback(sesion);
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
