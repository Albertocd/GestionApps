
package reservashotel.business.vo;

/**
 * @author alberto
 * Filtro para la entidad Consumo.
 */
public class ConsumoFiltro {
    
    private CargoFiltro cargo;
    private String      referencia;

    public CargoFiltro getCargo() {
        return cargo;
    }

    public void setCargo(CargoFiltro cargo) {
        this.cargo = cargo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    
}
