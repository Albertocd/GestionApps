
package reservashotel.business.vo;

import java.util.Date;

/**
 * @author alberto
 * Filtro para la entidad ReservaCab.
 */
public class ReservaCabFiltro {
    
    private String      referencia;
    private Date        fechaRegistroDesde;
    private Date        fechaRegistroHasta;
    private Date        fechaEntradaDesde;
    private Date        fechaEntradaHasta;
    private Date        fechaSalidaDesde;
    private Date        fechaSalidaHasta;
    private Integer     cliente;
    private int         estado;

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    public Date getFechaRegistroDesde() {
        return fechaRegistroDesde;
    }

    public void setFechaRegistroDesde(Date fechaRegistroDesde) {
        this.fechaRegistroDesde = fechaRegistroDesde;
    }

    public Date getFechaRegistroHasta() {
        return fechaRegistroHasta;
    }

    public void setFechaRegistroHasta(Date fechaRegistroHasta) {
        this.fechaRegistroHasta = fechaRegistroHasta;
    }

    public Date getFechaEntradaDesde() {
        return fechaEntradaDesde;
    }

    public void setFechaEntradaDesde(Date fechaEntradaDesde) {
        this.fechaEntradaDesde = fechaEntradaDesde;
    }

    public Date getFechaEntradaHasta() {
        return fechaEntradaHasta;
    }

    public void setFechaEntradaHasta(Date fechaEntradaHasta) {
        this.fechaEntradaHasta = fechaEntradaHasta;
    }

    public Date getFechaSalidaDesde() {
        return fechaSalidaDesde;
    }

    public void setFechaSalidaDesde(Date fechaSalidaDesde) {
        this.fechaSalidaDesde = fechaSalidaDesde;
    }

    public Date getFechaSalidaHasta() {
        return fechaSalidaHasta;
    }

    public void setFechaSalidaHasta(Date fechaSalidaHasta) {
        this.fechaSalidaHasta = fechaSalidaHasta;
    }

    public Integer getCliente() {
        return cliente;
    }

    public void setCliente(Integer cliente) {
        this.cliente = cliente;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
}
