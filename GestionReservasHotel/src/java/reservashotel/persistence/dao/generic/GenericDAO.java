package reservashotel.persistence.dao.generic;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 * @author alberto
 * @param <Entity>
 * @param <Key>
 * 
 * Implemetación de las funcionalidades genéricas de la capa de persistencia.
 */
public class GenericDAO<Entity, Key extends Serializable> implements IGenericDAO<Entity, Key> {

    public  Class<Entity>   domainClass = getDomainClass();
   
    protected Class getDomainClass() {
        if (domainClass == null) {
            ParameterizedType thisType = (ParameterizedType) getClass().getGenericSuperclass();
            domainClass = (Class) thisType.getActualTypeArguments()[0];
        }

        return domainClass;
    }

    @Override
    public Entity buscar(Session sesion, Key id) throws HibernateException {
        Entity returnValue;
        
        try {
            returnValue = (Entity) sesion.get(domainClass, id);
        } catch (HibernateException e) {
            throw e;
        }

        return returnValue;
    }

    @Override
    public List<Entity> listar(Session sesion, String ordenarPor) throws HibernateException {
        List<Entity> returnValue;

        try {
            Criteria crit = sesion.createCriteria(domainClass);
            crit.addOrder(Order.asc(ordenarPor));
            crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            returnValue = crit.list();
        } catch (HibernateException hex) {
            throw hex;
        } 

        return returnValue;
    }
    
    @Override
    public void modificar(Session sesion, Entity t) throws Exception {
        sesion.saveOrUpdate(t);
        sesion.flush();
    }

    @Override
    public void insertar(Session sesion, Entity t) throws HibernateException {
        sesion.save(t);
    }

    @Override
    public void eliminar(Session sesion, Entity t) throws HibernateException {
        sesion.delete(t); 
    }

    @Override
    public void rollback(Session sesion) throws Exception {
        if (sesion != null) {
            sesion.getTransaction().rollback();
        }
    }

}
