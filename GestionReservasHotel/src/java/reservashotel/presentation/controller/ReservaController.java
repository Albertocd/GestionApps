
package reservashotel.presentation.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.event.SelectEvent;
import reservashotel.business.exception.ErrorException;
import reservashotel.business.exception.InfoException;
import reservashotel.business.service.ClienteService;
import reservashotel.business.service.ReservaCabService;
import reservashotel.business.vo.ReservaCabFiltro;
import reservashotel.business.vo.generic.ConstantesFiltro;
import reservashotel.persistence.entities.Cliente;
import reservashotel.persistence.entities.Consumo;
import reservashotel.persistence.entities.Habitacion;
import reservashotel.persistence.entities.ReservaCab;
import reservashotel.persistence.entities.ReservaDet;
import reservashotel.presentation.util.ConstantesErrores;
import reservashotel.presentation.util.FechasUtil;
import reservashotel.presentation.util.JsfUtil;
import reservashotel.presentation.util.ReservasUtil;

/**
 * @author alberto
 * Controller para las funcionalidades de las reservas.
 */
@ManagedBean
@SessionScoped
public class ReservaController {

    private final   ReservaCabService      servicio             = new ReservaCabService();
    private final   ClienteService         servicioCliente      = new ClienteService();
    private         ReservaCabFiltro       filtro               = new ReservaCabFiltro();
    private         List<ReservaCab>       listaReservas        = new ArrayList<ReservaCab>();
    private         ReservaCab             reservaActual;
    private         Date                   fechaActual;
    private         List<ReservaDet>       listaTempDetalles    = new ArrayList<ReservaDet>();
    
    
    /**
    * Creates a new instance of ReservaCabController
    */
    public ReservaController() {
        this.reservaActual = new ReservaCab();
        this.reservaActual.setCliente(new Cliente());
        this.reservaActual.setImporteTotal(0.0F);
        this.reservaActual.setDetalles(new ArrayList<ReservaDet>(0));
    }
 
    /**
     * getReservaActual
     * @return reservaActual
     */
    public ReservaCab getReservaActual() {
        return this.reservaActual;
    }

    /**
     * setReservaActual
     * @param reservaActual ReservaCab
     */
    public void setReservaActual(ReservaCab reservaActual) {
        this.reservaActual = reservaActual;
    }

    /**
     * Recupera la lista temporal de habitaciones.
     * @return 
     */
    public List<ReservaDet> getListaTempDetalles() {
        return listaTempDetalles;
    }

    /**
     * Obtiene la fecha actual.
     * @return fecha actual
     */
    public Date getFechaActual() {
        return FechasUtil.fechaActual();
    }
    
    /**
     * getFiltro
     * @return ReservaCabFiltro
     */
    public ReservaCabFiltro getFiltro() {
        return filtro;
    }
    
    /**
     * Carga la lista en la página
     * @return lista de reservas
     */
    public List<ReservaCab> getListaReservas() {
        return this.listaReservas;
    }

    /**
     * getDetalles
     * @return Lista de detalles de la reserva (habitaciones).
     */
    public List<ReservaDet> getHabitaciones() {
        List<ReservaDet> detalles = this.reservaActual.getDetalles();
        List<ReservaDet> detallesRetorno = new ArrayList<ReservaDet>();
        
        for(ReservaDet detalle : detalles) {
            if (!detallesRetorno.contains(detalle)) {
                detallesRetorno.add(detalle);
            }
        }
        
        return detallesRetorno;
    }

    /**
     * getDetalles
     * @return Lista de consumos de la reserva (cargos).
     */
    public Set<Consumo> getConsumos() {
        return this.reservaActual.getConsumos();
    }
    
    /**
     * Crea la instancia dela nueva reserva.
     * @return destino
     */
    public String doPrepararAlta() {
        this.reservaActual = new ReservaCab();
        this.reservaActual.setCliente(new Cliente());
        this.listaTempDetalles = new ArrayList<ReservaDet>();
        this.reservaActual.setImporteTotal(0.0F);
        
        return "alta";
    }

