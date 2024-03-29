package gestionclases.persistence.entity;
// Generated 25-mar-2015 23:03:28 by Hibernate Tools 4.3.1


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mensualidades generated by hbm2java
 */
@Entity
@Table(name="mensualidades")
public class Mensualidad  implements Serializable {


    private Integer         id;
    private Asistencia      asistencia;
    private FechaPago       fechaPago;
    private Pago            pago;
    private String          mensualidad;

    public Mensualidad() {
    }
	
   
    @Id 
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="asistencia", nullable=false)
    public Asistencia getAsistencia() {
        return this.asistencia;
    }
    
    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="fecha_pago", nullable=false)
    public FechaPago getFechaPago() {
        return this.fechaPago;
    }
    
    public void setFechaPago(FechaPago fechaPago) {
        this.fechaPago = fechaPago;
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pago")
    public Pago getPago() {
        return this.pago;
    }
    
    public void setPago(Pago pago) {
        this.pago = pago;
    }
    
    @Column(name="mensualidad", nullable=false, length=45)
    public String getMensualidad() {
        return this.mensualidad;
    }
    
    public void setMensualidad(String mensualidad) {
        this.mensualidad = mensualidad;
    }

}


