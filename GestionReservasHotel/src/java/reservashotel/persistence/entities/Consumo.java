package reservashotel.persistence.entities;
// Generated 28-feb-2015 21:08:17 by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Consumo generated by hbm2java
 */
@Entity
@Table(name = "consumos")
public class Consumo implements Serializable {

    private Integer     idConsumo;
    private Date        fechaConsumo;
    private Integer     cantidad;
    private Cargo       cargo;
    private ReservaCab  reservaCab;

    public Consumo() {
    }

    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_consumo", nullable=false)
    private Integer getIdConsumo() {
        return this.idConsumo;
    }
    
    private void setIdConsumo(Integer idConsumo) {
        this.idConsumo = idConsumo;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_cargo", nullable=false)
    public Cargo getCargo() {
        return this.cargo;
    }
    
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_reserva_cab", nullable=false)
    public ReservaCab getReservaCab() {
        return this.reservaCab;
    }
    
    public void setReservaCab(ReservaCab reservaCab) {
        this.reservaCab = reservaCab;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_consumo", nullable=false)
    public Date getFechaConsumo() {
        return this.fechaConsumo;
    }
    
    public void setFechaConsumo(Date fechaConsumo) {
        this.fechaConsumo = fechaConsumo;
    }
    
    @Column(name="cantidad", nullable=false)
    public Integer getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}


