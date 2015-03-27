
package gestionclases.business.service;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.service.generic.IGenericService;
import gestionclases.persistence.dao.AccesoDAO;
import gestionclases.persistence.entity.Acceso;
import gestionclases.persistence.util.HibernateUtil;
import gestionclases.presentation.util.ConstantesErrores;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author alberto
 */
public class AccesoService implements IGenericService<Acceso>{

    @Override
    public List<Acceso> listar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertar(Acceso t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(Acceso t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Acceso t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Comprobación de que el el nombre de usuario y la contraseña se corresponde
     * con un usuario del sistema, y si éste se encuentra activo.
     * @param nombreUsuario String - Nombre de usuario.
     * @param contraseña String - Contraseña.
     * @return Usuario
     * @throws Exception Excepción.
     */
    public Acceso compruebaAcceso(String nombreUsuario, String contraseña) throws Exception {
        Acceso     acceso         = null;
        Session    sesion         = null;
        AccesoDAO  accesoDao      = null;
        
        try {    
            accesoDao = new AccesoDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();

            if (nombreUsuario == null || nombreUsuario.isEmpty() 
                    || contraseña == null || contraseña.isEmpty()) {
                throw new ErrorException(ConstantesErrores.USUARIO_CONTRASENA_VACIOS);
            }

            acceso = accesoDao.getAccesoBy(sesion, nombreUsuario);

            if (acceso != null) {
                if (!acceso.getContrasena().equalsIgnoreCase(contraseña)) { 
                    throw new ErrorException(ConstantesErrores.CONTRASENA_INCORRECTA);
                }
            } else { 
                throw new ErrorException(ConstantesErrores.USUARIO_NO_EXISTE);
            }

            sesion.getTransaction().commit();
               
        } catch (ErrorException ex) {
            if (accesoDao != null) {
                accesoDao.rollback(sesion);
            }
            throw ex;
           
        } catch (ExceptionInInitializerError ex) { 
            if (accesoDao != null) {
                accesoDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.NO_CONEXION_BBDD);
        }
        
        return acceso;
    }
}
