
package reservashotel.presentation.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.CargoService;
import reservashotel.business.vo.CargoFiltro;
import reservashotel.persistence.entities.Cargo;
import reservashotel.presentation.util.ConstantesErrores;

import reservashotel.presentation.util.JsfUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de los cargos.
 */
@ManagedBean
@SessionScoped
public class CargoController {

    private final   CargoService      servicio        = new CargoService();
    private         CargoFiltro       filtro          = new CargoFiltro();
    private         List<Cargo>       lista           = new ArrayList<Cargo>();
    private         Cargo             cargoActual;

    /**
    * Creates a new instance of CargoController
    */
    public CargoController() {
    }

    /**
     * getCargoActual
     * @return cargoActual
     */
    public Cargo getCargoActual() {
        return this.cargoActual;
    }

    /**
     * setCargoActual
     * @param cargoActual Cargo
     */
    public void setCargoActual(Cargo cargoActual) {
        this.cargoActual = cargoActual;
    }
    
    /**
     * CargoFiltro
     * @return CargoFiltro
     */
    public CargoFiltro getFiltro() {
        return filtro;
    }
    
    /**
     * Carga la lista en la página
     * @return List< Cargo > 
     */
    public List<Cargo> getLista() {
        return this.lista;
    }
    
    /**
     * Crea la instancia del nuevo cargo.
     * @return Destino
     */
    public String doPrepararAlta() {
        this.cargoActual = new Cargo();
        
        return "alta";
    }

    /**
     * Inserta el cargo actual en el sistema.
     * @return Destino
     */
    public String doAlta() {
        String retorno;
        
        try {
            this.cargoActual.setCodigo(this.cargoActual.getCodigo().toUpperCase());
            this.servicio.insertar(this.cargoActual);
            this.lista = servicio.listar(this.filtro);

            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno= "listado";
            
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
        return "listado";
    }
    
    /**
     * Carga el cargo para la consulta.
     * @param cargo Cargo
     * @return Destino
     */
    public String doPrepararConsulta(Cargo cargo) {
        this.cargoActual = cargo;
        
        return "consulta";
    }
    
    /**
     * Carga el cargo para su moficiación.
     * @param cargo Cargo
     * @return Destino
     */
    public String doPrepararModificacion(Cargo cargo) {
        this.cargoActual = cargo;   

        return "modificacion";
    }
    
    /**
     * Modifica el cargo en el sistema.
     * @return Destino
     */
    public String doModificacion() {
        String retorno = null;
        
        try {
            this.servicio.modificar(cargoActual);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            retorno = "listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
        
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }
        
        return retorno;
    }

    /**
     * Elimina el cargo en el sistema.
     * @param cargo Cargo
     * @return Destino
     */
    public String doEliminar(Cargo cargo) {
        String retorno = null;
        
        try {
            this.servicio.eliminar(cargo);
            this.lista = servicio.listar(this.filtro);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            retorno = "listado";
            
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;
            
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
        filtro  = new CargoFiltro();
        lista   = null;

        return "/cargo/listado";
    }
    
    /**
     * Busca el cargo en base al filtro.
     * @return 
     */
    public String doBuscar() {
        String retorno = null;

        try {
            validacionesFiltro();
            
            this.lista = servicio.listar(this.filtro);
            
            retorno = "listado";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Realiza las validaciones de los datos introducidos en los filtros.
     * @throws InfoException 
     */
    private void validacionesFiltro() throws InfoException {
        if (this.filtro != null) {
            // Rango del precio.
            if ((this.filtro.getPrecioDesde() != null && this.filtro.getPrecioDesde().isNaN())
                    || (this.filtro.getPrecioHasta() != null && this.filtro.getPrecioHasta().isNaN())) {
                throw new InfoException(ConstantesErrores.PRECIO_VALORES_NO_VALIDOS);
            }
            
            if ((this.filtro.getPrecioDesde() != null && this.filtro.getPrecioDesde() < 0)
                    || (this.filtro.getPrecioHasta() != null && this.filtro.getPrecioHasta() < 0)) {
                throw new InfoException(ConstantesErrores.PRECIO_VALORES_NO_VALIDOS);
            }
            
            if (this.filtro.getPrecioDesde() != null && this.filtro.getPrecioHasta() != null
                    && this.filtro.getPrecioDesde() > this.filtro.getPrecioHasta()) {
                throw new InfoException(ConstantesErrores.PRECIOMIN_SUPERIOR_PRECIOMAX);
            }
        }
    }

}

