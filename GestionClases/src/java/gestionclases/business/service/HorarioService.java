
package gestionclases.business.service;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.service.generic.IGenericService;
import gestionclases.business.vo.HorarioFiltro;
import gestionclases.persistence.dao.HorarioDAO;
import gestionclases.persistence.entity.Horario;
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
public class HorarioService implements IGenericService<Horario> {

    @Override
    public List<Horario> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void insertar(Horario horario) throws Exception {
        Session      sesion      = null;
        HorarioDAO   horarioDao   = null;
        
        try {
            horarioDao = new HorarioDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            horarioDao.insertar(sesion, horario);
            
            sesion.getTransaction().commit();
            
        } catch (Exception ex) {
            if (horarioDao != null) {  
                horarioDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(Horario horario) throws Exception {
        Session      sesion      = null;
        HorarioDAO   horarioDao   = null;
        
        try {
            horarioDao = new HorarioDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            horarioDao.modificar(sesion, horario);
            
            sesion.getTransaction().commit();

        } catch (Exception ex) {
            if (horarioDao != null) {  
                horarioDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(Horario horario) throws Exception {
        Session      sesion      = null;
        HorarioDAO   horarioDao   = null;
        
        try {
            
            horarioDao = new HorarioDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            horarioDao.eliminar(sesion, horario);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (horarioDao != null) {
                horarioDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_UTILIZADO);
            
        } catch (Exception ex) {
            if (horarioDao != null) {
                horarioDao.rollback(sesion);
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
     * @param filtro HorarioFiltro
     * @return List< Horario > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Horario> listar(HorarioFiltro filtro) throws HibernateException, Exception {
        Session          sesion      =   null;
        HorarioDAO       horarioDao  =   null;
        List<Horario>    lista       =   null;
        
        try {
            
            horarioDao = new HorarioDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = horarioDao.listar(sesion, filtro, "hora");
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (horarioDao != null) {
                horarioDao.rollback(sesion);
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
