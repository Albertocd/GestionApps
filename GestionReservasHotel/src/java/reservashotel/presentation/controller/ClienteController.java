
package reservashotel.presentation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.ClienteService;
import reservashotel.business.vo.ClienteFiltro;
import reservashotel.persistence.entities.Cliente;
import reservashotel.presentation.util.ConstantesErrores;

import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de los clientes.
 */
@ManagedBean
@SessionScoped
public class ClienteController {

    private final   ClienteService      servicio        = new ClienteService();
    private         ClienteFiltro       filtro          = new ClienteFiltro();
    private         List<Cliente>       lista           = new ArrayList<Cliente>();
    private         Cliente             clienteActual;

    /**
    * Creates a new instance of ClienteController
    */
    public ClienteController() {
    }

    /**
     * getClienteActual
     * @return Cliente
     */
    public Cliente getClienteActual() {
        return this.clienteActual;
    }

    /**
     * setClienteActual
     * @param clienteActual Cliente
     */
    public void setClienteActual(Cliente clienteActual) {
        this.clienteActual = clienteActual;
    }

    /**
     * getFiltro
     * @return ClienteFiltro
     */
    public ClienteFiltro getFiltro() {
        return filtro;
    }

    /**
     * setFiltro
     * @param filtro ClienteFiltro
     */
    public void setFiltro(ClienteFiltro filtro) {
        this.filtro = filtro;
    }
    
    /**
     * Carga la lista en la página
     * @return List< Cliente > 
     */
    public List<Cliente> getLista() {
        return this.lista;
    }
    
    /**
     * Crea la instancia del nuevo cliente.
     * @return Destino
     */
    public String doPrepararAlta() {
        this.clienteActual = new Cliente();
        
        return "alta";
    }

    /**
     * Inserta el cliente actual en el sistema
     * @return Destino
     */
    public String doAlta() {
        try {
            validacionesDatos();
            
            this.clienteActual.setDni(this.clienteActual.getDni().toUpperCase());
            this.servicio.insertar(this.clienteActual);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            return "listado";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ConstantesErrores.ERROR_INDETERMINADO);
        }
        
        return null;
        
    }

    /**
     * Establece el destino en el listado.
     * @return 
     */
    public String doPrepararListado() {
        return "listado";
    }
    
    /**
     * Carga el cliente para la consulta.
     * @param cliente Cliente
     * @return Destino
     */
    public String doPrepararConsulta(Cliente cliente) {
        this.clienteActual = cliente;
        
        return "consulta";
    }
    
    /**
     * Carga el cliente para su moficiación.
     * @param cliente Cliente
     * @return Destino
     */
    public String doPrepararModificacion(Cliente cliente) {
        this.clienteActual = cliente;   

        return "modificacion";
    }
    
    /**
     * Modifica el cliente en el sistema.
     * @return Destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            validacionesDatos();
            
            this.servicio.modificar(clienteActual);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));    
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception e) {
            JsfUtil.mensajeError(ConstantesErrores.ERROR_INDETERMINADO);
        }
        
        return retorno;
    }

    /**
     * Elimina el cliente en el sistema.
     * @param cliente Cliente
     * @return Destino
     */
    public String doEliminar(Cliente cliente) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(cliente);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "listado";
            
        } catch (ErrorException ex) {
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
        filtro  = new ClienteFiltro();
        lista   = null;
        
        this.clienteActual = new Cliente();

        return "/cliente/listado";
    }
    
    public String doBuscar() {
        String retorno = null;

        try {
            this.lista = servicio.listar(this.filtro);
            
            retorno = "listado";
   
        } catch (Exception ex) {
            JsfUtil.mensajeError(ConstantesErrores.ERROR_INDETERMINADO);
        }
        
        return retorno;
    }
    
    /**
     * Realiza las validaciones de los datos en el alta.
     * @throws InfoException 
     */
    private void validacionesDatos() throws ErrorException {

        // Fecha de nacimiento.
        Date fechaActual = (Date)new GregorianCalendar().getTime();
        if ((this.clienteActual.getFechaNacimiento() != null &&
                this.clienteActual.getFechaNacimiento().after(fechaActual))) {
            throw new ErrorException(ConstantesErrores.FECHANAC_SUPERIOR_ACTUAL);
        }
        
    }
}
