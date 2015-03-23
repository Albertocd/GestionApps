
package reservashotel.presentation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.CargoService;
import reservashotel.business.service.ConsumoService;
import reservashotel.business.service.ReservaCabService;
import reservashotel.business.vo.CargoFiltro;
import reservashotel.business.vo.ConsumoFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.entities.Cargo;
import reservashotel.persistence.entities.Consumo;
import reservashotel.persistence.entities.ReservaCab;
import reservashotel.persistence.entities.ReservaDet;
import reservashotel.presentation.util.ConstantesErrores;
import reservashotel.presentation.util.FechasUtil;
import reservashotel.presentation.util.JsfUtil;
import reservashotel.presentation.util.ReservasUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de los consumos.
 */
@ManagedBean
@SessionScoped
public class ConsumoController {

    private final   ConsumoService          servicioConsumo      = new ConsumoService();
    private final   ReservaCabService       servicioReserva      = new ReservaCabService();
    private final   CargoService            servicioCargo        = new CargoService();
    private         List<Cargo>             listaCargos          = new ArrayList<Cargo>();
    private         List<Consumo>           listaConsumos        = new ArrayList<Consumo>();
    private         CargoFiltro             filtroCargo          = new CargoFiltro();
    private         ConsumoFiltro           filtroConsumo        = new ConsumoFiltro();
    private         Consumo                 consumoActual;
    private         Cargo                   cargoActual;
    private         ReservaCab              reservaActual;
    private         Float                   importeTotal;
    

    
    public ConsumoController() {
        this.cargoActual = new Cargo();
        this.consumoActual = new Consumo();
        this.consumoActual.setCargo(this.cargoActual);
    }

    /**
     * Propiedad para la lista de cargos.
     * @return lista de cargos.
     */
    public List<Cargo> getListaCargos() {
        return this.listaCargos;
    }

    /**
     * Obtiene el filtro de cargos.
     * @return filtro del cargo
     */
    public CargoFiltro getFiltroCargo() {
        return filtroCargo;
    }

    /**
     * Obtiene el filtro de consumos.
     * @return filtro del consumo
     */
    public ConsumoFiltro getFiltroConsumo() {
        return filtroConsumo;
    }
    
    /**
     * Propiedad para el consumo actual.
     * @return consumoActual
     */
    public Consumo getConsumoActual() {
        return consumoActual;
    }

    /**
     * Propiedad para el importe total.
     * @return importeTotal
     */
    public Float getImporteTotal() {
        return importeTotal;
    }

    /**
     * Propiedad para setear el consumo actual.
     * @param consumoActual 
     */
    public void setConsumoActual(Consumo consumoActual) {
        this.consumoActual = consumoActual;
    }

    /**
     * Obtiene la lista de consumos de la reserva. 
     * @return List< Consumo >
     */
    public List<Consumo> getListaConsumos() {
        return listaConsumos;
    }
    
    /**
     * Propiedad para el cargo actual.
     * @return cargoActual
     */
    public Cargo getCargoActual() {
        return cargoActual;
    }
    
    /**
     * Propiedad para la reserva actual.
     * @return reserva seleccionada.
     */
    public ReservaCab getReservaActual() {
        return reservaActual;
    }

    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doPrepararListadoVacio() {
        // Limpiar la lista.
        this.listaConsumos = null;
        this.importeTotal = 0.0F;
        
        return "listado";
    }
    
    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doPrepararListado() {
        return "listado";
    }
    
    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doPrepararListadoVacioAlta() {
        // Limpiar la lista.
        this.listaCargos   = null;

        return "alta";
    }
    
    
    /**
     * Crea la instancia del nuevo consumo.
     * @return destino
     */
    public String doPrepararNuevoConsumo() {
        this.cargoActual    = new Cargo();
        this.consumoActual  = new Consumo();
        this.consumoActual.setCargo(cargoActual);
        this.consumoActual.setFechaConsumo(FechasUtil.fechaActual());
        this.consumoActual.setCantidad(1);
        
        return "alta";
    }
    
