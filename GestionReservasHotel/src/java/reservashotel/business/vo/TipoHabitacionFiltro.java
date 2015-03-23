
package reservashotel.business.vo;

import reservashotel.business.vo.generic.GenericTipoEstadoFiltro;

/**
 * @author alberto
 * Filtro para la entidad TipoHabitacion.
 */
public class TipoHabitacionFiltro extends GenericTipoEstadoFiltro {
    
    private String      codigo;
    private String      nombre;
    private Float       precioDesde;
    private Float       precioHasta;
    private int         activoSn;
    
    
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

    public Float getPrecioDesde() {
        return precioDesde;
    }

    public void setPrecioDesde(Float precioDesde) {
        this.precioDesde = precioDesde;
    }

    public Float getPrecioHasta() {
        return precioHasta;
    }

    public void setPrecioHasta(Float precioHasta) {
        this.precioHasta = precioHasta;
    }

    public int getActivoSn() {
        return activoSn;
    }

    public void setActivoSn(int activoSn) {
        this.activoSn = activoSn;
    }
}
