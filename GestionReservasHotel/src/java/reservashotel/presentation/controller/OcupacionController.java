
package reservashotel.presentation.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import reservashotel.business.service.OcupacionService;
import reservashotel.persistence.entities.Ocupacion;
import reservashotel.presentation.util.FechasUtil;
import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para el listado de ocupación.
 */
@ManagedBean
@SessionScoped
public class OcupacionController {

    private final   OcupacionService    servicio          = new OcupacionService();
    private         List<Ocupacion>     listaOcupacion    = new ArrayList<Ocupacion>();
    private         Date                fechaActual;
    
    
    /**
     * Creates a new instance of OcupacionController
     */
    public OcupacionController() {
    }

    public List<Ocupacion> getListaOcupacion() {
        return listaOcupacion;
    }

    public void setListaOcupacion(List<Ocupacion> listaOcupacion) {
        this.listaOcupacion = listaOcupacion;
    }
    
    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doListar() {

        try {
            this.fechaActual = FechasUtil.fechaActual();
            this.listaOcupacion = servicio.listar(this.fechaActual);

        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }

        return "/ocupacion/listado";
    }

    /**
     * Obtiene la fecha actual en formato cadena.
     * @return fecha actual.
     */
    public String getFechaActual() {
        this.fechaActual = FechasUtil.fechaActual();
        
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = formato.format(this.fechaActual);
        
        return fecha;
    }
    
    
}