    /**
     * Busca el cliente en base al codigo insertado.
     */
    public void doCargoBuscar(){
        
        try {
            String codigo = this.consumoActual.getCargo().getCodigo();
            Cargo cargo = servicioCargo.buscar(codigo);
            
            if (cargo == null) {
                this.consumoActual.setCargo(new Cargo());
                throw new ErrorException(ConstantesErrores.CODIGO_CARGO_NO_EXISTE);
            }
            
            this.consumoActual.setCargo(cargo);
            this.cargoActual = cargo;
        
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
    }
    
    /**
     * Establece el cargo activo.
     * @param event 
     */
    public void doCargoSeleccionado(SelectEvent event) {
        Cargo cargo = (Cargo) event.getObject();
        this.cargoActual = cargo;
        this.consumoActual.setCargo(cargo);
    } 
    
    /**
     * Crea los consumos y los adjunta a la reserva.
     * @return destino
     */
    public String doGuardar() {
        String retorno;
        
        try {
            // Actualizar el importe total de la reserva.
            this.actualizarImporteTotal();
            
            // Pase de lista a conjunto y asignación a la reserva.
            Set<Consumo> conjuntoConsumos = new HashSet<Consumo>();
            List<Consumo> listaConsumos = this.listaConsumos;
            for(Consumo consumo : listaConsumos) {
                conjuntoConsumos.add(consumo);
            }
            this.reservaActual.setConsumos(conjuntoConsumos);
            
            this.servicioConsumo.modificarConsumos(this.reservaActual);

            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            this.cargoActual = new Cargo();
            this.consumoActual = new Consumo();
            this.consumoActual.setCargo(this.cargoActual);
            // Se resetea la lista de detalles a borrar de la reserva.
            this.reservaActual.setConsumosBorrar(new HashSet<Consumo>());
            
            retorno= "listado";

        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
            
            retorno = null;
        }
        
        return retorno;
    }         
    
    /**
     * Crea un nuevo consumo para la reserva con los datos insertados y
     * retorna al listado de consumos.
     * @return destino
     */
    public String doNuevoConsumo() {
        boolean agregar = false;
        String retorno = null;
        
        try {
            Date fechaConsumo = FechasUtil.quitaHorasFecha(this.consumoActual.getFechaConsumo());
            Date fechaEntrada = FechasUtil.quitaHorasFecha(this.reservaActual.getFechaEntrada());
            Date fechaSalida  = FechasUtil.quitaHorasFecha(this.reservaActual.getFechaSalida());

            if ((!fechaConsumo.after(fechaSalida) 
                    && !fechaConsumo.before(fechaEntrada))) {
                int cantidad        = this.consumoActual.getCantidad();

                this.consumoActual.setCargo(this.cargoActual);
                this.consumoActual.setReservaCab(this.reservaActual);

                // Se recorre la lista para actualizar las nuevas cantidades en caso de que
                // el cargo ya exista en la lista, siempre que la fecha de consumo sea diferente.
                if (this.listaConsumos != null && !this.listaConsumos.isEmpty()) {
                    for(Consumo consumo : this.listaConsumos) {
                        if (consumo.getCargo().equals(this.cargoActual)
                                && fechaConsumo.equals(consumo.getFechaConsumo())) {
                            this.consumoActual.setCantidad(cantidad + consumo.getCantidad());
                        } else {
                            agregar = true;
                        }
                    }
                } else {
                    this.listaConsumos.add(this.consumoActual);
                }

                if (agregar) {
                   this.listaConsumos.add(this.consumoActual); 
                }

                doCalculaImporteConsumos();

                retorno = "listado";
            } else {
                JsfUtil.mensajeError("No se permite imputar consumos fuera del rango de fechas de la reserva.");
                retorno = null;
            }
        } catch (Exception ex) {
            JsfUtil.mensajeError("Se ha producido un error al realizar comprobaciones de fechas.");
            retorno = null;
        }
            
        return retorno;
    }
    
    /**
     * Elimina de la lista el consumo pasado por parámetro.
     * @param consumo 
     */
    public void doEliminar(Consumo consumo) {
        if (this.listaConsumos != null && !this.listaConsumos.isEmpty() 
                && this.listaConsumos.contains(consumo)) {
            this.listaConsumos.remove(consumo);
        }
        
        // Se inserta en la lista de detalles a borrar.
        this.reservaActual.getConsumosBorrar().add(consumo);
        
        doCalculaImporteConsumos();
    }
    
    /**
     * Establece el cargo seleccionado como el actual.
     * @param cargo 
     */
    public void doSeleccionar(Cargo cargo) {
        this.cargoActual = cargo;
    }
    
     /**
     * Busca cargos en base al filtro.
     * @return destino
     */
    public String doBuscarCargos() {
        String retorno = null;

        try {
            this.filtroCargo.setActivoSn(ConstantesFiltro.ACTIVO_SI);
            this.listaCargos = servicioCargo.listar(this.filtroCargo);
            
            retorno = "alta";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(ex.getMessage());
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
        return retorno;
    }
   
    /**
     * Actualiza el importe total de la reserva incluyendo los consumos.
     */
    public void actualizarImporteTotal() {
        Float importeTotal = 0.0F;
        Float importeHab   = 0.0F;
        Float importeCon   = 0.0F;
        
        int numNoches = ReservasUtil.calculaNumeroNoches(this.reservaActual);
        
        // Importe subtotal de los consumos.
        if (this.listaConsumos != null 
                && !this.listaConsumos.isEmpty()) {
            for(Consumo consumo : this.listaConsumos) {
                importeCon = importeCon + (consumo.getCargo().getPrecio() * consumo.getCantidad());
            }
        }
        
        // Importe subtotal de las habitaciones.
        if(this.reservaActual.getDetalles() != null 
                && !this.reservaActual.getDetalles().isEmpty()) {
            for(ReservaDet detalle : this.reservaActual.getDetalles()) {
                float precio = detalle.getHabitacion().getTipoHabitacion().getPrecio();
                importeHab = importeHab + (precio * numNoches);
            }
        }
        
        importeTotal = importeHab + importeCon;
        
        this.reservaActual.setImporteTotal(importeTotal);
    }
    
    /**
     * Busca la reserva en base a la referencia.
     */
    public void doBuscarxRef(){
        
        try {
            String ref = this.filtroConsumo.getReferencia();
            ReservaCab reserva = servicioReserva.buscar(ref);
            
            if (reserva == null) {
                this.reservaActual.setConsumos(null);
                throw new ErrorException(JsfUtil.getMessageError(ConstantesErrores.REFERENCIA_RESERVA_NO_EXISTE));
            }
            
            this.reservaActual = reserva;
        
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
        
    }
    
    /**
     * Redirecciona a la página de consumos de la reserva.
     * @param reserva
     * @return destino
     */
    public String doListadoConsumos(ReservaCab reserva) {
        this.reservaActual = reserva;
        
        // Pase de conjunto a lista.
        Set<Consumo>  conjuntoConsumos = this.reservaActual.getConsumos();
        List<Consumo> lista = new ArrayList<Consumo>();
        for(Consumo consumo : conjuntoConsumos) {
            lista.add(consumo);
        }
        this.listaConsumos = lista;
        
        this.doCalculaImporteConsumos();
        
        return "/consumo/listado";
    }
    
    /**
     * Calcula el importe total de los consumos listados y actualiza el valor.
     */
    public void doCalculaImporteConsumos() {
        Float importe = 0.0F;
        
        if(this.listaConsumos.size() > 0 ) {
            for(Consumo consumo : this.listaConsumos) {
                importe = importe + (consumo.getCantidad() * consumo.getCargo().getPrecio());
            }
        }
        
        this.importeTotal = importe;
    }
    
   
}
