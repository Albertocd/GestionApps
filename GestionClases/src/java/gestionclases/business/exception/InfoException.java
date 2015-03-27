package gestionclases.business.exception;

/**
 * @author alberto
 * Clase para manejo de excepciones propias.
 */
public class InfoException extends GenericException{
    
    /**
     * Constructor
     * @param codigo Código de excepción.
     */
    public InfoException(String codigo) {
        super(codigo);
    }

}
