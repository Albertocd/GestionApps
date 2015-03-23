
package reservashotel.business.service;

import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.service.generic.ConstantesService;
import reservashotel.business.service.generic.IGenericService;
import reservashotel.business.vo.ReservaCabFiltro;
import reservashotel.persistence.dao.ReservaCabDAO;
import reservashotel.persistence.dao.ReservaDetDAO;
import reservashotel.persistence.entities.ReservaCab;
import reservashotel.persistence.entities.ReservaDet;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad ReservaCab.
 */
public class ReservaCabService implements IGenericService<ReservaCab> {
    
    @Override
    public List<ReservaCab> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<ReservaCab> lista = new ReservaCabDAO().listar(sesion, ConstantesService.ORDENACION_FECHAENTRADA);
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(ReservaCab reservaCab) throws Exception {
        Session         sesion          = null;
        ReservaCabDAO   reservaCabDao   = null;
        
        try {
            reservaCabDao = new ReservaCabDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            reservaCabDao.insertar(sesion, reservaCab);
            
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (reservaCabDao != null) {
                reservaCabDao.rollback(sesion);
            } 
            throw new ErrorException(ConstantesErrores.REFERENCIA_RESERVA_EXISTE);
            
        } catch (Exception ex) {
            if (reservaCabDao != null) {  
                reservaCabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(ReservaCab reservaCab) throws Exception {
        Session         sesion          = null;
        ReservaCabDAO   reservaCabDao   = null;
        ReservaDetDAO   reservaDetDao   = null;
        
        try {
            reservaCabDao = new ReservaCabDAO();
            reservaDetDao = new ReservaDetDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            // Borrado de los detalles a eliminar.
            for (ReservaDet detalle : reservaCab.getDetallesBorrar()) {
                reservaDetDao.eliminar(sesion, detalle);
            }
            
            reservaCabDao.modificar(sesion, reservaCab);
            
            sesion.getTransaction().commit();
            
        } catch (Exception ex) {
            if (reservaCabDao != null) {  
                reservaCabDao.rollback(sesion);
            }
            if (reservaDetDao != null) {  
                reservaDetDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(ReservaCab reservaCab) throws Exception {
        Session         sesion          = null;
        ReservaCabDAO   reservaCabDao   = null;
        
        try {
            
            reservaCabDao = new ReservaCabDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            reservaCabDao.eliminar(sesion, reservaCab);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (sesion != null) {
                sesion.getTransaction().rollback();
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_UTILIZADO);
            
        } catch (Exception ex) {
            if (reservaCabDao != null) {
                reservaCabDao.rollback(sesion);
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
     * @param filtro ReservaCabFiltro
     * @return List< ReservaCab > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<ReservaCab> listar(ReservaCabFiltro filtro) throws HibernateException, Exception {
        Session             sesion              =   null;
        ReservaCabDAO       reservaCabDao       =   null;
        List<ReservaCab>    lista               =   null;
        
        try {
            
            reservaCabDao = new ReservaCabDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = reservaCabDao.listar(sesion, filtro, ConstantesService.ORDENACION_FECHAENTRADA);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (reservaCabDao != null) {
                reservaCabDao.rollback(sesion);
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
     * Lista reservas para las cuales la fecha pasada por parámetro
     * se encuentra dentro del rango de fecha de entrada y de salida.
     * @param fecha Fecha
     * @return List< ReservaCab > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<ReservaCab> listarEnFecha(Date fecha) throws HibernateException, Exception {
        Session             sesion              =   null;
        ReservaCabDAO       reservaCabDao       =   null;
        List<ReservaCab>    lista               =   null;
        
        try {
            
            reservaCabDao = new ReservaCabDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = reservaCabDao.listar(sesion, fecha, ConstantesService.ORDENACION_FECHAREGISTRO);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (reservaCabDao != null) {
                reservaCabDao.rollback(sesion);
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
     * Busca reserva por la referencia.
     * @param referencia
     * @return reserva
     * @throws Exception 
     */
    public ReservaCab buscar(String referencia) throws Exception {
        Session         sesion          = null;
        ReservaCabDAO   reservaCabDao   = null;
        ReservaCab      reserva         = null;
        
        try {
            reservaCabDao = new ReservaCabDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            reserva = reservaCabDao.buscar(sesion, referencia);
            
            sesion.getTransaction().commit();
        } catch (Exception ex) {
            if (reservaCabDao != null) {  
                reservaCabDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        
        return reserva;
    }
}
