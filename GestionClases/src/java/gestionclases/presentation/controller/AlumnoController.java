
package gestionclases.presentation.controller;

import gestionclases.business.exception.ErrorException;
import gestionclases.business.exception.InfoException;
import gestionclases.business.service.AlumnoService;
import gestionclases.business.vo.AlumnoFiltro;
import gestionclases.persistence.entity.Alumno;
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
public class AlumnoController {

    private final   AlumnoService      servicio        = new AlumnoService();
    private         Alumno             alumno          = new Alumno();
    private         List<Alumno>       lista           = new ArrayList<Alumno>();
    private         AlumnoFiltro       filtro          = new AlumnoFiltro();
    

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public List<Alumno> getLista() {
        return lista;
    }

    public void setLista(List<Alumno> lista) {
        this.lista = lista;
    }

    public AlumnoFiltro getFiltro() {
        return filtro;
    }

    public void setFiltro(AlumnoFiltro filtro) {
        this.filtro = filtro;
    }
    
    /**
     * Crea la instancia del nuevo alumno.
     * @return destino
     */
    public String doPrepararAlta() {
        this.alumno = new Alumno();
        this.alumno.setFechaAlta(JsfUtil.fechaActual());
        this.alumno.setActivoSn(Boolean.TRUE);
        
        return "/alumno/alta";
    }
    
    /**
     * Búsqueda de alumnos.
     * @return 
     */
    public String doBuscar() {
        String retorno = null;

        try {
            this.lista = servicio.listar(this.filtro);
            
            retorno = "/alumno/listado";
            
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
        filtro  = new AlumnoFiltro();
        lista   = null;

        return "/alumno/listado";
    }
    
    /**
     * Carga el alumno para su moficiación.
     * @param alumno Alumno
     * @return destino
     */
    public String doPrepararModificacion(Alumno alumno) {
        this.alumno = alumno;   

        return "/alumno/modificacion";
    }
    
    /**
     * Modifica el alumno en el sistema.
     * @return destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            this.servicio.modificar(this.alumno);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "/alumno/listado";
        
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
     * Elimina el alumno en el sistema.
     * @param alumno Alumno
     * @return destino
     */
    public String doEliminar(Alumno alumno) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(alumno);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "/alumno/listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Inserta el alumno actual en el sistema.
     * @return destino
     */
    public String doAlta() {
        String retorno;
        
        try {
            this.servicio.insertar(this.alumno);
            this.lista = servicio.listar(this.filtro);

            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno= "/alumno/listado";
            
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
        return "/alumno/listado";
    }
}