    /**
     * Crea e inserta la reserva actual en el sistema.
     * @return destino
     */
    public String doNuevaReserva() {
        String destino = "";
        
        try {
            // Validaciones previas.
            if (this.listaTempDetalles.isEmpty()) {
                throw new ErrorException(ConstantesErrores.RESERVA_SIN_HABITACIONES);
            }
            if (this.reservaActual.getCliente() == null) {
                throw new ErrorException(ConstantesErrores.CLIENTE_NO_SELECCIONADO);
            }
            if (this.reservaActual.getImporteTotal() == null) {
                throw new ErrorException(ConstantesErrores.RESERVA_SIN_IMPORTE);
            }
            if (this.reservaActual.getEstadoReserva().getIdEstadoReserva() == ConstantesFiltro.ESTADO_RESERVA_CERRADA
                    || this.reservaActual.getEstadoReserva().getIdEstadoReserva() == ConstantesFiltro.ESTADO_RESERVA_ANULADA) {
                throw new InfoException(ConstantesErrores.CREAR_RESERVA_ESTADO_INCORRECTO);
            }
            
            validacionesCamposFechas();
            
            Date fechaRegistro = FechasUtil.fechaActual();
            
            // Creación de la reserva y guardado.
            this.reservaActual.setFechaRegistro(fechaRegistro);
            this.reservaActual.setUsuario(ReservasUtil.obtenerUsuario());
            this.reservaActual.setReferencia(ReservasUtil.generaReferencia(this.reservaActual,listaTempDetalles));
            this.reservaActual.setDetalles(listaTempDetalles);
            this.reservaActual.setTimestamp(new Timestamp(fechaRegistro.getTime()));
            
            this.servicio.insertar(this.reservaActual);
            
            JsfUtil.mensajeInfo("Operación realizada correctamente");
            
            destino = "listado";
        
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
            
        }
        
        return destino;
    }

    /**
     * Establece el destino en el listado.
     * @return destino
     */
    public String doPrepararListado() {
        return "/reserva/listado";
    }
    
    /**
     * Carga la reserva para la consulta.
     * @param reservaCab ReservaCab
     * @return destino
     */
    public String doPrepararConsulta(ReservaCab reservaCab) {
        this.reservaActual = reservaCab;
        
        this.calculaNumeroNochesConsulta();
        
        return "consulta";
    }
    
    /**
     * Carga el cargo para su moficiación.
     * @param reservaCab ReservaCab
     * @return destino
     */
    public String doPrepararModificacion(ReservaCab reservaCab) {
        this.reservaActual = reservaCab; 
        this.listaTempDetalles = reservaCab.getDetalles();
        
        int numeroNoches = FechasUtil.diferenciaDias(reservaCab.getFechaEntrada(), reservaCab.getFechaSalida());
        this.reservaActual.setNumeroNoches(numeroNoches);

        return "modificacion";
    }
    
    /**
     * Modifica el cargo en el sistema.
     * @return destino
     */
    public String doModificarReserva() {
        String retorno = null;
        
        try {
            // Validaciones previas.
            if (this.listaTempDetalles.isEmpty()) {
                throw new ErrorException(ConstantesErrores.RESERVA_SIN_HABITACIONES);
            }
            if (this.reservaActual.getCliente() == null) {
                throw new ErrorException(ConstantesErrores.CLIENTE_NO_SELECCIONADO);
            }
            if (this.reservaActual.getImporteTotal() == null) {
                throw new ErrorException(ConstantesErrores.RESERVA_SIN_IMPORTE);
            }
            
            // Asignación de los detalles temporales a la reserva definitiva.
            this.reservaActual.setDetalles(this.listaTempDetalles);
            
            // Se recalcula el importe por si se han modificado las habitaciones.
            this.doCalcularImporteReserva();

            // Modificación y recarga del listado.
            this.servicio.modificar(this.reservaActual);
            this.listaReservas = servicio.listar(this.filtro);
            
            // Se resetea la lista de detalles a borrar de la reserva.
            this.reservaActual.setDetallesBorrar(new ArrayList<ReservaDet>());
            
            JsfUtil.mensajeInfo("Operación realizada correctamente.");
            
            retorno = "listado";
         
        } catch (ErrorException | InfoException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
            retorno = null;    
            
        } catch (Exception e) {
            JsfUtil.mensajeError(e.getMessage());
        }
        
        return retorno;
    }
    
