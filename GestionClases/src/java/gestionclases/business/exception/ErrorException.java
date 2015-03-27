
package gestionclases.business.exception;

/**
 * @author alberto
 * Clase para manejo de excepciones de error.
 */
public class ErrorException extends GenericException {

    /**
     * Constructor
     * @param codigo Código de excepción.
     */
    public ErrorException(String codigo) {
        super(codigo);
    }
   
}
