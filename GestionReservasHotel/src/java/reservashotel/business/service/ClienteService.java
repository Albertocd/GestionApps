
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
import reservashotel.business.vo.ClienteFiltro;
import reservashotel.persistence.dao.ClienteDAO;
import reservashotel.persistence.entities.Cliente;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad Cliente.
 */
public class ClienteService implements IGenericService<Cliente> {

    @Override
    public List<Cliente> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<Cliente> lista = new ClienteDAO().listar(sesion, ConstantesService.ORDENACION_APELLIDO1);
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(Cliente cliente) throws Exception {
        Session     sesion      = null;
        ClienteDAO  clienteDao  = null;
        
        try {
            clienteDao = new ClienteDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            clienteDao.insertar(sesion, cliente);
            
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) {   
            if (clienteDao != null) {  
                clienteDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.DNI_CLIENTE_EXISTE);
                
        } catch (Exception ex) {
            if (clienteDao != null) {  
                clienteDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void modificar(Cliente cliente) throws Exception {
        Session     sesion      = null;
        ClienteDAO  clienteDao  = null;
        
        try {
            clienteDao = new ClienteDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            clienteDao.modificar(sesion, cliente);
            
            sesion.getTransaction().commit();
            
        } catch (StaleObjectStateException ex) {
            if (clienteDao != null) {  
                clienteDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.REGISTRO_YA_MODIFICADO);
             
        } catch (ConstraintViolationException ex) { 
            if (clienteDao != null) {
                clienteDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.DNI_CLIENTE_EXISTE);
            
        } catch (Exception ex) {
            if (clienteDao != null) {  
                clienteDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
    }

    @Override
    public void eliminar(Cliente cliente) throws Exception {
        Session     sesion       = null;
        ClienteDAO  clienteDao   = null;
        
        try {
            
            clienteDao = new ClienteDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            clienteDao.eliminar(sesion, cliente);
            sesion.getTransaction().commit();
            
        } catch (ConstraintViolationException ex) { 
            if (clienteDao != null) {  
                clienteDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.CLIENTE_CON_RESERVAS);
            
        } catch (Exception ex) {
            if (clienteDao != null) {
                clienteDao.rollback(sesion);
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
     * @param filtro ClienteFiltro
     * @return List< Cliente > 
     * @throws HibernateException
     * @throws Exception 
     */
    public List<Cliente> listar(ClienteFiltro filtro) throws HibernateException, Exception {
        Session         sesion      =   null;
        ClienteDAO      clienteDao  =   null;
        List<Cliente>   lista       =   null;
        
        try {
            
            clienteDao = new ClienteDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            lista = clienteDao.listar(sesion, filtro, ConstantesService.ORDENACION_APELLIDO1);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (clienteDao != null) {
                clienteDao.rollback(sesion);
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
     * Obtiene el cliente que se corresponde con el DNI.
     * @param dni
     * @return cliente
     * @throws HibernateException
     * @throws Exception 
     */
    public Cliente buscar(String dni) throws HibernateException, Exception {
        Session         sesion      =   null;
        ClienteDAO      clienteDao  =   null;
        Cliente         cliente     =   null;
        
        try {
            
            clienteDao = new ClienteDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();
            
            cliente = clienteDao.buscar(sesion, dni);
            
            sesion.getTransaction().commit();
         
        } catch (Exception ex) {
            if (clienteDao != null) {
                clienteDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.ERROR_INDETERMINADO);
            
        } finally {
            if (sesion != null) {
                sesion.close();
            }
        }
        
        return cliente;
    }
}