    /**
     * Establece el destino en el listado.
     * @return 
     */
    public String doPrepararListadoVacio() {
        // Limpiar el filtro y la lista
        filtro  = new ReservaCabFiltro();
        listaReservas   = null;

        return "/reserva/listado";
    }
    
    /**
     * Obtiene un listado de reservas en base al filtro establecido.
     * @return destino
     */
    public String doBuscar() {
        String retorno = null;

        try {
            // Validaciones previas a la búsqueda.
            validacionesFiltro();
            
            // Búsqueda en base a los filtros establecidos.
            this.listaReservas = servicio.listar(this.filtro);
            
            retorno = "listado";
            
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(ex.getMessage());
            
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
            // Rango fecha de entrada.
            if (this.filtro.getFechaEntradaDesde() != null && this.filtro.getFechaEntradaHasta() != null
                    && this.filtro.getFechaEntradaDesde().after(this.filtro.getFechaEntradaHasta())) {
                throw new InfoException(ConstantesErrores.FECHAENTRADA_RANGO_ERRONEO);
            }
            
            // Rango fecha de salida.
            if (this.filtro.getFechaSalidaDesde() != null && this.filtro.getFechaSalidaHasta() != null
                    && this.filtro.getFechaSalidaDesde().after(this.filtro.getFechaSalidaHasta())) {
                throw new InfoException(ConstantesErrores.FECHASALIDA_RANGO_ERRONEO);
            }
        }
    }
    
    /**
     * Realiza las validaciones de los datos introducidos en los campos del alta.
     * @throws InfoException 
     */
    private void validacionesCamposFechas() throws InfoException {
        // Fecha de entrada anterior a la actual.
        try {
            fechaActual = FechasUtil.quitaHorasFecha(FechasUtil.fechaActual());
        } catch (ParseException ex) {
            throw new InfoException(ConstantesErrores.ERROR_TRATAMIENTO_FECHAS_RESERVA);
        }
        
        if (this.reservaActual.getFechaEntrada() != null &&
                this.reservaActual.getFechaEntrada().before(fechaActual)) {
            throw new InfoException(ConstantesErrores.FECHAENTRADA_ANTERIOR_ACTUAL);
        }
        // Fecha de salida anterior a la actual.
        if (this.reservaActual.getFechaSalida() != null &&
                this.reservaActual.getFechaSalida().before(fechaActual)) {
            throw new InfoException(ConstantesErrores.FECHASALIDA_ANTERIOR_ACTUAL);
        }
        if (this.reservaActual.getFechaEntrada() != null 
                && this.reservaActual.getFechaSalida() != null) {
            // Rango de fechas.
            if (this.reservaActual.getFechaEntrada().after(this.reservaActual.getFechaSalida())) {
                throw new InfoException(ConstantesErrores.FECHAENTRADA_SUPERIOR_FECHASALIDA);
            }
        }
        if (this.reservaActual.getFechaEntrada().equals(this.reservaActual.getFechaSalida())) {
            throw new InfoException(ConstantesErrores.RESERVA_ENTRADA_SALIDA_MISMO_DIA);
        }
    }
    
