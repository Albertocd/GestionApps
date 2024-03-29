package gestionclases.persistence.entity;
// Generated 25-mar-2015 23:03:28 by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Alumnos generated by hbm2java
 */
@Entity
@Table(name="alumnos")
public class Alumno  implements Serializable {

    private Integer             id;
    private String              nombre;
    private String              telefono;
    private String              descripcion;
    private String              correoElectronico;
    private Date                fechaAlta;
    private boolean             activoSn;
    private boolean             esGrupo;
    private Set<Asistencia>     asistencias = new HashSet<Asistencia>(0);

    public Alumno() {
    }
	
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="id", unique=true, nullable=false)
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="nombre", nullable=false, length=50)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Column(name="telefono", nullable=false, length=10)
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Column(name="correo_electronico", length=50)
    public String getCorreoElectronico() {
        return this.correoElectronico;
    }
    
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_alta", nullable=false, length=10)
    public Date getFechaAlta() {
        return this.fechaAlta;
    }
    
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    @Column(name="activo_sn")
    public boolean getActivoSn() {
        return this.activoSn;
    }
    
    public void setActivoSn(boolean activoSn) {
        this.activoSn = activoSn;
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy="alumno")
    public Set<Asistencia> getAsistencias() {
        return this.asistencias;
    }
    
    public void setAsistencias(Set<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }

    @Column(name="descripcion")
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name="es_grupo")
    public boolean isEsGrupo() {
        return esGrupo;
    }

    public void setEsGrupo(boolean esGrupo) {
        this.esGrupo = esGrupo;
    }

    
}


