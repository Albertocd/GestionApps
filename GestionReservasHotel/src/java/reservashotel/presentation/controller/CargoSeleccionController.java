
package reservashotel.presentation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import reservashotel.business.service.CargoService;
import reservashotel.business.vo.CargoFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.entities.Cargo;
import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para la ventana de selección de cargos.
 */
@ManagedBean
@SessionScoped
public class CargoSeleccionController {

    private final   CargoService      servicio    = new CargoService(); 
    private         List<Cargo>       lista       = null;
    private         CargoFiltro       filtro      = new CargoFiltro();
    
    
    /**
     * getLista
     * @return lista de cargos
     */
    public List<Cargo> getLista() {
        return lista;
    }

    /**
     * getFiltro
     * @return filtro
     */
    public CargoFiltro getFiltro() {
        return filtro;
    }
    
    /**
     * Muestra la ventana de seleccion.
     */
    public void doPrepararSeleccion() {
        filtro  = new CargoFiltro();
        lista   = null;

        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentHeight", 350);
        options.put("contentWidth", 600);

        RequestContext.getCurrentInstance().openDialog("/cargo/seleccion", options, null);
    }
    
    /**
     * Rellena el listado segun el filtro establecido.
     * @return destino
     */
    public String doListar() {       
        String retorno = null;
        
        try {
            // Solamente se listan los registros activos.
            this.filtro.setActivoSn(ConstantesFiltro.ACTIVO_SI);
            lista = servicio.listar(this.filtro);
            
            retorno = "/cargo/seleccion";
        }
        catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Retorna a la llamada el cargo seleccionado en la ventana.
     * @param cargo 
     */
    public void doSeleccionar(Cargo cargo) {
        RequestContext.getCurrentInstance().closeDialog(cargo);
    }
    
    /**
     * Limpiar los filtros de la ventana y la lista.
     */
    public void doLimpiar() {
        this.filtro = new CargoFiltro();
        this.lista = null;
    }
}
