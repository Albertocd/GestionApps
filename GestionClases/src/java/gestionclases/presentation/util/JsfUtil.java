
package gestionclases.presentation.util;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author alberto
 * Métodos útiles para las interfaces de usuario.
 */
public class JsfUtil {

    /**
     * Muestra mensaje de información.
     * @param msg Mensaje
     */
    public static void mensajeInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
    }
     
    /**
     * Muestra mensaje de aviso.
     * @param msg Mensaje
     */
    public static void mensajeAviso(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
    }
    
    /**
     * Muestra mensaje de error.
     * @param msg Mensaje
     */
    public static void mensajeError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
    }
    
    /**
     * Muestra mensaje más severo que el grado de error.
     * @param msg Mensaje
     */
    public static void mensajeFatal(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg));
    }
    
    /**
     * Obtiene el mensjae de error correspondiente al código pasado por parámetro.
     * @param codigo
     * @return cadena
     */
    public static String getMessageError(String codigo)
    {
        String msg;

        ResourceBundle rb = ResourceBundle.getBundle("gestionclases.presentation.util.messages_error");

        msg = rb.getString(codigo);

        return msg;
    }
    
    
    /**
     * Obtiene la fecha actual.
     * @return 
     */
    public static Date fechaActual() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }
}
