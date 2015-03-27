
package gestionclases.presentation.controller;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.exception.InfoException;
import gestionclases.business.service.AccesoService;
import gestionclases.persistence.entity.Acceso;
import gestionclases.presentation.util.JsfUtil;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alberto
 */
@ManagedBean
@SessionScoped
public class AccesoController {

    private final   AccesoService      servicio        = new AccesoService();
    private         Acceso             usuario         = new Acceso();

    /**
     * getUsuario
     * @return Usuario
     */
    public Acceso getUsuario() {
        return usuario;
    }

    /**
     * setUsuario
     * @param usuario usuario
     */
    public void setUsuario(Acceso usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Valida los datos de entrada del usuario, y si son correctos permite el acceso.
     * @return destino
     */
    public String doValidaUsuario() {
        String destino = null;
        
        try {
            String nombreUsuario    = this.getUsuario().getUsuario();
            String contrasena       = this.getUsuario().getContrasena();
                    
            // Comprobación de usuario y contraseña correctos.
            Acceso usuario = servicio.compruebaAcceso(nombreUsuario, contrasena);
            
            // Se establece el usuario en el controller.
            this.setUsuario(usuario);

            destino = "/alumno/listado";
            
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