    /**
     * Calcula el número de noches según las fechas de entrada y salida.
     */
    public void calculaNumeroNoches() {
        int numNoches;
        
        try {
            // Validación previa de las fechas.
            validacionesCamposFechas();

            // Cálculo de la diferencia de días.
            if (this.reservaActual.getFechaEntrada() != null 
                    && this.reservaActual.getFechaSalida() != null) {
                numNoches = FechasUtil.diferenciaDias(this.reservaActual.getFechaEntrada(),
                                                   this.reservaActual.getFechaSalida());
                
                this.reservaActual.setNumeroNoches(numNoches);
                
                this.doCalcularImporteReserva();
            }
        
        } catch (InfoException ex) {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
    }
    
    /**
     * Calcula el número de noches según las fechas de entrada y salida.
     */
    public void calculaNumeroNochesConsulta() {
        this.reservaActual.setNumeroNoches(ReservasUtil.calculaNumeroNoches(this.reservaActual));
    }
    
    /**
     * Establece el cliente en la reserva.
     * @param event 
     */
    public void doClienteSeleccionado(SelectEvent event) {
        Cliente cliente = (Cliente) event.getObject();
        
        this.reservaActual.setCliente(cliente);
    }  

    /**
     * Establece una habitación en la reserva.
     * @param event 
     */
    public void doHabitacionSeleccionada(SelectEvent event) {
        Habitacion habitacion = (Habitacion) event.getObject();

        // Comprobación de que la habitación no haya sido seleccionada previamente.
        boolean seguir = true;
        if(!this.listaTempDetalles.isEmpty()) {
            for(ReservaDet detalle : this.listaTempDetalles) {
                if (detalle.getHabitacion().equals(habitacion)) {
                    seguir = false;
                }
            }
        }
        
        if (seguir) {
            ReservaDet detalle = new ReservaDet();
            detalle.setHabitacion(habitacion);
            detalle.setReservaCab(this.reservaActual);
            this.listaTempDetalles.add(detalle);
            
            this.doCalcularImporteReserva();
            
        } else {
            JsfUtil.mensajeAviso(JsfUtil.getMessageError(ConstantesErrores.HABITACION_YA_SELECCIONADA));
        }
    } 
    
    /**
     * Calcula el importe total de la reserva, contando con el precio de las
     * habitaciones incluidas en el detalle y con los consumos asignados a la
     * cabecera de la reserva.
     */
    public void doCalcularImporteReserva() {
        Float importeTotal = 0.0F;
        
        try {
            // Importe subtotal de las habitaciones.
            if(!this.listaTempDetalles.isEmpty()) {
                for(ReservaDet detalle : this.listaTempDetalles) {
                    float precio = detalle.getHabitacion().getTipoHabitacion().getPrecio();
                    importeTotal = importeTotal + (precio * this.reservaActual.getNumeroNoches());
                }
            }

            // Importe subtotal de los consumos.
            if(this.reservaActual.getConsumos() != null) {
                for(Consumo consumo : this.reservaActual.getConsumos()) {
                    importeTotal = importeTotal + (consumo.getCantidad() * consumo.getCargo().getPrecio());
                }
            }

            // Se establece el importe total.
            this.reservaActual.setImporteTotal(importeTotal);    
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
        }
    }
    
    
    /**
     * Busca el cliente en base al DNI insertado.
     */
    public void doClienteBuscar(){
        
        try {
            String dni = this.reservaActual.getCliente().getDni();
            Cliente cliente = servicioCliente.buscar(dni);
            
            if (cliente == null) {
                this.reservaActual.setCliente(new Cliente());
                throw new ErrorException(ConstantesErrores.DNI_CLIENTE_NO_EXISTE);
            }
            
            this.reservaActual.setCliente(cliente);
        
        } catch (ErrorException ex) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ex.getCodigo()));
            
        } catch (Exception ex) {
            JsfUtil.mensajeError(ex.getMessage());
                    
        }
        
    }
    
    /**
     * Limpia la información que se visualiza del cliente.
     */
    public void doClienteLimpiar(){
        this.reservaActual.setCliente(new Cliente());
    }
    
    /**
     * Elimina una habitación de las seleccionadas en la lista temporal.
     * @param detalle 
     */
    public void doEliminarHabitacion(ReservaDet detalle) {
        // Si solo queda una habitación y se desea borrar, no se permite.
        if (this.listaTempDetalles.size() == 1) {
            JsfUtil.mensajeError(JsfUtil.getMessageError(ConstantesErrores.RESERVA_SIN_HABITACIONES));
        } else {
            // Búsqueda del detalle que se desea eliminar para retirarlo de la lista.
            List<ReservaDet> lista = this.listaTempDetalles;
            ReservaDet det;
            Iterator<ReservaDet> it;

            it = lista.iterator();

            while (it.hasNext() ) {
                det = it.next();
                if (det.equals(detalle)) {
                    it.remove(); 
                }
            }

            // Se inserta en la lista de detalles a borrar.
            this.reservaActual.getDetallesBorrar().add(detalle);

            this.doCalcularImporteReserva();
        }
    }
   

}
