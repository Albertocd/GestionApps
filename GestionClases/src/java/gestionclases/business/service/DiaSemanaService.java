
package gestionclases.business.service;

import gestionclases.business.service.generic.IGenericService;
import gestionclases.persistence.dao.DiaSemanaDAO;
import gestionclases.persistence.entity.DiaSemana;
import gestionclases.persistence.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author alberto
 */
public class DiaSemanaService implements IGenericService<DiaSemana> {

    @Override
    public List<DiaSemana> listar() {
        Session sesion = HibernateUtil.getSession();
        sesion.beginTransaction();
        
        List<DiaSemana> lista = new DiaSemanaDAO().listar(sesion, "id");
        
        sesion.getTransaction().commit();
        
        return lista;
    }

    @Override
    public void insertar(DiaSemana t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modificar(DiaSemana t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(DiaSemana t) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
