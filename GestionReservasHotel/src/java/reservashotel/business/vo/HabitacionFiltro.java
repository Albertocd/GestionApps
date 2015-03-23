
package reservashotel.business.vo;

/**
 * @author alberto
 * Filtro para la entidad Habitacion.
 */
public class HabitacionFiltro {
    
    private String      codigo;
    private String      nombre;
    private int         fumadorSn;
    private int         exteriorSn;
    private int         movReducidaSn;
    private int         camaSuplSn;
    private int         activoSn;
    private Integer     tipoHabitacion;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFumadorSn() {
        return fumadorSn;
    }

    public void setFumadorSn(int fumadorSn) {
        this.fumadorSn = fumadorSn;
    }
    
    public int getMovReducidaSn() {
        return movReducidaSn;
    }

    public void setMovReducidaSn(int movReducidaSn) {
        this.movReducidaSn = movReducidaSn;
    }

    public int getExteriorSn() {
        return exteriorSn;
    }

    public void setExteriorSn(int exteriorSn) {
        this.exteriorSn = exteriorSn;
    }

    public int getActivoSn() {
        return activoSn;
    }

    public void setActivoSn(int activoSn) {
        this.activoSn = activoSn;
    }

    

    public Integer getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(Integer tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public int getCamaSuplSn() {
        return camaSuplSn;
    }

    public void setCamaSuplSn(int camaSuplSn) {
        this.camaSuplSn = camaSuplSn;
    }

}
