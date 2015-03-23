package reservashotel.persistence.entities;
// Generated 28-feb-2015 21:08:17 by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * ReservaCab generated by hbm2java
 */
@Entity
@Table(name = "reservas_cab")
public class ReservaCab implements Serializable {

    private Integer          idReservaCab;
    private String           referencia;
    private Date             fechaRegistro;
    private Date             fechaEntrada;
    private Date             fechaSalida;
    private String           observaciones;
    private Cliente          cliente;
    private Float            importeTotal;
    private EstadoReserva    estadoReserva;
    private Usuario          usuario;
    private Timestamp        timestamp;
    private int              numeroNoches;
    private List<ReservaDet> detalles       = new ArrayList<>(0);
    private List<ReservaDet> detallesBorrar = new ArrayList<>();
    // Los consumos son un conjunto en lugar de una lista porque Hibernate no
    // permite tener en memoria dos listas para relaciones manyToOne.
    private Set<Consumo>     consumos       = new HashSet<>(0); 
    private Set<Consumo>     consumosBorrar = new HashSet<>(0);
    
    
    
    public ReservaCab() {
        this.cliente = new Cliente();
    }
   
    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_reserva_cab", nullable=false)
    public Integer getIdReservaCab() {
        return this.idReservaCab;
    }
    
    public void setIdReservaCab(Integer idReservaCab) {
        this.idReservaCab = idReservaCab;
    }

    @Column(name="referencia", nullable=false)
    public String getReferencia() {
        return this.referencia;
    }
    
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    

    @Column(name="timestamp", nullable=false)
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_cliente", nullable=false)
    public Cliente getCliente() {
        return this.cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_estado_reserva", nullable=false)
    public EstadoReserva getEstadoReserva() {
        return this.estadoReserva;
    }
    
    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    @Column(name="importe_total", nullable=false)
    public Float getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(Float importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_usuario", nullable=false)
    public Usuario getUsuario() {
        return this.usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_registro", nullable=false)
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_entrada", nullable=false)
    public Date getFechaEntrada() {
        return this.fechaEntrada;
    }
    
    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_salida", nullable=false)
    public Date getFechaSalida() {
        return this.fechaSalida;
    }
    
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    
    @Column(name="observaciones")
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Transient
    public int getNumeroNoches() {
        return numeroNoches;
    }

    public void setNumeroNoches(int numeroNoches) {
        this.numeroNoches = numeroNoches;
    }


    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="reservaCab")
    public Set<Consumo> getConsumos() {
        return this.consumos;
    }
    
    public void setConsumos(Set<Consumo> consumos) {
        this.consumos = consumos;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="reservaCab")
    public List<ReservaDet> getDetalles() {
        return this.detalles;
    }
    
    public void setDetalles(List<ReservaDet> detalles) {
        this.detalles = detalles;
    }

    @Transient
    public List<ReservaDet> getDetallesBorrar() {
        return detallesBorrar;
    }

    public void setDetallesBorrar(List<ReservaDet> detallesBorrar) {
        this.detallesBorrar = detallesBorrar;
    }

    @Transient
    public Set<Consumo> getConsumosBorrar() {
        return consumosBorrar;
    }

    public void setConsumosBorrar(Set<Consumo> consumosBorrar) {
        this.consumosBorrar = consumosBorrar;
    }

}


