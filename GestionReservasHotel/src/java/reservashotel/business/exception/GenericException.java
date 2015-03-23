
package reservashotel.business.exception;

/**
 * @author alberto
 * Clase genérica para gestionar excepciones.
 */
public class GenericException extends Exception {
    
    private String codigo;

    /**
     * Constructor
     * @param codigo Código de excepción.
     */
    public GenericException(String codigo) {
        super();
        this.codigo = codigo;
    }
    
    /**
     * getCodigo
     * @return codigo Código
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * setCodigo
     * @param codigo Código
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
