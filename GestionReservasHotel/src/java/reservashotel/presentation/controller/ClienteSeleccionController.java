
package reservashotel.presentation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import reservashotel.business.service.ClienteService;
import reservashotel.business.vo.ClienteFiltro;
import reservashotel.persistence.entities.Cliente;
import org.primefaces.context.RequestContext;
import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para la ventana de selección de cliente.
 */
@ManagedBean
@SessionScoped
public class ClienteSeleccionController {

    private final   ClienteService      servicio    = new ClienteService();
    private         List<Cliente>       lista       = null;
    private         ClienteFiltro       filtro      = new ClienteFiltro();

    /**
     * getLista
     * @return lista de clientes
     */
    public List<Cliente> getLista() {
        return lista;
    }

    /**
     * getFiltro
     * @return filtro
     */
    public ClienteFiltro getFiltro() {
        return filtro;
    }
    
    /**
     * Muestra la ventana de seleccion.
     */
    public void doPrepararSeleccion() {
        filtro  = new ClienteFiltro();
        lista   = null;
        
        Map<String,Object> options = new HashMap<>();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentHeight", 450);
        options.put("contentWidth", 850);
         
        RequestContext.getCurrentInstance().openDialog("/cliente/seleccion", options, null);
    }
    
    /**
     * Rellena el listado segun el filtro establecido.
     * @return destino
     */
    public String doListar() {       
        String retorno = null;
        
        try {
            lista = servicio.listar(this.filtro);
            
            retorno = "/cliente/seleccion";
        }
        catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Retorna a la llamada el cliente seleccionado en la ventana.
     * @param cliente 
     */
    public void doSeleccionar(Cliente cliente) {
        RequestContext.getCurrentInstance().closeDialog(cliente);
    }
    
    /**
     * Limpiar los filtros de la ventana y la lista.
     */
    public void doLimpiar() {
        this.filtro = new ClienteFiltro();
        this.lista = null;
    }
}
