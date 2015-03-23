
package reservashotel.presentation.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.UsuarioService;
import reservashotel.persistence.entities.Usuario;

import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de los usuarios.
 */
@ManagedBean
@SessionScoped
public class UsuarioController {

    private final   UsuarioService      servicio        = new UsuarioService();
    private         Usuario             usuarioActual   = new Usuario();
    
    /**
     * Creates a new instance of UsuarioController
     */
    public UsuarioController() {
    }

    /**
     * getUsuarioActual
     * @return Usuario
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * setUsuarioActual
     * @param usuarioActual Usuario
     */
    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
    
    /**
     * Valida los datos de entrada del usuario, y si son correctos permite el acceso.
     * @return destino
     */
    public String doValidaUsuario() {
        String destino = null;
        
        try {
            String nombreUsuario    = this.getUsuarioActual().getNombreUsuario();
            String contraseña       = this.getUsuarioActual().getContrasena();
                    
            // Comprobación de usuario y contraseña correctos.
            Usuario usuario = servicio.compruebaAcceso(nombreUsuario, contraseña);
            
            // Se establece el usuario en el controller.
            this.setUsuarioActual(usuario);

            destino = "/reserva/listado";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeFatal(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
            
        }
        
        return destino;
    }
    
    /**
     * Cierra la sesión abierta y redirije al login.
     */
    public void doCerrarSesion() {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

        try {
            ((HttpSession) ctx.getSession(false)).invalidate();
            ctx.redirect(ctxPath + "/faces/index.xhtml");
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
    }
}
