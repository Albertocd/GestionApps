
package reservashotel.business.service.generic;

import java.util.List;

/**
 *
 * @author alberto
 * Interfaz genérica con las operaciones comunes definidas para los servicios.
 * @param <T>
 */
public interface IGenericService<T> {
    
    /**
     * listar
     * @return List < T >
     */
    public List<T> listar();
    
    /**
     * insertar
     * @param t 
     * @throws java.lang.Exception 
     */
    public void insertar(T t) throws Exception;
    
    /**
     * modificar
     * @param t 
     * @throws java.lang.Exception 
     */
    public void modificar(T t) throws Exception;
    
    /**
     * eliminar
     * @param t 
     * @throws java.lang.Exception 
     */
    public void eliminar(T t) throws Exception;
}
