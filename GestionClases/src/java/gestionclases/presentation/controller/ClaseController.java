
package gestionclases.presentation.controller;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.exception.InfoException;
import gestionclases.business.service.ClaseService;
import gestionclases.business.vo.ClaseFiltro;
import gestionclases.persistence.entity.Clase;
import gestionclases.presentation.util.JsfUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author alberto
 */
@ManagedBean
@SessionScoped
public class ClaseController {

    private final   ClaseService       servicio        = new ClaseService();
    private         Clase              clase           = new Clase();
    private         List<Clase>        lista           = new ArrayList<Clase>();
    private         ClaseFiltro        filtro          = new ClaseFiltro();
    
    /**
     * Creates a new instance of ClaseController
     */
    public ClaseController() {
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }

    public List<Clase> getLista() {
        return lista;
    }

    public void setLista(List<Clase> lista) {
        this.lista = lista;
    }

    public ClaseFiltro getFiltro() {
        return filtro;
    }

    
    /**
     * Crea la instancia de la nueva clase.
     * @return destino
     */
    public String doPrepararAlta() {
        this.clase = new Clase();

        return "/clase/alta";
    }
    
    /**
     * Búsqueda de clases.
     * @return 
     */
    public String doBuscar() {
        String retorno = null;

        try {
            this.lista = servicio.listar(this.filtro);
            
            retorno = "/clase/listado";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Establece el destino en el listado.
     * @return 
     */
    public String doPrepararListadoVacio() {
        // Limpiar el filtro y la lista
        filtro  = new ClaseFiltro();
        lista   = null;

        return "/clase/listado";
    }

    
    /**
     * Carga la clase para su modificación.
     * @param clase Clase
     * @return destino
     */
    public String doPrepararModificacion(Clase clase) {
        this.clase = clase;   

        return "/clase/modificacion";
    }
    
    /**
     * Modifica la clase en el sistema.
     * @return destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            this.servicio.modificar(this.clase);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "/clase/listado";
        
        } catch (InfoException ex) {
            JsfUtil.mensajeInfo(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }
        
        return retorno;
    }

    /**
     * Elimina la clase en el sistema.
     * @param clase Clase
     * @return destino
     */
    public String doEliminar(Clase clase) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(clase);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "/clase/listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Inserta la clase actual en el sistema.
     * @return destino
     */
    public String doAlta() {
        String retorno;
        
        try {
            this.servicio.insertar(this.clase);
            this.lista = servicio.listar(this.filtro);

            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno= "/clase/listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
        } catch (InfoException ex) {
            JsfUtil.mensajeInfo(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
            
            retorno = null;
        }
        
        return retorno;
    }

    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doPrepararListado() {
        return "/clase/listado";
    }
    
}
