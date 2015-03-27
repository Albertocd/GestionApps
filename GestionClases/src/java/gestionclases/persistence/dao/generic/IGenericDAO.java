package gestionclases.persistence.dao.generic;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;

/**
 * @author alberto
 * @param <Entity>
 * @param <PK>
 * 
 * Interface para funcionalidades genéricas de la capa de persistencia.
 */
public interface IGenericDAO<Entity, PK extends Serializable> {

    /**
     * Buscar la identidad pasada por parámetro.
     * @param sesion Sesión
     * @param id Valor de búsqueda
     * @return Entity
     */
    Entity buscar(Session sesion, PK id);
    
    /**
     * Lista las instancias de la entidad ordenada según el parámetro.
     * @param sesion Sesión
     * @param ordenarPor Criterio de ordenación.
     * @return List< Entity >
     */
    List<Entity> listar(Session sesion, String ordenarPor);
    
    /**
     * Inserta la entidad pasada por parámetro.
     * @param sesion Sesión
     * @param t Entity
     */
    void insertar(Session sesion, Entity t);

    /**
     * Modifica la entidad pasada porm parámetro.
     * @param sesion Sesión
     * @param t Entity
     * @throws Exception 
     */
    void modificar(Session sesion, Entity t) throws Exception;

    /**
     * Elimina la entidad pasada porm parámetro.
     * @param sesion Sesión
     * @param t Entity
     * @throws Exception 
     */
    void eliminar(Session sesion, Entity t) throws Exception;
    
    /**
     * Realiza rollback de la sesión.
     * @param sesion Sesión
     * @throws Exception 
     */
    void rollback(Session sesion) throws Exception;
}
