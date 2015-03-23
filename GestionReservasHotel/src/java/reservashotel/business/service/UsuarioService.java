
package reservashotel.business.service;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.generic.ConstantesService;
import reservashotel.business.service.generic.IGenericService;
import reservashotel.persistence.dao.UsuarioDAO;
import reservashotel.persistence.entities.Usuario;
import reservashotel.persistence.util.HibernateUtil;
import reservashotel.presentation.util.ConstantesErrores;

/**
 * @author alberto
 * Clase para las funcionalidades de la entidad Usuario.
 */
public class UsuarioService implements IGenericService<Usuario> {

    
    /**
     * Comprobación de que el el nombre de usuario y la contraseña se corresponde
     * con un usuario del sistema, y si éste se encuentra activo.
     * @param nombreUsuario String - Nombre de usuario.
     * @param contraseña String - Contraseña.
     * @return Usuario
     * @throws InfoException Excepción.
     */
    public Usuario compruebaAcceso(String nombreUsuario, String contraseña) throws Exception {
        Usuario     usuario         = null;
        Session     sesion          = null;
        UsuarioDAO  usuarioDao      = null;
        
        try {    
            usuarioDao = new UsuarioDAO();
            sesion = HibernateUtil.getSession();
            sesion.beginTransaction();

            if (nombreUsuario == null || nombreUsuario.isEmpty() 
                    || contraseña == null || contraseña.isEmpty()) {
                throw new InfoException(ConstantesErrores.USUARIO_CONTRASEÑA_VACIOS);
            }

            usuario = usuarioDao.getUsuarioBy(sesion, nombreUsuario);

            if (usuario != null) {

                if (!usuario.getContrasena().equalsIgnoreCase(contraseña)) { 
                    throw new ErrorException(ConstantesErrores.CONTRASEÑA_INCORRECTA);
                }

                if (!usuario.getActivoSn()) { 
                    throw new ErrorException(ConstantesErrores.USUARIO_NO_ACTIVO);
                }

            } else { 
                throw new ErrorException(ConstantesErrores.USUARIO_NO_EXISTE);
            }

            sesion.getTransaction().commit();
               
        } catch (ErrorException ex) {
            if (usuarioDao != null) {
                usuarioDao.rollback(sesion);
            }
            throw ex;
           
        } catch (ExceptionInInitializerError ex) { 
            if (usuarioDao != null) {
                usuarioDao.rollback(sesion);
            }
            throw new ErrorException(ConstantesErrores.NO_CONEXION_BBDD);
        }
        
        return usuario;
    }

    

    /**
     * insertar
     * @param t 
     */
    @Override
    public void insertar(Usuario t) {
        throw new UnsupportedOperationException("Función no implementada.");
    }
    
    @Override
    public void modificar(Usuario t) {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public void eliminar(Usuario t) {
        throw new UnsupportedOperationException("Función no implementada."); 
    }

    @Override
    public List<Usuario> listar() {
        throw new UnsupportedOperationException("Función no implementada.");
    }
}
