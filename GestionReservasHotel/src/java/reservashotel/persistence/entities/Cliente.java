package reservashotel.persistence.entities;
// Generated 28-feb-2015 21:08:17 by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Version;

/**
 * Cliente generated by hbm2java
 */
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    private Integer     idCliente;
    private String      nombre;
    private String      apellido1;
    private String      apellido2;
    private String      dni;
    private Date        fechaNacimiento;
    private String      email;
    private String      telefono;
    private String      cuentaBancaria;
    private Provincia   provincia;
    private Timestamp   timestamp;
   
    public Cliente() {
    }

    @Id 
    @GeneratedValue(strategy=IDENTITY)
    @Column(name="id_cliente", unique=true, nullable=false)
    public Integer getIdCliente() {
        return this.idCliente;
    }
    
    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    @Version
    @Column(name="timestamp", nullable=false)
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="fecha_nacimiento", nullable=false)
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="id_provincia", nullable=false)
    public Provincia getProvincia() {
        return this.provincia;
    }
    
    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
    
    @Column(name="nombre", nullable=false)
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Column(name="apellido1", nullable=false)
    public String getApellido1() {
        return this.apellido1;
    }
    
    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    
    @Column(name="apellido2", nullable=false)
    public String getApellido2() {
        return this.apellido2;
    }
    
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    @Column(name="dni", unique=true, nullable=false)
    public String getDni() {
        return this.dni;
    }
    
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    @Column(name="email", nullable=false)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Column(name="telefono", nullable=false)
    public String getTelefono() {
        return this.telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    @Column(name="cuenta_bancaria", nullable=false)
    public String getCuentaBancaria() {
        return this.cuentaBancaria;
    }
    
    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    /**
     * Propiedad para obtener el nombre completo, en formato:
     * apellido1 apellido2, nombre
     * @return String
     */
    @Override
    public String toString() {
        String cadena = "";
        
        if (this.apellido1 != null) {
            cadena = this.apellido1 + " " + this.apellido2 + ", " + this.nombre;
        }
        
        return cadena;
    }
}


